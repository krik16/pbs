package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.AliMch;

/**
 * kejun
 * 2016/3/29 11:43
 **/
public interface AliMchService {

    void insert(AliMch aliMch);

    void update(AliMch aliMch);

    AliMch selectById(Integer id);

    AliMch selectByShopId(Integer shopId);
}
