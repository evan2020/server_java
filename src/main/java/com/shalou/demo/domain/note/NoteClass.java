package com.shalou.demo.domain.note;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class NoteClass {

    //自动生成创建时间
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    //自动记录最后一次修改时间
    @LastModifiedDate
    @Column(name = "modify_time")
    private Date modifyTime;


    //设置ID
    @Id
    @GeneratedValue
    private Integer id;

    //分类数据
    @Column(name = "class_arr", length = 13500)
    private String classArr;

    //作者的ID
    private String noteUserId;

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

    public String getClassArr() {
        return classArr;
    }

    public void setClassArr(String classArr) {
        this.classArr = classArr;
    }

    public String getNoteUserId() {
        return noteUserId;
    }

    public void setNoteUserId(String noteUserId) {
        this.noteUserId = noteUserId;
    }

    @Override
    public String toString() {
        return "NoteClass{" +
                "createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", id=" + id +
                ", classArr='" + classArr + '\'' +
                ", noteUserId='" + noteUserId + '\'' +
                '}';
    }
}
