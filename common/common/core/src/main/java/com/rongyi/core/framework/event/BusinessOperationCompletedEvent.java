package com.rongyi.core.framework.event;

/**
 * 
 * ClassName: BusinessOperationCompletedEvent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 业务处理结束事件。 <br/>
 * date: 2015年4月28日 下午4:21:32 <br/>
 * 
 * @author bin.zhang
 * @version @param <S>
 * @since JDK 1.6
 */
public class BusinessOperationCompletedEvent<S> extends BusinessOperationEvent<S> {

  public BusinessOperationCompletedEvent(BusinessOperation operation, S source) {
    super(operation, CommonOperationAction.Completed, source);
  }

  /**
	 * 
	 */
  private static final long serialVersionUID = -2955968165155860532L;

}
