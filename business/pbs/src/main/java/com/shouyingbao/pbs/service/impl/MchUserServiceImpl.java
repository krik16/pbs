package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.MchUser;
import com.shouyingbao.pbs.service.MchUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:47
 **/
public class MchUserServiceImpl extends BaseServiceImpl implements MchUserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MchUserServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.MchUserMapper";

    @Override
    public void insert(MchUser mchUser) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",mchUser);
    }

    @Override
    public void update(MchUser mchUser) {
        this.getBaseDao().insertBySql(NAMESPACE+".updateByPrimaryKeySelective",mchUser);
    }

    @Override
    public MchUser selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<MchUser> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }
}
