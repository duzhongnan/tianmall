package com.dzn.mall.modules.ums.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author duzhongnan
 * @date 2020/9/26 21:59
 * 登录所用参数
 */
@Getter
@Setter
public class UmsAdminLoginParam {
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

}
