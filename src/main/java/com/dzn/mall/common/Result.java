package com.dzn.mall.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duzhongnan
 * @date 2020/9/23 20:31
 */
public class Result<T> {
    /**
     * 状态码
     */
    private long code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 封装数据
     */
    private T data;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(){}

    public Result(long code, String message, T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    /**
     * 成功返回结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofSuccess(T data){
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofSuccess(String message, T data){
        return new Result<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 返回结果失败
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofFailed(IErrorCode errorCode){
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 返回结果失败
     * @param errorCode
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofFailed(IErrorCode errorCode, String message){
        return new Result<T>(errorCode.getCode(), message, null);
    }

    /**
     * 返回结果失败
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofFailed(String message){
        return new Result<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 返回结果失败
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofFailed(){
        return ofFailed(ResultCode.FAILED);
    }

    /**
     * 认证失败结果
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofUnauthorizedFiled(){
        return new Result<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }

    /**
     * 认证失败结果
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> ofUnauthorizedFiled(String message){
        return new Result<T>(ResultCode.UNAUTHORIZED.getCode(), message, null);
    }

    public static <T> Result<T> forbidden(T data){
        return new Result<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> Result<T> unauthorized(String message){
        return new Result<T>(ResultCode.UNAUTHORIZED.getCode(), message, null);
    }



}
