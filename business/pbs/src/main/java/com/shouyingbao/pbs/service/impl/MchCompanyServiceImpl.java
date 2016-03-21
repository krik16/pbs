package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.service.MchCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 17:25
 **/
@Service
public class MchCompanyServiceImpl extends BaseServiceImpl implements MchCompanyService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MchCompanyServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.MchCompanyMapper";


    @Override
    public void insert(MchCompany mchCompany) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",mchCompany);
    }

    @Override
    public void update(MchCompany mchCompany) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective",mchCompany);
    }

    @Override
    public MchCompany selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<MchCompany> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }
}
