package com.rongyi.core.framework.event;

/**
 * 
 * ClassName: BusinessOperationBeforeEvent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 业务处理即将开始。 <br/>
 * date: 2015年4月28日 下午4:21:51 <br/>
 * 
 * @author bin.zhang
 * @version @param <S>
 * @since JDK 1.6
 */
public class BusinessOperationBeforeEvent<S> extends BusinessOperationEvent<S> {

  private static final long serialVersionUID = -6570770545271413543L;

  public BusinessOperationBeforeEvent(BusinessOperation operation, S source) {
    super(operation, CommonOperationAction.Before, source);
  }
}
