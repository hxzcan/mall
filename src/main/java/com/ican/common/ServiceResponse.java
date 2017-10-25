package com.ican.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 接口返回的数据形式
 * Created with IntelliJ IDEA
 * Created By Administrator
 * Date: 2017/10/23 0023
 * Time: 14:24
 */
//保证序列化json的时候，如果时null的对象，key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse<T> implements Serializable{
    private int status;//返回码
    private String msg;//返回的信息
    private T data;//返回的数据
    //构造方法私有，通过外部的public方法来调用

    private ServiceResponse(int status) {
        this.status = status;
    }

    private ServiceResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServiceResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServiceResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 返回码是否为0，是的话就成功
     *   @JsonIgnore 不在json数据中显示
     * @return
     */
    @JsonIgnore
    public boolean isSuccess(){
        return  this.status==ResponseCode.SUCCESS.getCode();
    }

    /**
     * 成功的公共的方法
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createBySuccess(){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createBySuccess(String successMsg){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),successMsg);
    }

    public static <T> ServiceResponse<T> createBySuccess(T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServiceResponse<T> createBySuccess(String successMsg,T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),successMsg,data);
    }

    /**
     * 失败的公共方法
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createByError(){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServiceResponse<T> createByError(String errorMsg){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }

    public static <T> ServiceResponse<T> createByError(int code,String errorMsg){
        return new ServiceResponse<T>(code,errorMsg);
    }
    /**
     * 参数不合法的公共方法
     * @param <T>
     * @return
     */
    public static <T> ServiceResponse<T> createIllegal(){
        return new ServiceResponse<T>(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }

    public static <T> ServiceResponse<T> createIllegal(String illegalMsg){
        return new ServiceResponse<T>(ResponseCode.ILLEGAL_ARGUMENT.getCode(),illegalMsg);
    }

}
