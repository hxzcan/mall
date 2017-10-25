package com.ican.common;

/**
 * 响应的返回码
 * 枚举类型
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/23 0023
 * Time: 14:35
 */
public enum  ResponseCode {
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private Integer code;
    private String desc;

    ResponseCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
