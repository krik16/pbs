package com.shouyingbao.pbs.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * kejun
 * 2016/3/15 13:44
 **/
public class BaseController {

    //获取用户信息
    public UserDetails getUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails;
    }

}
