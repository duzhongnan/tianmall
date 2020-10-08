package com.dzn.mall.common;

/**
 * @author duzhongnan
 * @date 2020/9/27 17:30
 * 自定义api exception类
 */
public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
