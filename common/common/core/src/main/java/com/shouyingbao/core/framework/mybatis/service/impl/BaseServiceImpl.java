package com.shouyingbao.core.framework.mybatis.service.impl;

import com.shouyingbao.core.framework.mybatis.dao.BaseDao;
import com.shouyingbao.core.framework.mybatis.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BaseServiceImpl implements BaseService {

  @Autowired(required = false)
  @Qualifier(value = "baseDao")
  private BaseDao baseDao;

  @Override
  public BaseDao getBaseDao() {
    return this.baseDao;
  }
}
