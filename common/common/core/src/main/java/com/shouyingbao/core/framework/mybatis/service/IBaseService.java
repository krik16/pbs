package com.shouyingbao.core.framework.mybatis.service;

import com.shouyingbao.core.common.Page;
import com.shouyingbao.core.common.PageList;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseService<T, PK extends Serializable> {

  /**
   * add
   * 
   * @param entity
   * @return
   */
  public int add(T entity) throws Exception;

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

}
