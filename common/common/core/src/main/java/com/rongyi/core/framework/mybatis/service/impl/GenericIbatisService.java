package com.rongyi.core.framework.mybatis.service.impl;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rongyi.core.common.Page;
import com.rongyi.core.common.PageList;
import com.rongyi.core.common.util.ReflectionUtils;
import com.rongyi.core.framework.mybatis.dao.IBaseDao;
import com.rongyi.core.framework.mybatis.service.IBaseService;

@Transactional
public abstract class GenericIbatisService<T, PK extends Serializable> implements
    IBaseService<T, PK> {

  @Autowired
  private IBaseDao<T, PK> baseDao;

  private Class<T> entityClass;
  protected String entityClassName;
  public String queryAll;
  public String queryByPK;
  public String delete;
  public String deleteByPK;
  public String insert;
  public String count;
  public String queryPage;
  public String updateByPK;
  public String queryByUUID;

  public GenericIbatisService() {
    this.entityClass = ReflectionUtils.getSuperClassGenericType(getClass());
    this.entityClassName = this.entityClass.getSimpleName();

    this.count = MessageFormat.format("{0}.count", new Object[] {this.entityClassName});
    this.queryAll = MessageFormat.format("{0}.queryAll", new Object[] {this.entityClassName});
    this.queryByPK = MessageFormat.format("{0}.queryByPK", new Object[] {this.entityClassName});
    this.delete = MessageFormat.format("{0}.delete", new Object[] {this.entityClassName});
    this.deleteByPK = MessageFormat.format("{0}.deleteByPK", new Object[] {this.entityClassName});
    this.insert = MessageFormat.format("{0}.insert", new Object[] {this.entityClassName});
    this.queryPage = MessageFormat.format("{0}.queryPage", new Object[] {this.entityClassName});
    this.updateByPK = MessageFormat.format("{0}.updateByPK", new Object[] {this.entityClassName});
    this.queryByUUID = MessageFormat.format("{0}.queryByUUID", new Object[] {this.entityClassName});
  }

  public void initialize() throws Exception {

  }


  /**
   * 增加
   * 
   * @return
   * @throws Exception
   */
  public int add(T entity) {
    Assert.notNull(entity);
    return this.baseDao.add(entity);
  }



  /**
   * 根据主键更新信息
   * 
   * @return
   * @throws Exception
   */
  public int updateByPK(T entity) throws Exception {
    Assert.notNull(entity);
    return this.baseDao.updateByPK(entity);
  }


  /**
   * 查询所有
   * 
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<T> queryAll() throws Exception {
    return this.baseDao.queryAll();
  }

  /**
   * 根据主键查询
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public T queryByPK(PK id) throws Exception {
    Assert.notNull(id);
    return this.baseDao.queryByPK(id);
  }

  /**
   * 分页查询
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public PageList<T> queryPage(Page page, Map<String, Object> paramMap) throws Exception {
    Assert.notNull(page);


    return this.baseDao.queryPage(page, paramMap);
  }

  /**
   * 删除
   * 
   * @param entity
   * @return influencdRow
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int delete(Map<String, Object> paramMap) throws Exception {
    return this.baseDao.delete(paramMap);
  }

  /**
   * 根据主键删除
   * 
   * @param id
   * @return influencdRow
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int deleteByPK(PK id) throws Exception {
    return this.baseDao.deleteByPK(id);
  }

  /**
   * 根据UUID查询
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public T queryByUUID(String uuid) throws Exception {
    return this.baseDao.queryByUUID(uuid);
  }

}
