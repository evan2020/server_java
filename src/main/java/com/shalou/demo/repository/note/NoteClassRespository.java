package com.shalou.demo.repository.note;

import com.shalou.demo.domain.note.NoteClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//云笔记分类接口
public interface NoteClassRespository extends JpaRepository<NoteClass, Integer>, JpaSpecificationExecutor<NoteClass> {
    //根据用户ID查找分类列表
    public NoteClass findNoteClassByNoteUserId(String noteUserId);
}
