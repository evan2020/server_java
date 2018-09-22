package com.shalou.demo.enums;

//定义枚举(统一状态码和信息)
public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(0,"成功"),
    FAIL(9999,"失败了"),
    MIN_SCHOOL(100,"你还在上小学吧"),
    MAX_SCHOOL(101,"你还在上初中吧"),
    ;

    //定义状态码
    private Integer code;

    //定义信息
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //get

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
