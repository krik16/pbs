package com.rongyi.core.framework.ienum;

/**
 * ClassName: IEnum <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:枚举类型接口。 此接口规约为，枚举均通过int类型的值来表示。 此接口，定义了两个规约， 一是获取枚举对应的int表示值， 二是根据int值，获取其对应的枚举值。 <br/>
 * date: 2015年4月29日 下午8:03:24 <br/>
 * 
 * @author bin.zhang
 * @version
 * @since JDK 1.6
 */
public interface IEnum {
  /***
   * 获取枚举对应值。
   * 
   * @return
   */
  public int getValue();

}
