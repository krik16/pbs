package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.Iterator;
import java.util.Set;

/**
 * kejun
 * 2016/3/15 13:44
 **/
@Controller
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    UserService userService;

    /**
     * 获取登录用户名及权限信息
     *
     * @return
     */
    public UserDetails getUserDetails() {
        try {
            return (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception e) {
            LOGGER.error(ConstantEnum.EXCEPTION_USER_NOT_LOGIN.getValueStr());
//            e.printStackTrace();
            throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_LOGIN.getCodeStr(), ConstantEnum.EXCEPTION_USER_NOT_LOGIN.getValueStr());
        }
    }

    /**
     * 获取用户数据库相关信息
     *
     * @return
     */
    public User getUser() {
        return userService.selectByUserAccount(getUserDetails().getUsername());
    }

    /**
     * 获取分页长度
     *
     * @param pageTotal
     * @return
     */
    public Integer getRowCount(Integer pageTotal) {
        double total = Double.valueOf(pageTotal);
        return (int) Math.ceil(total / ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
    }

    public String getAuthority() {
        UserDetails userDetails = getUserDetails();
        Set<GrantedAuthority> authoritySet = (Set<GrantedAuthority>) userDetails.getAuthorities();
        Iterator<GrantedAuthority> iterator = authoritySet.iterator();
        if (iterator != null && iterator.hasNext())
            return iterator.next().getAuthority();
        return null;
    }
}
