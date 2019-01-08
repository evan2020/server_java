package com.shalou.demo.domain;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class CountDown {

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

    //事件名称
    private String itemName;

    //目标日期
    private String targetDate;

    //分类
    private String classIfy;

    //是否置顶(0 为不置顶 1 置顶)
    private String checked;

    //是否重复(0 为不重复 1 重复)
    private String isRepeat;

    //是否提醒(0 为不提醒 1 提醒)
    private String isRemind;

    //记录openId
    private String openId;

    @Override
    public String toString() {
        return "CountDown{" +
                "createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", id=" + id +
                ", itemName='" + itemName + '\'' +
                ", targetDate='" + targetDate + '\'' +
                ", classIfy='" + classIfy + '\'' +
                ", checked='" + checked + '\'' +
                ", isRepeat='" + isRepeat + '\'' +
                ", isRemind='" + isRemind + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getClassIfy() {
        return classIfy;
    }

    public void setClassIfy(String classIfy) {
        this.classIfy = classIfy;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
