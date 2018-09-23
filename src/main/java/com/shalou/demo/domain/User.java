package com.shalou.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//设置用户表
@Entity
public class User {

    //设置用户ID
    @Id
    @GeneratedValue
    private Integer id;

    //设置用户名
    private String userName;

    //设置手机号码
    private long phoneNum;

    //设置邮箱
    private String userEmail;

    //设置性别
    private Integer sex;

    //设置地区
    private String city;

    //设置状态
    private Integer status;

    //设置get和set方法


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

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
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
