package com.shalou.demo.service.note;

import com.shalou.demo.domain.note.NotesCloud;
import com.shalou.demo.repository.note.NotesCloudRespository;
import com.shalou.demo.service.UserService;
import com.shalou.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//设置service
@Service
public class NotesCloudService {
    //引入日志
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private NotesCloudRespository notesCloudRespository;

    //保存文章
    public Object saveNote(NotesCloud notesCloud) {
        //根据文章id查找文章
        NotesCloud notesCloudOne = notesCloudRespository.findNotesCloudsByTimeStamp(notesCloud.getTimeStamp());
        //保存原来的元数据
        notesCloudOne.setCreateTime(notesCloudOne.getCreateTime());
        notesCloudOne.setModifyTime(notesCloudOne.getModifyTime());
        notesCloudOne.setTimeStamp(notesCloudOne.getTimeStamp());
        notesCloudOne.setId(notesCloudOne.getId());
        //获取文章的内容
        notesCloudOne.setNoteContent(notesCloud.getNoteContent());

        notesCloudRespository.save(notesCloudOne);
        return ResultUtil.success("保存成功");
    }

}
