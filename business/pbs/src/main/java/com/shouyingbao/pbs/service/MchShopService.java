package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.AliMch;
import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.vo.MchShopVO;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:39
 **/
public interface MchShopService {

    void insert(MchShop mchShop);

    void update(MchShop mchShop);

    void save(MchShop mchShop,AliMch aliMch,WeixinMch weixinMch);

    MchShop selectById(Integer id);

    List<MchShopVO> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);

    List<MchShop> selectByCompanyId(Integer companyId);

    List<MchShop> selectBySubCompanyId(Integer subCompanyId);

    List<MchShop> selectAllList();

    List<MchShop> selectOnlySelf();
}
