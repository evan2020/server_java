package com.shalou.demo.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//设置用户表
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ShortUrl {
    //自动生成创建时间
    @CreatedDate
    @Column(name = "create_time") private Date createTime;

    //自动记录最后一次修改时间
    @LastModifiedDate
    @Column(name = "modify_time") private Date modifyTime;


    //设置用户ID
    @Id
    @GeneratedValue
    private Integer id;

    //设置用户名
    private String shortUrl;

    //设置手机号码
    private String longUrl;

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

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public String toString() {
        return "ShortUrlUtils{" +
                "createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", id=" + id +
                ", shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                '}';
    }
}
