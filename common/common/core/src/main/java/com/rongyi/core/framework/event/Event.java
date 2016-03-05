package com.rongyi.core.framework.event;

/**
 * 
 * ClassName: Event <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年4月28日 下午4:20:06 <br/>
 * 
 * @author bin.zhang
 * @version @param <S>
 * @since JDK 1.6
 */
public interface Event<S> extends java.io.Serializable {

  /****
   * get事件类型。
   */
  public String getType();

  /****
   * get发生时间
   */
  public long getOccuredTime();

  /****
   * get事件源
   */
  public S getSource();


}
