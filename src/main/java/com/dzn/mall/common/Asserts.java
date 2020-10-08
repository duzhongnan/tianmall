package com.dzn.mall.common;

/**
 * @author duzhongnan
 * @date 2020/9/27 17:33
 * 断言类，用于抛出各种api异常
 */
public class Asserts {
    public static void ofFail(String message){
        throw new ApiException(message);
    }
}
