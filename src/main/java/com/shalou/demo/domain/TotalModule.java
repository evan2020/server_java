package com.shalou.demo.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


//设置总的分类模块的实体类
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TotalModule {

    //自动生成创建时间
    @CreatedDate
    @Column(name = "create_time")
    private Date created_time;

    //自动记录最后一次修改时间
    @LastModifiedDate
    @Column(name = "modify_time")
    private Date modifyTime;

    //设置Id
    @Id
    @GeneratedValue
    private Integer id;

    //设置总的分类名称
    private String categoryName;

    //设置封面图片地址
    private String coverImgUrl;

    //设置H5活动地址
    private String webUrl;

    //设置标题
    private String title;

    //设置描述
    private String description;

    //设置备注
    private String remarks;

    //设置排序
    private Integer sort;

    //设置层级
    private Integer level;

    //设置类型
    private String type;

    //设置点击数
    private Integer clickNum;

    //设置转发数
    private Integer forwardNum;

    //设置收藏数
    private Integer collectionNum;


    //设置get和set


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(Integer forwardNum) {
        this.forwardNum = forwardNum;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    //设置toString方法

    @Override
    public String toString() {
        return "TotalModule{" +
                "created_time=" + created_time +
                ", modifyTime=" + modifyTime +
                ", id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", remarks='" + remarks + '\'' +
                ", sort=" + sort +
                ", level=" + level +
                ", type='" + type + '\'' +
                ", clickNum=" + clickNum +
                ", forwardNum=" + forwardNum +
                ", collectionNum=" + collectionNum +
                '}';
    }
}
