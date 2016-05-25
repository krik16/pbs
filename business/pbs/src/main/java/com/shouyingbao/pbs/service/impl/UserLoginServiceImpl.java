package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.entity.Authority;
import com.shouyingbao.pbs.entity.Role;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.AuthorityService;
import com.shouyingbao.pbs.service.RoleService;
import com.shouyingbao.pbs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * kejun
 * 2016/3/14 15:50
 **/
@Service
public class UserLoginServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        try {
            UserDetails userDetails = null;
            User user = userService.selectByUserAccount(userAccount);
            if (user != null) {
                userDetails = new org.springframework.security.core.userdetails.User(user.getUserAccount(), user.getUserPwd().toLowerCase(), true, true, true, true,
                        getAuthorities(user.getId()));
            }
            return userDetails;
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
      return null;
    }
    /**
     * 获得访问角色权限
     *
     * @param userId
     * @return
     */
    public Collection<GrantedAuthority> getAuthorities(Integer userId) {

        List<GrantedAuthority> authList = new ArrayList<>();
        List<Role> roleList = roleService.selectByUserId(userId);
        for (Role role :roleList){
            List<Authority> authorityList = authorityService.selectByRoleId(role.getId());
            for (Authority authority:authorityList){
                authList.add(new SimpleGrantedAuthority(authority.getValue()));
            }
        }
        LOGGER.debug("user have auths={}",authList.toString());
        return authList;
    }
}
