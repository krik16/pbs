package com.shouyingbao.core.common.third.param.user;/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  
 * time:  2015/9/15
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/9/15              1.0            创建文件
 *
 */


import java.io.Serializable;

public class UserThirdParam  implements Serializable {


  private String data;  //请求参数

  private String timeStamp;//请求

  private String sign; //签名

  private String channel; //请求渠道

 // private String couponId;//红包Id 优惠卷Id


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


}
