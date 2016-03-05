package com.rongyi.core.framework.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.rongyi.core.framework.mybatis.dao.BaseDao;

public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {

  @Override
  public <E> List<E> selectListBySql(String statementId) {
    return this.getSqlSession().selectList(statementId);
  }

  @Override
  public <E> List<E> selectListBySql(String statementId, Map<String, Object> parameterMap) {
    return this.getSqlSession().selectList(statementId, parameterMap);
  }

  @Override
  public <T> T selectOneBySql(String statementId) {
    List<T> list = this.getSqlSession().selectList(statementId);
    if (list == null || list.size() <= 0) {
      return null;
    }
    return list.get(0);
  }

  @Override
  public <T> T selectOneBySql(String statementId, Map<String, Object> parameterMap) {
    List<T> list = this.getSqlSession().selectList(statementId, parameterMap);
    if (list == null || list.size() <= 0) {
      return null;
    }
    return list.get(0);
  }

  @Override
  public int updateBySql(String statementId, Object object) {
    return this.getSqlSession().update(statementId, object);
  }

  @Override
  public int insertBySql(String statementId) {
    return this.getSqlSession().insert(statementId);
  }

  @Override
  public int insertBySql(String statementId, Object object) {
    return this.getSqlSession().insert(statementId, object);
  }

  @Override
  public int delete(String statementId, Object object) {
    return this.getSqlSession().delete(statementId, object);
  }

  /**
   * TODO 简单描述该方法的实现功能（可选）.
   * 
   * @see com.rongyi.core.framework.mybatis.dao.BaseDao#count(java.lang.String, java.lang.Object)
   */
  @Override
  public int count(String statementId, Object object) {
    return this.getSqlSession().selectOne(statementId, object);
  }

  /**
   * TODO 简单描述该方法的实现功能（可选）.
   * 
   * @see com.rongyi.core.framework.mybatis.dao.BaseDao#count(java.lang.String, java.util.Map)
   */
  @Override
  public int count(String statementId, Map map) {
    return this.getSqlSession().selectOne(statementId, map);
  }
}
