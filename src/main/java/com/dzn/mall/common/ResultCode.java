package com.dzn.mall.common;

/**
 * @author duzhongnan
 * @date 2020/9/23 20:35
 */
public enum ResultCode implements IErrorCode{
    SUCCESS(200, "成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或token已失效"),
    FORBIDDEN(403, "没有权限");
    /**
     * 状态码
     */
    private long code;
    /**
     * 消息
     */
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
