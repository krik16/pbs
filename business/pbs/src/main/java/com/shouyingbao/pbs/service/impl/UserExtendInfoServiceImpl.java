package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.UserExtendInfo;
import com.shouyingbao.pbs.service.UserExtendInfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:59
 **/
@Service
public class UserExtendInfoServiceImpl extends BaseServiceImpl implements UserExtendInfoService{

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.UserExtendInfoMapper";

    @Override
    public void insert(UserExtendInfo userExtendInfo) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", userExtendInfo);
    }

    @Override
    public void update(UserExtendInfo userExtendInfo) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective",userExtendInfo);
    }

    @Override
    public UserExtendInfo selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public UserExtendInfo selectByUserId(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByUserId",map);
    }
}
