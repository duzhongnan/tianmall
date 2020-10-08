package com.dzn.mall.modules.ums.bo;

import com.dzn.mall.model.UmsAdmin;
import com.dzn.mall.model.UmsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duzhongnan
 * @date 2020/9/27 12:57
 * spring security需要的用户详情
 */
public class AdminUserDetails implements UserDetails {
    private UmsAdmin admin;
    private List<UmsResource> resourceList;

    public AdminUserDetails(UmsAdmin admin, List<UmsResource> resourceList){
        this.admin=admin;
        this.resourceList=resourceList;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resourceList.stream()
                .map(item -> new SimpleGrantedAuthority(item.getId()+":"+item.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getStatus().equals(1);
    }
}
