package com.shalou.demo.domain.note;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class NotesCloud {

    //自动生成创建时间
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    //自动记录最后一次修改时间
    @LastModifiedDate
    @Column(name = "modify_time")
    private Date modifyTime;


    //设置用户ID
    @Id
    @GeneratedValue
    private Integer id;

    //创建文章的时间戳
    private String timeStamp;

    //笔记的标题
    private String noteTitle;

    //笔记的作者
    private  String noteAuthor;

    //作者的ID
    private String noteUserId;

    //文章的ID
    private String noteArticleId;

    //笔记的标签
    private String noteLabel;

    //笔记的分类
    private String noteClassify;

    //笔记的内容
    private String noteContent;

    //笔记用于分享的链接
    private String noteShareLinks;

    //笔记分享的密码
    private String noteSharePassword;

    //笔记分享的时间限制
    private String noteTimeLimit;

    //笔记分享的阅读次数
    private String noteReadingTimes;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteAuthor() {
        return noteAuthor;
    }

    public void setNoteAuthor(String noteAuthor) {
        this.noteAuthor = noteAuthor;
    }

    public String getNoteUserId() {
        return noteUserId;
    }

    public void setNoteUserId(String noteUserId) {
        this.noteUserId = noteUserId;
    }

    public String getNoteArticleId() {
        return noteArticleId;
    }

    public void setNoteArticleId(String noteArticleId) {
        this.noteArticleId = noteArticleId;
    }

    public String getNoteLabel() {
        return noteLabel;
    }

    public void setNoteLabel(String noteLabel) {
        this.noteLabel = noteLabel;
    }

    public String getNoteClassify() {
        return noteClassify;
    }

    public void setNoteClassify(String noteClassify) {
        this.noteClassify = noteClassify;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteShareLinks() {
        return noteShareLinks;
    }

    public void setNoteShareLinks(String noteShareLinks) {
        this.noteShareLinks = noteShareLinks;
    }

    public String getNoteSharePassword() {
        return noteSharePassword;
    }

    public void setNoteSharePassword(String noteSharePassword) {
        this.noteSharePassword = noteSharePassword;
    }

    public String getNoteTimeLimit() {
        return noteTimeLimit;
    }

    public void setNoteTimeLimit(String noteTimeLimit) {
        this.noteTimeLimit = noteTimeLimit;
    }

    public String getNoteReadingTimes() {
        return noteReadingTimes;
    }

    public void setNoteReadingTimes(String noteReadingTimes) {
        this.noteReadingTimes = noteReadingTimes;
    }

    @Override
    public String toString() {
        return "NotesCloud{" +
                "createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", id=" + id +
                ", timeStamp='" + timeStamp + '\'' +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteAuthor='" + noteAuthor + '\'' +
                ", noteUserId='" + noteUserId + '\'' +
                ", noteArticleId='" + noteArticleId + '\'' +
                ", noteLabel='" + noteLabel + '\'' +
                ", noteClassify='" + noteClassify + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", noteShareLinks='" + noteShareLinks + '\'' +
                ", noteSharePassword='" + noteSharePassword + '\'' +
                ", noteTimeLimit='" + noteTimeLimit + '\'' +
                ", noteReadingTimes='" + noteReadingTimes + '\'' +
                '}';
    }
}
