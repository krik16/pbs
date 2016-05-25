package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.UserExtendInfo;

/**
 * kejun
 * 2016/4/14 10:24
 **/
public interface UserExtendInfoService {

    void insert(UserExtendInfo userExtendInfo);

    void update(UserExtendInfo userExtendInfo);

    UserExtendInfo selectById(Integer id);

    UserExtendInfo selectByUserId(Integer userId);

}
