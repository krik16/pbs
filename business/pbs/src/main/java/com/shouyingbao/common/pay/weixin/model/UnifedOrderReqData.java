package com.shouyingbao.common.pay.weixin.model;

import com.shouyingbao.common.pay.weixin.util.Configure;
import com.shouyingbao.common.pay.weixin.util.MD5;
import com.shouyingbao.common.pay.weixin.util.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**	
 * @Author:  柯军
 * @Description: 统一下单接口请求参数model 
 * @datetime:2015年9月2日上午10:56:41
 *
 **/
public class UnifedOrderReqData {
    //每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String device_info = "";
    private String nonce_str = "";
    private String sign = "";
    private String body = "";
    private String detail = "";
    private String attach = "";
    private String out_trade_no = "";
    private String fee_type = "";
    private Integer total_fee = 0;
    private String spbill_create_ip = "";
    private String time_start = "";
    private String time_expire = "";
    private String goods_tag = "";
    private String notify_url = "";
    private String trade_type = "";
    private String product_id = "";
    private String limit_pay = "";
    private String openid = "";
    


    /**
     * @Author:  柯军
     * @Description: 初始化必要参数 
     * @datetime:2015年9月2日
     *
     **/
    public UnifedOrderReqData(String body,String outTradeNo,Integer totalFee,String notifyUrl,String timeStart,String timeExpire,String openid,
							  Configure configure){
        //微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(configure.getAppID());

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(configure.getMchID());

        //随机字符串，不长于32 位
        setNonce_str(genNonceStr());
        
        //商品描述
        setBody(body);
        
        //商户系统自己生成的唯一的订单号
        setOut_trade_no(outTradeNo);

        //总金额
        setTotal_fee(totalFee);
        
        //终端IP
        setSpbill_create_ip(configure.getIp());
        
        //异步通知地址
        setNotify_url(notifyUrl);
        
        //交易类型
        setTrade_type(configure.getTradeType());

//		if(ConstantEnum.WEIXIN_PAY_TRADE_TYPE_APP.getValueStr().equals(configure.getTradeType())) {
//			//交易起始时间
//			setTime_start(timeStart);
//
//			//交易失效时间
//			setTime_expire(timeExpire);
//		}

//		if(ConstantEnum.WEIXIN_PAY_TRADE_TYPE_JSAPI.getValueStr().equals(configure.getTradeType())) {
//			setOpenid(openid);
//		}

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(),configure.getKey());
        setSign(sign);//把签名数据设置到Sign这个属性中

    }
    
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }


    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    
    
    
    public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UnifedOrderReqData [appid=");
		builder.append(appid);
		builder.append(", mch_id=");
		builder.append(mch_id);
		builder.append(", device_info=");
		builder.append(device_info);
		builder.append(", nonce_str=");
		builder.append(nonce_str);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", body=");
		builder.append(body);
		builder.append(", detail=");
		builder.append(detail);
		builder.append(", attach=");
		builder.append(attach);
		builder.append(", out_trade_no=");
		builder.append(out_trade_no);
		builder.append(", fee_type=");
		builder.append(fee_type);
		builder.append(", total_fee=");
		builder.append(total_fee);
		builder.append(", spbill_create_ip=");
		builder.append(spbill_create_ip);
		builder.append(", time_start=");
		builder.append(time_start);
		builder.append(", time_expire=");
		builder.append(time_expire);
		builder.append(", goods_tag=");
		builder.append(goods_tag);
		builder.append(", notify_url=");
		builder.append(notify_url);
		builder.append(", trade_type=");
		builder.append(trade_type);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append(", limit_pay=");
		builder.append(limit_pay);
		builder.append(", openid=");
		builder.append(openid);
		builder.append("]");
		return builder.toString();
	}

	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
