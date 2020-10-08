package com.dzn.mall.modules.ums.controller;


import com.dzn.mall.common.Result;
import com.dzn.mall.common.ResultCode;
import com.dzn.mall.model.UmsAdmin;
import com.dzn.mall.modules.ums.dto.UmsAdminLoginParam;
import com.dzn.mall.modules.ums.dto.UmsRegisterParam;
import com.dzn.mall.modules.ums.service.UmsAdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author duzhongnan
 * @since 2020-09-25
 */
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    private static final Logger logger = LoggerFactory.getLogger(UmsAdminController.class);
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Autowired
    UmsAdminService adminService;

    /**
     * 获取指定用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<UmsAdmin> getItem(Long id){
        return Result.ofFailed();
    }

    /**
     * 注册新用户
     * @param registerParam
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result<UmsAdmin> register(@Validated @RequestBody UmsRegisterParam registerParam){
        logger.info("start to register, and param is {}",registerParam);
        UmsAdmin admin = adminService.register(registerParam);
        if(admin==null){
            return Result.ofFailed(ResultCode.FAILED, "注册用户失败");
        }
        return Result.ofSuccess(admin);
    }

    /**
     * 登陆，若成功则返回token
     * @param loginParam
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@Validated @RequestBody UmsAdminLoginParam loginParam){
        String token = adminService.login(loginParam.getUsername(),loginParam.getPassword());
        if(token==null){
            return Result.ofFailed("用户名或密码错误");
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return Result.ofSuccess(map);
    }
}

