package com.dzn.mall.modules.ums.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author duzhongnan
 * @date 2020/9/25 18:25
 * 注册新用户所用参数
 */
@Getter
@Setter
public class UmsRegisterParam {
    /**
     * 用户名
     */
    @NotEmpty
    private String username;
    /**
     * 密码
     */
    @NotEmpty
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 备注信息
     */
    private String note;

}
