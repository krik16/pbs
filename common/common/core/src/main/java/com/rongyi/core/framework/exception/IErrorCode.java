package com.rongyi.core.framework.exception;

/****
 * 通用的异常代码接口。 该接口规约为，所有异常，以一个异常代码，和异常描述信息组成。
 * 
 * @author bin.zhang
 *
 */
public interface IErrorCode extends java.io.Serializable, java.lang.Cloneable {

    /****
     * 获取异常代码。
     * 
     * @return
     */
    public String getErrorCode();

    /****
     * 获取异常描述信息。
     * 
     * @return
     */
    public String getErrorMsg();

}
