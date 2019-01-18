package com.shalou.demo.controller.note;

import com.shalou.demo.controller.CountDownController;
import com.shalou.demo.domain.note.NoteClass;
import com.shalou.demo.domain.note.NotesCloud;
import com.shalou.demo.repository.note.NoteClassRespository;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

//设置API注解
@Controller
public class NoteClassController {

    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(CountDownController.class);

    //引入云笔记接口
    @Autowired
    private NoteClassRespository noteClassRespository;

    //测试接口
    @GetMapping(value = "/noteClass/test")
    @ResponseBody
    public Object test() {
        return ResultUtil.success("test noteClass API");
    }

    //创建分类
    @PostMapping(value = "/noteClass/addClass")
    @ResponseBody
    public Object addClass(NoteClass noteClass) {
        //根据ID查找是否已有分类记录
        String userId = noteClass.getNoteUserId();
        NoteClass noteClassFindOne = noteClassRespository.findNoteClassByNoteUserId(userId);
        if (noteClassFindOne == null) {
            logger.info("没有日志,开始创建");
            //创建一篇新的笔记
            NoteClass noteClassOne = new NoteClass();
            //获取当前系统的时间戳
            String timeStamp = String.valueOf(new Date().getTime());
            //打印日志
            logger.info("分类数组 >>>>>>>>>>>>>" + noteClass.getClassArr());
            //获取分类数组
            noteClassOne.setClassArr(noteClass.getClassArr());
            //获取用户ID
            noteClassOne.setNoteUserId(noteClass.getNoteUserId());
            //保存到数据库
            NoteClass noteClassAll = noteClassRespository.save(noteClassOne);
            //返回添加成功
            return ResultUtil.success(noteClassAll);
        } else {
            logger.info("已有日志");
            noteClassFindOne.setClassArr(noteClass.getClassArr());
            //保存到数据库
            NoteClass noteClassAll = noteClassRespository.save(noteClassFindOne);
            return ResultUtil.success(noteClassAll);
        }
    }

    //查找用户下的分类列表
    @GetMapping(value = "/noteClass/findClass")
    @ResponseBody
    public Object findClass(NoteClass noteClass) {
        return ResultUtil.success(noteClassRespository.findNoteClassByNoteUserId(noteClass.getNoteUserId()));
    }

}
