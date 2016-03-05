package com.rongyi.core.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ClassName: PageList <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 分页对象 <br/>
 * date: 2015年7月20日 上午11:07:21 <br/>
 * 
 * @author bin.zhang
 * @version @param <T>
 * @since JDK 1.6
 */
@Deprecated
public class PageList<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<T> list;

  private int total;// 总条数

  private Page page;

  public PageList(List<T> list, Page page) {
    this.list = list;
    this.page = page;
    this.total = page.getTotalCount();
  }

  public PageList() {
    this.list = new ArrayList<T>();
    this.page = new Page();
    this.total = page.getTotalCount();
  }

  public List<T> getList() {
    return this.list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public Page getPage() {
    return this.page;
  }

  public void setPage(Page page) {
    this.page = page;
    this.total = page.getTotalCount();
  }

  public String toString() {
    return "PageList [list=" + this.list + ", page=" + this.page + "]";
  }

  /**
   * 
   * 前端分页
   * 
   * @author bdq.bao
   * @date 2014年9月5日 下午3:28:56
   */
  public List<T> getObjects(int pageNo) {
    int pageEndRow = 0;
    if (pageNo == 0)
      page.setPageNo(pageNo);
    else
      page.setPageNo(pageNo);
    if (page.isHasNext()) {// 判断是否为最后一页
      pageEndRow = pageNo * page.getPageSize();
    } else {
      pageEndRow = page.getTotalCount();
    }

    List<T> objects = null;
    if (!list.isEmpty()) {
      objects = list.subList(page.getStartIndex(), pageEndRow);
    }
    return objects;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

}
