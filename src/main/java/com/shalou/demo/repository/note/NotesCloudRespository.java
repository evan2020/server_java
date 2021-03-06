package com.shalou.demo.repository.note;

import com.shalou.demo.domain.note.NotesCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//云笔记接口
public interface NotesCloudRespository extends JpaRepository<NotesCloud, Integer>, JpaSpecificationExecutor<NotesCloud> {
    //根据(时间戳)找文章
    public NotesCloud findNotesCloudsByTimeStamp(String timeStamp);

    //根据文章ID找文章
    public NotesCloud findNotesCloudsByNoteArticleId(String noteArticleId);

    //根据用户iD查找该用户下的所有笔记
    public List<NotesCloud> findAllByNoteUserId(String noteUserId);

    //通过id和分类查询笔记
    public List<NotesCloud> findNotesCloudsByNoteUserIdAndNoteClassify(String noteUserId, String classify);

    //根据文章ID删除当前文章
    public NotesCloud deleteByNoteArticleId(String noteArticleId);

}
