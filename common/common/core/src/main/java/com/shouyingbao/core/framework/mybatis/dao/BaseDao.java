package com.shouyingbao.core.framework.mybatis.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {

  <E> List<E> selectListBySql(String statementId);

  <E> List<E> selectListBySql(String statementId, Map<String, Object> parameterMap);

  <T> T selectOneBySql(String statementId);

  <T> T selectOneBySql(String statementId, Map<String, Object> parameterMap);

  int updateBySql(String statementId, Object object);

  int insertBySql(String statementId);

  int insertBySql(String statementId, Object object);

  int delete(String statementId, Object object);

  int count(String statementId, Object object);

  int count(String statementId, Map map);
}
