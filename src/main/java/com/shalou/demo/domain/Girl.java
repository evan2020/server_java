package com.shalou.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

//设置注解
@Entity
public class Girl {

    //id自增
    @Id
    @GeneratedValue
    //女生的id
    private Integer id;

    //女生的数据
    private String cupSize;

    //设置表单验证规则和提示信息
    @Min(value = 18,message = "未成年少女禁止入内")
    //女生的年龄
    private Integer age;

    //get和set

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Girl(){

    }

    //重写toString方法
    @Override
    public String toString() {
        return "Girl{" +
                "id=" + id +
                ", cupSize='" + cupSize + '\'' +
                ", age=" + age +
                '}';
    }
}
