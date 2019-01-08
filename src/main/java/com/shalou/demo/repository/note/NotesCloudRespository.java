package com.shalou.demo.repository.note;

import com.shalou.demo.domain.note.NotesCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//云笔记接口
public interface NotesCloudRespository extends JpaRepository<NotesCloud,Integer>, JpaSpecificationExecutor<NotesCloud> {
    //根据文章id(时间戳)找文章
    public NotesCloud findNotesCloudsByTimeStamp(String timeStamp);
}
