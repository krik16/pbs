package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.MchSubCompany;
import com.shouyingbao.pbs.service.MchSubCompanyService;
import com.shouyingbao.pbs.vo.MchSubCompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 17:52
 **/
@Service
public class MchSubCompanyServiceImpl extends BaseServiceImpl implements MchSubCompanyService{


    private static final Logger LOGGER = LoggerFactory.getLogger(MchCompanyServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.MchSubCompanyMapper";


    @Override
    public void insert(MchSubCompany mchSubCompany) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",mchSubCompany);
    }

    @Override
    public void update(MchSubCompany mchSubCompany) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective",mchSubCompany);
    }

    @Override
    public MchSubCompany selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<MchSubCompany> selectByCompanyId(Integer companyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        return this.getBaseDao().selectListBySql(NAMESPACE+".selectByCompanyId",map);
    }

    @Override
    public List<MchSubCompanyVO> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        if(currentPage != null && pageSize != null) {
            map.put("currentPage", (currentPage - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }
}
