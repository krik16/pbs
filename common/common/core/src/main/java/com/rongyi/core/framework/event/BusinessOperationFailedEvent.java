package com.rongyi.core.framework.event;

import com.rongyi.core.framework.exception.IllegalParamterException;
import com.rongyi.core.framework.exception.RYServiceException;

/**
 * 
 * ClassName: BusinessOperationFailedEvent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 业务操作失败事件。业务动作失败，必有其失败原因。 其失败原因，以定义的业务异常描述。 <br/>
 * date: 2015年4月28日 下午4:24:42 <br/>
 * 
 * @author bin.zhang
 * @version @param <S>
 * @since JDK 1.6
 */
public class BusinessOperationFailedEvent<S> extends BusinessOperationEvent<S> {

  public BusinessOperationFailedEvent(BusinessOperation operation, S source,
      RYServiceException cause) {
    super(operation, CommonOperationAction.Failed, source);
    if (cause == null) {
      throw new IllegalParamterException();
    }
    this.cause = cause;
  }

  /**
	 * 
	 */
  private static final long serialVersionUID = -6421807859604513487L;

  private RYServiceException cause;

  public RYServiceException getCause() {
    return cause;
  }



}
