package com.shouyingbao.core.framework.mybatis.dao.impl;

import com.shouyingbao.core.common.Page;
import com.shouyingbao.core.common.PageList;
import com.shouyingbao.core.common.util.ReflectionUtils;
import com.shouyingbao.core.framework.mybatis.dao.IBaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class GenericIbatisDao<T, PK extends Serializable> extends SqlSessionDaoSupport
    implements IBaseDao<T, PK> {

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

  public GenericIbatisDao() {
    this.entityClass = ReflectionUtils.getSuperClassGenericType(getClass());
    this.entityClassName = this.entityClass.getSimpleName();

    logger.debug("GenericIbatisDao() " + entityClassName);
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
   * 自动注入
   */
  @Autowired
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    super.setSqlSessionFactory(sqlSessionFactory);
  }

  /**
   * 增加
   * 
   * @return
   * @throws Exception
   */
  public int add(T entity) {
    int result = -1;
    try {
      result = this.getSqlSession().insert(this.insert, entity);
    } catch (Exception e) {
      logger.error("新增失败！" + e);
    }
    return result;
  }



  /**
   * 根据主键更新信息
   * 
   * @return
   * @throws Exception
   */
  public int updateByPK(T entity) {
    int result = -1;
    try {
      result = this.getSqlSession().update(this.updateByPK, entity);
    } catch (Exception e) {
      logger.error("更新失败！" + e);
    }
    return result;
  }


  /**
   * 查询所有
   * 
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<T> queryAll() throws Exception {
    logger.debug("GenericIbatisDao() " + entityClassName);
    return this.getSqlSession().selectList(this.queryAll);
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
    return this.getSqlSession().selectOne(this.queryByPK, id);
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

    int totalCount = ((Integer) this.getSqlSession().selectOne(this.count, paramMap)).intValue();

    page.setTotalCount(totalCount);

    if (paramMap == null) {
      paramMap = new HashMap<String, Object>();
    }

    paramMap.put("startIndex", Integer.valueOf(page.getStartIndex()));// 设置起始
    paramMap.put("pageSize", Integer.valueOf(page.getPageSize()));// 设置结束

    List<T> list = this.getSqlSession().selectList(this.queryPage, paramMap);// 查询结果

    PageList<T> pagingList = new PageList<T>(list, page);

    return pagingList;
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
    int influencdRow = 0;

    influencdRow = getSqlSession().delete(this.delete, paramMap);

    return influencdRow;
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int deleteByPK(PK id) throws Exception {

    int influencdRow = 0;

    influencdRow = getSqlSession().delete(this.deleteByPK, id);

    return influencdRow;
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
    return this.getSqlSession().selectOne(this.queryByUUID, uuid);
  }

}
