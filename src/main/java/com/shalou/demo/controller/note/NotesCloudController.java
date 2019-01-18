package com.shalou.demo.controller.note;

import com.shalou.demo.controller.CountDownController;
import com.shalou.demo.domain.note.NotesCloud;
import com.shalou.demo.repository.note.NotesCloudRespository;
import com.shalou.demo.service.note.NotesCloudService;
import com.shalou.demo.utils.ResultUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//设置API注解
@Controller
public class NotesCloudController {
    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(CountDownController.class);

    //引入云笔记接口
    @Autowired
    private NotesCloudRespository notesCloudRespository;

    @Autowired
    private NotesCloudService notesCloudService;

    //测试接口
    @GetMapping(value = "/note/test")
    @ResponseBody
    public Object test(NotesCloud notesCloud) {
        return ResultUtil.success("test note API");
    }

    //创建笔记
    @GetMapping(value = "/note/addNote")
    @ResponseBody
    public Object addNote(NotesCloud notesCloud) {
        //创建一篇新的笔记
        NotesCloud notesCloudNew = new NotesCloud();

        //获取当前系统的时间戳
        String timeStamp = String.valueOf(new Date().getTime());
        String noteArticleId = timeStamp + "dsx2016";
        logger.info("分类名称" + notesCloud.getNoteClassify());
        notesCloudNew.setTimeStamp(timeStamp);
        notesCloudNew.setNoteArticleId(noteArticleId);
        notesCloudNew.setNoteClassify(notesCloud.getNoteClassify());
        notesCloudNew.setNoteUserId(notesCloud.getNoteUserId());

        //保存到数据库
        notesCloudRespository.save(notesCloudNew);

        Map config = new HashMap();
        config.put("noteArticleId", noteArticleId);
        config.put("timeStamp", timeStamp);
        JSONObject jsonTest = JSONObject.fromObject(config);

        //返回添加成功
        return ResultUtil.success(jsonTest);
    }

    //保存笔记
    @PostMapping(value = "/note/saveNote")
    @ResponseBody
    public Object saveNote(NotesCloud notesCloud) {

        //保存文章
        return notesCloudService.saveNote(notesCloud);

    }

    //查询该用户下所有的笔记
    @GetMapping(value = "/note/findAllNote")
    @ResponseBody
    public Object findAllNote(NotesCloud notesCloud) {
        List<NotesCloud> notesClouds = notesCloudRespository.findAllByNoteUserId(notesCloud.getNoteUserId());
        return ResultUtil.success(notesClouds);
    }

    //查询该用户分类笔记
    @GetMapping(value = "/note/findNoteByClass")
    @ResponseBody
    public Object findNoteByClass(NotesCloud notesCloud) {
        List<NotesCloud> notesClouds = notesCloudRespository.findNotesCloudsByNoteUserIdAndNoteClassify(notesCloud.getNoteUserId(), notesCloud.getNoteClassify());
        return ResultUtil.success(notesClouds);
    }


    //删除当前笔记
    @GetMapping(value = "/note/deleteOneNote")
    @ResponseBody
    public Object deleteOneNote(NotesCloud notesCloud) {
        notesCloudRespository.deleteById(notesCloud.getId());
        return ResultUtil.success("删除成功");
    }

    //查询单篇笔记
    @GetMapping(value = "/note/findOneNote")
    @ResponseBody
    public Object findOneNote(NotesCloud notesCloud) {
        NotesCloud notesCloudOne = notesCloudRespository.findNotesCloudsByNoteArticleId(notesCloud.getNoteArticleId());
        return ResultUtil.success(notesCloudOne);
    }
}
