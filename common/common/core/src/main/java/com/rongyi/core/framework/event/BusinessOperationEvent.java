package com.rongyi.core.framework.event;

import com.rongyi.core.framework.exception.IllegalParamterException;

/**
 * 
 * ClassName: BusinessOperationEvent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年4月28日 下午4:19:40 <br/>
 * 
 * @author bin.zhang
 * @version @param <S>
 * @since JDK 1.6
 */
public class BusinessOperationEvent<S> implements Event<S> {

  public static final String OPERATION_SEPARTOR = "_";

  public static final String BUSINESS_OPERATION = "BUSINESS_OPERATION";

  protected String type;

  protected long occuredTime = System.currentTimeMillis();

  protected S source;

  protected Action action;

  protected BusinessOperation operation;

  /****
   * 产生该事件的业务Action
   * 
   * @author bin.zhang
   *
   */
  public static interface Action extends java.io.Serializable {

    public String getAction();
  }

  public BusinessOperationEvent(BusinessOperation operation, Action action, S source) {
    if (operation == null || action == null || source == null) {
      throw new IllegalParamterException();
    }
    this.source = source;
    this.action = action;
    this.operation = operation;
    this.type =
        BUSINESS_OPERATION + OPERATION_SEPARTOR + operation.getOperation() + OPERATION_SEPARTOR
            + action.getAction();
  }



  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 319113704412167072L;



  @Override
  public String getType() {
    return type;
  }



  @Override
  public long getOccuredTime() {
    return occuredTime;
  }



  @Override
  public S getSource() {
    return source;
  }



  public Action getAction() {
    return action;
  }



  public BusinessOperation getOperation() {
    return operation;
  }


}
