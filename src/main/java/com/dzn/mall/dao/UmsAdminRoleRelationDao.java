package com.dzn.mall.dao;

import com.dzn.mall.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author duzhongnan
 * @date 2020/9/27 17:11
 */
public interface UmsAdminRoleRelationDao {
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);
}
