package com.shalou.demo.controller.note;

import com.shalou.demo.controller.CountDownController;
import com.shalou.demo.domain.note.NotesCloud;
import com.shalou.demo.repository.note.NotesCloudRespository;
import com.shalou.demo.service.note.NotesCloudService;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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

    //添加时间卡片
    @GetMapping(value = "/note/addNote")
    @ResponseBody
    public Object addNote(NotesCloud notesCloud){
        //创建一篇新的笔记
        NotesCloud notesCloudNew=new NotesCloud();

        //获取当前系统的时间戳
        String timeStamp= String.valueOf(new Date().getTime());

        notesCloudNew.setTimeStamp(timeStamp);

        //保存到数据库
        notesCloudRespository.save(notesCloudNew);

        //返回添加成功
        return ResultUtil.success("添加成功");
    }

    //添加时间卡片
    @GetMapping(value = "/note/saveNote")
    @ResponseBody
    public Object saveNote(NotesCloud notesCloud){

        //保存文章
        return notesCloudService.saveNote(notesCloud);

    }
}
