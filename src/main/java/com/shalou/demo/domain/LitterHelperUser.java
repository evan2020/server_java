package com.shalou.demo.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//设置用户表
@Entity
@EntityListeners(AuditingEntityListener.class)
public class LitterHelperUser {
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
    private String openid;

    //用户昵称
    private String nickname;

    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String sex;

    //用户个人资料填写的省份
    private String province;

    //普通用户个人资料填写的城市
    private String city;

    //国家，如中国为CN
    private String country;

    //用户头像
    private String headimgurl;

    //用户特权信息
    private String privilege;

    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String unionid;

    //公众号config torken
    private String accessToken;

    //公众号config torken创建时间(时间戳)
    private String accessTokenTime;

    //公众号config jsapi_ticket
    private String jsapiTicket;

    //access_token 超时时间
    private String timeOut;

    //redirectSate 重定向携带的指定参数
    private String redirectSate;

    //--------------------------------------------------------------


    @Override
    public String toString() {
        return "LitterHelperUser{" +
                "createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", id=" + id +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", privilege='" + privilege + '\'' +
                ", unionid='" + unionid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenTime='" + accessTokenTime + '\'' +
                ", jsapiTicket='" + jsapiTicket + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", redirectSate='" + redirectSate + '\'' +
                '}';
    }

    public String getRedirectSate() {
        return redirectSate;
    }

    public void setRedirectSate(String redirectSate) {
        this.redirectSate = redirectSate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenTime() {
        return accessTokenTime;
    }

    public void setAccessTokenTime(String accessTokenTime) {
        this.accessTokenTime = accessTokenTime;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

}
