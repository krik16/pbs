package com.rongyi.core.enumerate;

import java.util.HashMap;

import com.rongyi.core.sms.SmsConfig;

/**
 * 错误码常量
 * @author baodk
 */
public enum CodeEnum {
    
    SUCCESS("0", "正常"),
    
    /** 系统性错误 */
    //ERROR_ID_INVALID("-1", "调用者ID不存在或无效"),
    ERROR_ID_INVALID("-1", "请重新登录"),
    ERROR_PASSWORD("-2", "调用者密码出错"),
    ERROR_ID_ILLEGAL("-3", "调用方ID不合法 "),
    ERROR_VARSION("-4", "版本不匹配"),
    ERROR_SYSTEM("-11", "网络正在打瞌睡，请稍后再试"),
    ERROR_DATABASE("-12", "数据库故障"),
    ERROR_PARAM("-13", "必要参数为空或不合法"),
    ERROR_PARAM_1301("-1301", "必填参数不能为空"),

    ERROR_PARAM_CHAR("-78","包含特殊字符"),

    /** 正常 但没有数据 **/
    SUCCESS_NOINFO("-300","未查询到数据"),
    SUCCESS_RMMMEXCEPTION("-301","业务异常_具体的明细具体输出"),
    SUCCESS_MALLSHOPEXCEPTION("-302","摩店异常业务异常_具体的明细具体输出"),

    /** 注册用户 */
    ERROR_PHONE("-1101", "手机号已注册"),
    ERROR_QQ("-1102", "qq号已注册"),
    ERROR_WECHAT("-1103", "微信号已注册"),
    ERROR_EMAIL("-1104", "邮箱已注册"),
    ERROR_NICKNAME("-1105","昵称已经存在不能注册"),
    ERROR_UPGRADENICKNAME("-1106","已有相同昵称存在,请更换"),
    ERROR_TELEPHONEEXISIT("-1109","电话号码不能为空"),
    ERROR_PHONEEXISIT("-1110","手机号码已存在"),
    ERROR_USERSTATUS("-1111", SmsConfig.SERVER_TELPHONE),
    
    /**扫码错误信息*/
    ERROR_RECEIVECOUPONMSG("-1125","亲，手慢了，奖品被抢光了……"),
    ERROR_RECEIVECOUPONMSGCOUNT("-1129","对不去你的领取次数超过了限制"),
    /** 验证码无效或已过期 */
    ERROR_VERIFICATION_CODE("-1106","验证码无效或已过期 "),
    
    /** Json错误提示*/
    ERROR_TOJSON("-1107","转换json字符失败!"),
    ERROR_JSONTOOBJECT("-1108","将json字符转换为对象时失败!"),
    /** 查询 更新 修改累 */
    ERROR_PHONE_INVALID("-1111", "指定手机号不存在或无效"),
    ERROR_QQ_INVALID("-1112", "指定QQ号不不存在或无效"),
    ERROR_WECHAT_INVALID("-1113", "指定微信号不存在或无效"),
    ERROR_EMAIL_INVALID("-1114", "指定邮箱不存在或无效"),
    ERROR_MEMBER_INVALID("-1115", "指定会员编号不存在或无效"),
    ERROR_IDENTITY_INVALID("-1116", "指定身份证不存在或无效"),
    ERROR_CARD_INVALID("-1117", "开卡方编号不正确或无效"),
    ERROR_DATE_INVALID("-1118", "指定时间格式不正确或无效"),
    ERROR_SUGGESTION_INVALID("-1119", "反馈内容不不存在或无效"),
    ERROR_ASSISTANT_INVALID("-1120", "不是未审核的店员"),
    ERROR_INVALID_MALLID("-1121", "无效的商场id"),
    ERROR_NO_SHOP("-1122", "没有相关的店铺信息"),
    ERROR_INVALID_LETTER("-1123", "无效的首字母"),
    ERROR_NO_USER_SHOP("-1124", "该用户没有关联的店铺"),
    ERROR_EXCLUSIVE_GUIDE("-1126","设置专属导购出错"),
    ERROR_CANCEL_EXCLUSIVE_GUIDE("-1127","取消专属导购出错"),
    ERROR_EXCLUSIVE_GUIDE_LIST("-1128","专属导购列表当前页无数据"),
    ERROR_CREATE_BRAND_MALL("-1129","创建品牌商场关联表出错"),
    ERROR_INSERT_BRAND("-1130","插入品牌信息出错"),
    ERROR_INSERT_MALL("-1131","插入商场信息出错"),
    ERROR_INSERT_SHOP("-1132","插入店铺信息出错"),
    /** 密码修改 */
    ERROR_ORIGINAL_PASSOWRD("-1212", "原密码不正确"),
    ERROR_NEW_INVALID("-1213", "新密码无效"),
    ERROR_EXCEED_RESTRICTION("-1214", "同一天找回密码次数超限制"),
  
    /** 版本更新 */
    SUCCESS_UNNECESSARIES_UPDATE("-1310","当前版本不需要更新"),
    
    /** 积分 */
    ERROR_INTEGRAL_INVALID("-1301", "无效积分参数"),
    ERROR_INTEGRAL_LESS("-1302", "当前积分不够减扣"),
    
    /** 上传消费记录 */
    ERROR_TERMINAL_INVALID("-2001", "终端号无效"),
    ERROR_SERIAL_INVALID("-2002", "系统流水号无效"),
    ERROR_TRADE_DATE_INVALID("-2003", "交易日期参数无效"),
    ERROR_TRADE_MONEY_INVALID("-2004", "交易金额参数无效"),
    ERROR_TERMINAL_SHOP_INVALID("-2005", "找不到终端号对应的店铺号"),
    ERROR_SERIAL_REPEAT("-2006", "系统流水号重复"),
    
    /** MCMC商品 */
    ERROR_MCMC_COMMODITY_INVALID("-7001", "商品号无效"),
    ERROR_MCMC_COMMODITY_SORTBY("-7002", "未知的商品排序方式"),
    ERROR_MCMC_SHOP_NO_COMMODITY("-7003", "未找到此店铺相关商品"),
    ERROR_MCMC_COMMODITY_NO_COMMENT("-7004", "未找到此商品相关评论"),
    ERROR_COMMENT_SORTBY("-7005", "未知的评论排序方式"),
    ERROR_NOMORE_COMMODITY("-7006", "没有更多的商品了"),
    ERROR_MCMC_COMMODITY_CODE_USED("-7007", "商品编号重复"),
    ERROR_MCMC_COMMODITY_DUPLICATED_SPEC("-7008", "商品规格重复"),
    /** 百联优惠券常量 */
    ERROR_BUYED_OUT("-3007","优惠券已经被领完"),


    /** 环信IM 错误提示*/
    ERROR_REGISTER_TOKEN("-1401","注册环信账号获取TOKEN,系统故障"),
    ERROR_REGISTER_SYSTEM("-1402","注册环信账号,系统故障"),
    ERROR_BE_REGISTERED("-1403","此用户已注册过IM账号"),
    ERROR_RESETPWD("-1404","重置IM密码系统故障"),
    ERROR_RESETNICKNAME("-1405","重置IM昵称系统故障"),
    /** 绑定帐户 错误提示  */
    ERROR_UNBOUNDED("-1501","当前用户未绑定银行或支付宝帐户信息"),
    /** 用户地址错误提示 */
    ERROR_NO_ADDRESS("-1602","该用户未设置地址，请重新设置"),
    DEFAULT_ADDRESS_UNBOUNDED("-1601","该用户未设置默认地址"),
    /** 购物车错误提示*/
    ERROR_COMMODITY_EXIST("-1701","购物车中已存在该商品"),
    ERROR_COMMODITY_INEXIST("-1702","购物车中没有该商品"),
    ERROR_COMMODITY_MORE("-1703","库存不足"),
    /** 申请退款*/
    ERROR_REFUND_OVER("-1801","退款总金额大于总价"),
    ERROR_REFUND_MAXOVER("-1802","退款次数已达上限"),
    /** 收藏品牌错误提示*/
    ERROR_BRAND_COLLECTED("-1901","该品牌已经被收藏"),
    /** 生成订单错误提示*/
    ERROR_COMMODITY_OVER("-1911","商品库存不足"),
    ERROR_COMMODITY_LOWER("-1912","购买商品数量小于1"),
    ERROR_COMMODITY_NOADDRESS("-1913","没有邮寄地址"),
    ERROR_COMMODITY_SOLDOUT("-1914","商品已下架"),
    ERROR_COMMODITY_NOTFOUND("-1915","商品不存在"),
    ERROR_ORDER_NOTFOUND("-1916","订单不存在"),
    ERROR_ORDER_ERRORCREATE("-1917","生成订单模板失败"),
    /** GCC错误提示*/
    ERROR_GCC("-2101", "GCC异常"),
    /** 申请支付错误提示*/
    ERROR_PAYMENT_STATUS("-3001","不是未付款状态"),
    /** 申请维权错误提示 */
    ERROR_APEAL_MAXOVER("-4001","维权次数已达上限"),
    /** 子订单评价错误提示*/
    ERROR_EVALUATE_EXIST("-5001","已评价"),
    /** 红包优惠券错误提示*/
    ERROR_COUPON_OUTDATE("-6001","此红包不可用"),
    ERROR_COUPON_REPEAT("-6002","一张红包只能使用一次"),
    ERROR_COUPON_PUBLISH_OVER("-6003","每日限量发行数填写错误"),
    ERROR_COUPON_PUBLISH_CHANNEL("-6004","亲，活动券你不能管理哦"),

    ERROR_ACCOUNT_INSUFFICIENT_BALANCE("-7001","账户余额不足"),
    ERROR_ACCOUNT_NO_PERMISSION("-7002","账户被冻结或未授权"),
    ERROR_ACCOUNT_DRAW_TIMES("-7003","当日提款次数达上限"),
    
    ERROR_INVITE_CODE("-8001","邀请码不正确，请重新输入"),
    ERROR_NULL_INVITE_CODE("-8002","邀请码不能为空,请重新输入"),

    ERROR_DEBIT_NOTE("-9000","佣金小票上传失败，请重新上传"),
    ERROR_DEBIT_NOTE_STAUTS("-9001","佣金小票审核中，无法再次上传"),
    ERROR_NO_VALIDHISTORY("-9002","没有券码核销记录"),
    ERROR_SWEEP_CODE("-9003","扫码验券失败"),
    ERROR_VALID_CODE("-9004","验码失败"),
    SUCCESS_VALID_CODE("-9005","优惠券码验证成功"),
    
    ERROR_LOGIN_IN_INVALID("-2000","登录出错"),
    ERROR_UN_LOGIN("-10000", "尚未登陆或登陆出错，请重新登录"),
    ERROR_TAG_NUM_OUT("-10001", "标签数目超过最大数目"),
    
    ERROR_CODE("-20001","券码有误"),
    ERROR_CODE_USED("-20002","券码已使用"),
    ERROR_CODE_OUTDATE("-20003","券码已过期或已失效"),
    ERROR_CODE_REFUND("-20004","券码状态异常"),
    ERROR_CODE_NOHEAR("-20005","本店暂未发行该类优惠券"),
    
    ERROR_NOTICE_TYPE("1063001","对店铺公告点赞或取消赞类型错误"),
    ERROR_PRAISE_NOTICE("1063002","对店铺公告点赞或取消赞失败"),
    ERROR_SAVE_NOTICE("1073003","新建店铺公告失败"),
    ERROR_UPDATE_NOTICE("1073004","修改店铺公告失败"),
    ERROR_DELETE_NOTICE("1073005","删除店铺公告失败"),
    ERROR_OFFLINE_NOTICE("1073006","公告已强制下线"),

    ERROR_NO_RIGHTS("1073007", "亲,您无权限操作上级/下级发布的商品喔～"),
    
    //操作权限错误(BO：分公司、SM：店长、SG：导购)
    //分公司无权操作店长
    ERROR_AUTH_BO_TO_SM_UP("1101000","分公司无权上架店长商品"),
    ERROR_AUTH_BO_TO_SM_DOWN("1101001","分公司无权下架店长商品"),
    ERROR_AUTH_BO_TO_SM_EDIT("1101002","分公司无权修改店长商品"),
    //分公司无权操作导购
  	ERROR_AUTH_BO_TO_SG_UP("1101003","分公司无权上架导购商品"),
  	ERROR_AUTH_BO_TO_SG_DOWN("1101004","分公司无权下架导购商品"),
  	ERROR_AUTH_BO_TO_SG_EDIT("1101005","分公司无权修改导购商品"),
  	//分公司无权操作
  	ERROR_AUTH_BO_TO_OTHER_UP("1101006","分公司无权上架商品"),
  	ERROR_AUTH_BO_TO_OTHER_DOWN("1101007","分公司无权下架商品"),
  	ERROR_AUTH_BO_TO_OTHER_EDIT("1101008","分公司无权编辑商品"),
  	//店长无权操作分公司
  	ERROR_AUTH_SM_TO_BO_UP("1101009","店长无权上架分公司商品"),
	ERROR_AUTH_SM_TO_BO_DOWN("1101010","店长无权下架分公司商品"),
	ERROR_AUTH_SM_TO_BO_EDIT("1101011","店长无权修改分公司商品"),
	//店长无权操作导购
	ERROR_AUTH_SM_TO_SG_UP("1101012","店长无权上架导购商品"),
	ERROR_AUTH_SM_TO_SG_DOWN("1101013","店长无权下架导购商品"),
	ERROR_AUTH_SM_TO_SG_EDIT("1101014","店长无权修改导购商品"),
	//店长无权操作
  	ERROR_AUTH_SM_TO_OTHER_UP("1101015","店长无权上架商品"),
  	ERROR_AUTH_SM_TO_OTHER_DOWN("1101016","店长无权下架商品"),
  	ERROR_AUTH_SM_TO_OTHER_EDIT("1101017","店长无权编辑商品"),
    //导购无权操作分公司
    ERROR_AUTH_SG_TO_BO_UP("1101018","导购无权上架分公司商品"),
    ERROR_AUTH_SG_TO_BO_DOWN("1101019","导购无权下架分公司商品"),
	ERROR_AUTH_SG_TO_BO_EDIT("1101020","导购无权修改分公司商品"),
	//导购无权操作店长
	ERROR_AUTH_SG_TO_SM_UP("1101021","导购无权上架店长商品"),
	ERROR_AUTH_SG_TO_SM_DOWN("1101022","导购无权下架店长商品"),
	ERROR_AUTH_SG_TO_SM_EDIT("1101023","导购无权修改店长商品"),
	//导购无权操作
  	ERROR_AUTH_SG_TO_OTHER_UP("1101024","导购无权上架商品"),
  	ERROR_AUTH_SG_TO_OTHER_DOWN("1101025","导购无权下架商品"),
  	ERROR_AUTH_SG_TO_OTHER_EDIT("1101026","导购无权编辑商品"),
  	//其他操作分公司
  	ERROR_AUTH_OTHER_TO_BO_UP("1101027","无权上架分公司商品商品"),
  	ERROR_AUTH_OTHER_TO_BO_DOWN("1101028","无权下架分公司商品"),
  	ERROR_AUTH_OTHER_TO_BO_EDIT("1101029","无权编辑分公司商品"),
  	//其他操作店长
  	ERROR_AUTH_OTHER_TO_SM_UP("1101030","无权上架店长商品"),
  	ERROR_AUTH_OTHER_TO_SM_DOWN("1101031","无权下架店长商品"),
  	ERROR_AUTH_OTHER_TO_SM_EDIT("1101032","无权编辑店长商品"),
  	//其他操作导购
  	ERROR_AUTH_OTHER_TO_SG_UP("1101033","无权上架导购商品"),
  	ERROR_AUTH_OTHER_TO_SG_DOWN("1101034","无权下架导购商品"),
  	ERROR_AUTH_OTHER_TO_SG_EDIT("1101035","无权编辑导购商品"),
  	//其他操作分其他
  	ERROR_AUTH_OTHER_TO_OTHER_UP("1101036","无权上架商品"),
  	ERROR_AUTH_OTHER_TO_OTHER_DOWN("1101037","无权下架商品"),
  	ERROR_AUTH_OTHER_TO_OTHER_EDIT("1101038","无权编辑商品"),
	
	//返回前端的错误信息
	ERROR_ROLE_BO_EDIT("1201101", "你无权限操作，如需修改请联系分公司管理员"),
	ERROR_ROLE_SG_EDIT("1201102", "你无权限操作，如需修改请联系导购"),
	ERROR_ROLE_SM_EDIT("1201103", "你无权限操作，如需修改请联系店长"),
	
	ERROR_GUIDE_ISDISABLED("1062002","此店铺休息中，无法为您服务");

    private String code; 
    private String message; 
    private CodeEnum(String code, String message) {
        this.code = code; 
        this.message = message;
    }
    
    public String getActionCode(){
        return code; 
    }
    
    public String getMessage(){
        return message;
    }
    
    /**
     * Code枚举字典
     */
    private static HashMap<String, CodeEnum> codeEnumDic = new HashMap<String, CodeEnum>();
    static {
	    for (CodeEnum ce : CodeEnum.values()) {
	    	codeEnumDic.put(ce.getActionCode(), ce);
	    }
    }
    
    /**
     * 根据Code获取CodeEnum对象
     * @param codeCode
     * @return
     */
    public static CodeEnum getCodeEnum(String code) {
    	return codeEnumDic.get(code);
    }
}
