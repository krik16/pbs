package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.AliMch;
import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.service.AliMchService;
import com.shouyingbao.pbs.service.MchShopService;
import com.shouyingbao.pbs.service.WeixinMchService;
import com.shouyingbao.pbs.vo.MchShopVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:40
 **/
@Service
public class MchShopServiceImpl extends BaseServiceImpl implements MchShopService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MchShopServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.MchShopMapper";

    @Autowired
    AliMchService aliMchService;

    @Autowired
    WeixinMchService weixinMchService;

    @Override
    public void insert(MchShop mchShop) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",mchShop);
    }

    @Override
    public void update(MchShop mchShop) {
        this.getBaseDao().insertBySql(NAMESPACE+".updateByPrimaryKeySelective",mchShop);
    }

    @Override
    public void save(MchShop mchShop, AliMch aliMch, WeixinMch weixinMch) {
        if(mchShop.getId() == null){
            insert(mchShop);
            aliMch.setShopId(mchShop.getId());
            aliMchService.insert(aliMch);
            weixinMch.setShopId(mchShop.getId());
            weixinMchService.insert(weixinMch);

        }else {
            update(mchShop);
            if(aliMch.getId() != null) {
                aliMchService.update(aliMch);
            }else{
                aliMch.setShopId(mchShop.getId());
                aliMchService.insert(aliMch);
            }
            if(weixinMch.getId() != null) {
                weixinMchService.update(weixinMch);
            }else{
                weixinMch.setShopId(mchShop.getId());
                weixinMchService.insert(weixinMch);
            }
        }
    }

    @Override
    public MchShop selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<MchShopVO> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
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

    @Override
    public List<MchShop> selectByCompanyId(Integer companyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectByCompanyId", map);
    }

    @Override
    public List<MchShop> selectBySubCompanyId(Integer subCompanyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("subCompanyId",subCompanyId);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectBySubCompanyId",map);
    }

    @Override
    public List<MchShop> selectAllList(Map<String, Object> map) {
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectAllList",map);
    }

    @Override
    public List<MchShop> selectOnlySelf() {
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectOnlySelf");
    }
}
