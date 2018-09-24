package com.shalou.demo.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//设置用户表
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

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
    private String userName;

    //设置手机号码
    private String phoneNum;

    //设置邮箱
    private String userEmail;

    //设置性别
    private Integer sex;

    //设置地区
    private String city;

    //设置状态
    private Integer status;

    //设置get和set方法

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    //设置toString重构


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", phoneNum=" + phoneNum +
                ", userEmail='" + userEmail + '\'' +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", status=" + status +
                '}';
    }
}
