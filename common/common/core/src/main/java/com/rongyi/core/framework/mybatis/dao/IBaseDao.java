package com.rongyi.core.framework.mybatis.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.rongyi.core.common.Page;
import com.rongyi.core.common.PageList;

/**
 * DAO基础接口
 * 
 * @author dylan.lu
 * @date 2014年5月29日
 */
public interface IBaseDao<T, PK extends Serializable> {

  /**
   * add
   * 
   * @param entity
   * @return
   */
  public int add(T entity);

  /**
   * updateByPK
   * 
   * @param entity
   * @return
   * @throws Exception
   */
  public int updateByPK(T entity) throws Exception;

  /**
   * 查询全部
   * 
   * @return
   * @throws Exception
   */
  public List<T> queryAll() throws Exception;

  /**
   * queryByPK
   * 
   * @param id
   * @return
   * @throws Exception
   */
  public T queryByPK(PK id) throws Exception;

  /**
   * queryPage
   * 
   * @param page
   * @param paramMap
   * @return
   * @throws Exception
   */
  public PageList<T> queryPage(Page page, Map<String, Object> paramMap) throws Exception;

  /**
   * delete
   * 
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int delete(Map<String, Object> paramMap) throws Exception;

  /**
   * deleteByPK
   * 
   * @param id
   * @return
   * @throws Exception
   */
  public int deleteByPK(PK id) throws Exception;

  /**
   * 根据UUID查询实体
   * 
   * @param uuid
   * @return
   * @throws Exception
   */
  public T queryByUUID(String uuid) throws Exception;
}
