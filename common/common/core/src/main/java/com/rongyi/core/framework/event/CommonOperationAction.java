package com.rongyi.core.framework.event;

import com.rongyi.core.framework.event.BusinessOperationEvent.Action;

/**
 * 
 * ClassName: CommonOperationAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 操作动作<br/>
 * date: 2015年4月28日 下午4:25:06 <br/>
 * 
 * @author zeuskingzb
 * @version
 * @since JDK 1.6
 */
public enum CommonOperationAction implements Action {

  /****
   * 业务动作即将开始执行
   */
  Before("Before"),

  /****
   * 业务处理完成
   */
  Completed("Completed"),

  /****
   * 业务处理失败
   */
  Failed("Failed"), ;

  @Override
  public String getAction() {
    return this.action;
  }

  private String action;

  private CommonOperationAction(String action) {
    this.action = action;
  }


}
