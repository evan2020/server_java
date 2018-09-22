package com.shalou.demo.exception;

import com.shalou.demo.enums.ResultEnum;

//捕获异常信息(引用枚举的定义内容)
public class GirlException extends RuntimeException{
    private Integer code;

    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
