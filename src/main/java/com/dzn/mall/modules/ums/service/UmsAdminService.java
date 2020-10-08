package com.dzn.mall.modules.ums.service;

import com.dzn.mall.model.UmsAdmin;
import com.dzn.mall.modules.ums.bo.AdminUserDetails;
import com.dzn.mall.modules.ums.dto.UmsRegisterParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author duzhongnan
 * @since 2020-09-25
 */
public interface UmsAdminService{
    UmsAdmin register(UmsRegisterParam umsRegisterParam);
    String login(String username, String password);
    AdminUserDetails getUserDetailsByUsername(String username);
    UmsAdmin getAdminByUsername(String username);

}
