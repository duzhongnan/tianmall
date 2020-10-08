package com.dzn.mall.modules.ums.service.impl;

import com.dzn.mall.common.Asserts;
import com.dzn.mall.dao.UmsAdminRoleRelationDao;
import com.dzn.mall.mapper.ums.UmsAdminLoginLogMapper;
import com.dzn.mall.mapper.ums.UmsAdminMapper;
import com.dzn.mall.model.*;
import com.dzn.mall.modules.ums.bo.AdminUserDetails;
import com.dzn.mall.modules.ums.dto.UmsRegisterParam;
import com.dzn.mall.modules.ums.service.UmsAdminService;
import com.dzn.mall.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author duzhongnan
 * @since 2020-09-25
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    UmsAdminMapper adminMapper;
    @Autowired
    UmsAdminLoginLogMapper adminLoginLogMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UmsAdminRoleRelationDao adminRoleRelationDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 注册新用户
     *
     * @param umsRegisterParam
     * @return
     */
    @Override
    public UmsAdmin register(UmsRegisterParam umsRegisterParam) {
        logger.info("UmsAdminServiceImpl:{}", umsRegisterParam.toString());
        UmsAdmin admin = new UmsAdmin();
        BeanUtils.copyProperties(umsRegisterParam, admin);
        admin.setCreateTime(new Date());
        admin.setStatus(1);
        //查询该用户是否已注册
        UmsAdmin adminExists = getAdminByUsername(umsRegisterParam.getUsername());
        if (adminExists != null) {
            return null;
        }
        //将密码加密
        String password = passwordEncoder.encode(umsRegisterParam.getPassword());
        admin.setPassword(password);
        adminMapper.insert(admin);
        return admin;
    }

    /**
     * 登录。成功登录后返回token
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            AdminUserDetails userDetails = getUserDetailsByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.ofFail("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                Asserts.ofFail("账号已被禁用");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            logger.warn("登录异常:" + e.getMessage());
        }
        return token;
    }

    @Override
    public AdminUserDetails getUserDetailsByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        List<UmsResource> resourceList;
        if (admin != null) {
            resourceList = adminRoleRelationDao.getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码不正确");
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample adminExample = new UmsAdminExample();
        adminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    private void insertLoginLog(String username) {
        logger.info("insertLoginLog:{}"+username);
        UmsAdmin admin = getAdminByUsername(username);
        if (admin == null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpRequest = servletRequestAttributes.getRequest();
        loginLog.setIp(httpRequest.getRemoteAddr());
        adminLoginLogMapper.insert(loginLog);
    }
}
