package com.rongyi.core.constant;

import com.rongyi.core.common.PropertyConfigurer;
import com.rongyi.core.framework.spring.context.utils.SpringContextUtil;

public interface Constants {
    String COOKIE_DOMAIN_RONGYI = ".rongyi.com";
    String COOKIE_DOMAIN_RONGYIGUANG = ".rongyiguang.com";

    interface FilterRelation {
        String AND = "AND";
        String OR = "OR";
    }


    interface OrderPaytime {
        Integer PAY_SECOND = 900; //订单支付最大时间（秒数）
    }
    String BS_COOKIE_NAME = "BSST";
    String RY_COOKIE_NAME = "RYST";

    /**
     * 登录用户的session key
     */
    String USER_SESSION_KEY = "user_session_key"; //
    
    //商家账号初始密码
  	String USER_INIT_PWD = "RYxyz123";


    String RESULT_SUCCES = "success"; //成功标志位
    String RESULT_FAIL = "fail"; //失败标志位

    interface Version {
        String VERSION1 = "0.1.0";
    }

    interface FlopgoMessage {
        String API_NULL_FLOPUSER = "用户名参数没有";
        String API_NULL_FLOPCHANNEL = "用户渠道没有";
        String API_NULL_OBJECTFLOPGOID = "翻牌活动id没有";
        String API_NULL_ACTIVITYID = "最近没有活动";
        String API_NULL_LISTTYPE = "列表类型没有";
        String API_NULL_PAGESize = "没有分页";
        String API_NULL_USERPHONE="用户手机号为空";
        String API_NULL_COUPONID = "没有卷码";
        String API_NULL_ID = "没有奖品id";
        String API_USER_NO = "用户没有登录";
        String API_USER_CHANNEL_ERROR = "用户渠道错误 ";
    }

    interface SOME_TIME {



        int SESSION_REGIST_TIME = 30 * 60;//注册时的验证码有效期为30分钟
        int SESSION_LEFT_TIME = 60 * 60 * 24 * 30;//一个月的时间吧
    }



    /**
     * API 信息常量
     */
    interface Message {
        // API请求用户异常信息
        String API_REQUEST_SUCCESS = "请求成功";
        String REQUEST_ORDER_FALSE = "未找到相关订单";
        String API_USER_UBNORMAL = "用户不正确";
        String API_VERSION_UBNORMAL = "版本号不正确";
        String API_NULL_OPTENID = "微网站下单，openId 为空";
        String API_NULL_WECHAT_PHONE = "微网站下单，缺少必要电话参数";
        String API_NULL_TER_PHONE = "终端机渠道手机用户下单，缺少必要电话参数";
        String API_NULL_PHONE = "app渠道下单，缺少必要电话参数";
        String API_NULL_USER = "未找到相关用户信息";
        String API_NULL_PARAM = "缺少必要参数";

        // Order 下单信息
        String ORDER_PARAM_UBNORMAL = "下订单参数异常";

        String GROUPON_INEXISTENCE = "下单优惠券不存在";
        String GROUPON_SOLD_OUT = "此优惠券已下线或已过期";
        String GROUPON_OUT_DATE = "下单日期不在销售时间之内";
        String ORDER_PAY_SUCCESS = "下单并生成团购券成功";
        String ORDER_SUCCESS = "下单成功";
        String GROUPON_REST_LACK = "优惠券剩余数量不足";
        String GROUPON_OUT_QUOTA = "购买数量已经超出每个用户购买限制";
        String GROUPON_OUT_QUOTA_TODAY = "亲，优惠券已被抢购一空，明天再来吧";
        // 接口使用信息
        String NO_RELATIVE_GROUPON = "未找到相关优惠券";
        String ABNORMAL_USE_TIME = "优惠券使用时间异常";
        String ABNORMAL_USE_STATUS = "优惠券使用状态异常";

        // 订单详情中默认字段
        String UPDATE_BY = "用户";
    }

    interface Common {

        int YES = 1;
        int NO = 0;

        /**
         * 返回验证码的描述短信
         */
        String SEND_PHONE_VERIFY_CODE_MSG = "send.phone.verify.code.msg";
        /**
         * 使用哪个短信通道,1:云短信通道 ，2：百悟短信通道,3:建周
         */
        String MESSAGE_CHANNEL = "message_channel";
        /**
         * 存session的
         */
        String GET_CODE_4_VERIFY_PHONE = "getCode4VerifyPhone_";
    }

    interface JSON_RESULT {
        /**
         * 正常返回状态
         */
        short STATUS_NORMAL = 0;
        /**
         * 异常返回状态
         */
        short STATUS_ABNORMAL = 1;
        /**
         * 没有权限访问
         */
        short STATUS_DENIED = 11;
        /**
         * 未登录不可访问
         */
        short STATUS_UN_LOGIN = 21;
    }

    interface PageSize {
        /**
         * 已经报名的小伙伴一页数量
         */
        int ALREADY_APPLY_USERS = 100;//
        /**
         * 我的报名一页的数量
         */
        int MINE_APPLYS = 100;
        /**
         * 首页一页的数量
         */
        int HOME_INDEX = 100;
        /**
         * 我的订单每页数量
         */
        int MY_ORDER = 100;

    }

    interface ZonesType {
        String TYPE_PROVINCE = "Province";
        String TYPE_CITY = "City";
        String TYPE_DISTRICT = "District";
        String TYPE_BUSINESSDISTRICT = "BusinessDistrict";
        String TYPE_MALL = "Mall";
        String TYPE_MALL_AREA = "MallArea";

    }

    interface Valid {
        int OPEN = 0;
        int CLOSE = 1;
        int DELETE = 2;
    }

    interface Categories {
        String FIRST_PARENT_IDS = "51f9da1731d65584ab001f0f"; // 最上面一级的分类ID
    }

    interface MallActivity {
        PropertyConfigurer propertyConfigurer = (PropertyConfigurer) SpringContextUtil.getBean("propertyConfigurer");
        String CRM_HOST_URL_Path = propertyConfigurer.getProperty("crm.rongyi.com").toString();
        String CRM_MALL_ACTIVITYS_IMAGE_PATH = propertyConfigurer.getProperty("mallActivity.image.path").toString();
    }

    interface SolrURL {
        PropertyConfigurer propertyConfigurer = (PropertyConfigurer) SpringContextUtil.getBean("propertyConfigurer");
        String SOLR_TEST_BASE = propertyConfigurer.getProperty("solr.base.url").toString();
        // String SOLR_TEST_BASE = "http://192.168.1.202:8080/solr/";
        // String SOLR_TEST_BASE = "http://10.4.13.17/solr";
        String SOLR_BRAND = SOLR_TEST_BASE + "brand/";
        String SOLR_MALL = SOLR_TEST_BASE + "mall/";
        String SOLR_SHOP = SOLR_TEST_BASE + "shop/";
        String SOLR_MEMBER = SOLR_TEST_BASE + "member/";
        String SOLR_COMMODITY = SOLR_TEST_BASE + "commodity/";
        String SOLR_RMMMSHOP = SOLR_TEST_BASE + "poi/";
    }

    interface ActivityType {
        String SHOP = "2";
        String BRAND = "0";
        String MALL = "1";
        String BAILIAN = "4";
    }

    interface CouponOrderBy {
        String PRICEASC = "priceAsc";
        String PRICEDESC = "priceDesc";
        String PUBLICASC = "publicAsc";
        String PUBLICDESC = "publicDesc";
    }

    /**
     * 优惠券展示渠道
     */
    interface ShowChannel {
        /**
         * 互动屏、APP
         */
        String TERMINAL_APP = "1";

        /**
         * 微网站、APP
         */
        String WECHAT_APP = "2";

        /**
         * 互动屏、微网站、APP
         */
        String TERMINAL_WECHAT_APP = "3";

        /**
         * APP
         */
        String APP = "4";

        /**
         * 互动屏
         */
        String TERMINAL = "5";//

        /**
         * 微网站
         */
        String WECHAT = "6";//

        /**
         * 互动屏、微网站
         */
        String TERMINAL_WECHAT = "7";
    }

    interface CheckCodeError {
        String CODEERROR = "抱歉，您输入的券码错误，不能打印，如有疑问请咨询下方的客服电话";
        String NOPRINT = "抱歉，目前不提供打印功能，请咨询下方的客服电话";
    }

    interface CheckType {
        String COMMODITY = "01";
        String COUPON = "02";
    }

    /**
     * 集团id
     */
    interface GROUPID {

        String lUDI = "100001"; // 绿地
        String JIDI = "100002"; // 金地
        String BAILIAN = "100003"; // 百联
        String RONGYI = "100000"; // 容易
    }

    /**
     * owner_id 软件提供商id
     */
    interface OWNERID {
        /**
         * 绿地
         */
        String lUDI = "000001"; // 绿地
        /**
         * 金地
         */
        String JIDI = "000002"; // 金地
        /**
         * 百联
         */
        String BAILIAN = "000003"; // 百联
        /**
         * 容易
         */
        String RONGYI = "000000"; // 容易
    }

    /**
     * 会员类型 1:实体会员， 2:电子会员
     */
    interface MEMBER_NATURE {
        /**
         * 会员类型 1:实体会员
         */
        int ENTITY_MEMBER = 1;
        /**
         * 会员类型 2:电子会员
         */
        int ELECTRONIC_MEMBER = 2;
    }

    interface ImagesCloud {
        String UPAIYUN_Url = "http://rongyi.b0.upaiyun.com/"; // 云盘路径
        String UPAIYUN_URL_BENEFITS = "system/benefits/"; // 云盘路径-会员权益
        String UPAIYUN_URL_INTEGRALRULES = "system/integralRules/"; // 云盘路径-积分规则
        String UPAIYUN_URL_INTEGRALCOMM = "system/mall/commodity/intrgral/"; // 云盘路径-积分商品
        String UPAIYUN_URL_SHOPS = "system/photo/file/"; // 云盘路径-会员权益
    }
    
    /**
     * 是否绑定的会员
     */
    interface MemberBind {
        /**
         * 绑定
         */
        int BINDED = 0;
        /**
         * 非绑定
         */
        int UNBIND = 1;
    }

    /**
     * 会员性别
     */
    interface MemberSex {
        /**
         * 性别: M=Male男性, F=Female女性
         */
        String MALE = "M";
        /**
         * 性别: M=Male男性, F=Female女性
         */
        String FEMALE = "F";
    }

    interface RoaCommon {
        String PHONE_VERIFYCODE_OK = "true";
        String DELAYTIME = "30";

    }

    /**
     * 来源：0000（终端机），0001（app），0002（微信），0003为 移动微商城卖家版，
     * 0004为移动微商城买家版，0005（网页），0006-9999（其他，包括发码，活动等）
     *
     * @author user
     */
    interface RequestSource {
        String TERMINAL = "0000";
        String MALL_LIFE = "0001";
        String WECHAT = "0002";
        String SELLER_MOBILE_MALL = "0003";
        String BUYER_MOBILE_MALL = "0004";
        String WEB_PAGE = "0005";
    }

    interface RSA {
        String PUBLIC_KEY = "publicKey";
        String PRIVATE_KEY = "privateKey";

        String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCe1N+tR4udbureHSxDVlRCv7SLw7LLFixyLoXEjVFNNkxpnluammzeRlZ23PU16DLPIpCI9BtYHMh+n6Al4plc4nKEfJ0OgoLr4M/VzlCReAWl7+DFFxKZV7MA7yr7gsX/eLQyWAachc17gMmLMFfXBRD/8PZB2if2XnNfcsHL5wIDAQAB";
        String PRIVATE_KEY_STR = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ7U361Hi51u6t4dLENWVEK/tIvDsssWLHIuhcSNUU02TGmeW5qabN5GVnbc9TXoMs8ikIj0G1gcyH6foCXimVzicoR8nQ6Cguvgz9XOUJF4BaXv4MUXEplXswDvKvuCxf94tDJYBpyFzXuAyYswV9cFEP/w9kHaJ/Zec19ywcvnAgMBAAECgYBh/lBqZkPbQ/2gQDt46CBgdkp8GKeM6QdPAykA1XedvaxzVYKK5k/Y62iZOUYIllQSu+6UsJbWTBvfE7kI5r9bOH08nnReZN8HXEIPQGX5OYUzm/7kHhZYSXdw+rCNmbTBACEFskJ0qHGMKddxqGjuHRriI2zmVem2yHJ3uIx7EQJBAMvmqQYetN1Tfoud2ilVVMuPoFoetZdz1oHodyDFRgB82Lkpjv6e8JRg0Kuu+CXMK/0E788P1j+N16BJnqB9e4sCQQDHai4tT2ESAuqiQ3kgxqbNXlKq2c1eQFIqFn+crJesltt53ymAYShCRiGS18UFfU4tIS0nNji7qe5om0j9FyyVAkEAxQ81h66hic2p3XE/aPcJLnqutKo9QhQ32fE7QLangs0l/z3E7xvY0u4GfgZp2M3MLYpZ4boii+Wf4U+FOQJNJQJBAKSl39CbbNtxOweUzEsqmgiQ65KTWISMeQq1HtJekrD2seozPtH35kSqIQhcTRlWN5j9fomUtE0GZolKRX5qhu0CQE9D3ZasigRGCBRb9QBsSp+/5YZ1B8UPN7wUBmBKqhLVzZbTyEFVC95AofejU0jjcJ/HwPLaJmAs07oDzpJzNxU=";
    }

    /**
     * 退款状态, 未处理:0,已通过:1,已驳回:2
     *
     * @author LiuChao 2014/10/24
     */
    interface RefundStatus {
        int NOT_REFUND = 0;
        int HAS_REFUND = 1;
        int REJECT = 2;
    }

    interface MQRequestParam {
        String REQUEST_QUEUENAME_OSM = "osm";
        String REQUEST_QUEUENAME_RPB = "rpb";
        String REQUEST_QUEUENAME_VA = "va";
        String REQUEST_QUEUENAME_TMS = "tms";
        String EXCHANGE = "core_exchange";
    }

    interface Comment {
        String DEFAULT_PRAISE_WORDS = "东西不错，好评~";
    }

    interface forumCode {

        /**
         * 清仓商品板块代码
         */
        String CLEARANCE_COMMODITY = "A1003";

    }

    /*
     * 系统来源
     */
    interface sourceSystem {
        String SOTRE_MMS = "SOTRE_MMS";// 会员系统
        String STORE_CMS = "SOTRE_CMS";// 百货cms
        String STORE_COUPON = "STORE_COUPON";// 百货优惠券
        String MALLSHOP_COUPON = "MALLSHOP_COUPON";// 摩店优惠券
    }

    interface UdField {
        String UCONPONSIDS = "uConponsIds";// 优惠券增加和修改
        String DCONPONSIDS = "dConponsIds";// 优惠券删除
        String MEMBERBENEFITS = "memberBenefits";// 会员权益
        String INTEGRALRULES = "integralRules";// 积分规则
        String SIGNIN = "signIn";
        String UINTEGRALCOMMODITYIDS = "uIntegralCommodityIds";// 积分商品增加和修改
        String DINTEGRALCOMMODITYIDS = "dIntegralCommodityIds";// 积分商品删除
    }

    /**
     * 发布
     */
    interface isUdPublished {
        /**
         * 未发布
         */
        byte published_0 = 0;
        /**
         * 发布成功
         */
        byte published_1 = 1;
    }

    /**
     * 用户状态
     *
     * @author user
     */
    interface STATUS {
        /**
         * 用户状态：删除
         */
        Integer DELETE = 3;

        /**
         * 用户状态：审核中
         */
        Integer CHECKING = 2;

        /**
         * 用户状态：通过
         */
        Integer ACCESS = 1;
    }

    interface AccountType {
        /**
         * 银行卡类型
         */
        Integer BANK = 2;
        /**
         * 支付宝类型
         */
        Integer PAY = 1;
    }


    interface Permission {
        /**
         * 账户管理
         */
        Integer ACCOUNT_MANAGEMENT = 1;
        /**
         * 商品管理
         */
        Integer COMMODITY_MANAGEMENT = 2;
        /**
         * 排班管理
         */
        Integer WORKFORCE_MANAGEMENT = 3;
        /**
         * 订单管理
         */
        Integer ORDER_MANAGEMENT = 4;
        /**
         * 店员认证
         */
        Integer ASSISTANT_AUTHENTICATION = 5;
        /**
         * 客服
         */
        Integer CUSTOMER_SERVICE = 6;
        /**
         * 评价
         */
        Integer EVALUATE = 7;
        /**
         * 积分规则管理
         */
        Integer INTEGRAL_ROLE_MANAGEMENT = 8;
        /**
         * 后台商品管理
         */
        Integer BACKGROUND_COMMODITY_MANAGEMENT = 9;
        /**
         * 商品交易
         */
        Integer COMMODITY_DEAL = 10;

    }

    /**
     * SQL return Exception
     *
     * @author Lc
     */
    interface SQLException {
        String INCORRECT_VALUE = "Incorrect string value";
    }

    /**
     * 魔生活魔店版本号
     *
     */
    interface MallLifeVersion{
    	Integer VERSION=1;
    }

    interface TMSTimeRangeType {
    	int ALL = 0;
        int DAY = 1;
        int WEEK = 2;
        int MONTH = 3;
    }

    /**
    * @ClassName: DrawApplyStatus
    * @Description: TODO(提现记录状态)
    * @author lqy
    * @date 2015年5月28日 下午4:09:48
    *
    */
    interface DrawApplyStatus{
    	int ALL = 0;//所有
    	int SEND = 1;//已发放
    	int PROCESSING = 2;//处理中
    	int FAIL = 3;//失败
    	String BONUS_DEFAULT_MSG = "优秀成交额外奖励";
    }
    interface VderStatus {
        int NOT_PAY = 0;//待付款
        int HAD_PAY = 1;//已付款
        int HAD_CANCEL = 2;//已取消
        int HAD_DELETE = 3;//已删除
        int REFUND = 5; //退款状态,包括退款中和已退款
        PropertyDate[] vderStatusProperty = {new PropertyDate("0", "待付款"), new PropertyDate("1", "已付款"),
                new PropertyDate("2", "已取消"), new PropertyDate("3", "已删除")};
    }

    interface MyOrder {
        String ORDER_SPLIT = ",";
        int NOT_OUT_DATE = 0;
        int OUT_DATE = 1;
    }

    /**
     * 优惠券使用状态. 未使用:0,已使用:1,退款中:2,已退款:3,已过期:4
     *
     * @author LiuChao 2014/10/22
     */
    interface ActivityStatus {
        int NOT_USE = 0;
        int HAS_USED = 1;
        int REFUNDING = 2;
        int REFUNDED = 3;
        int EXPIRED = 4;
    }

    /**
     * 只用于优惠券列表展示, 待付款、已付款、已取消、已删除值与VderStatus同，不再重复赋值。 新增 4退款中, 5未消费, 6已退款,
     * 7已过期, 8已使用
     *
     * @author Lc
     */
    interface ActivityDisplayStatus {
        int REFUNDING = 4;
        int NOT_USE = 5;
        int REFUNDED = 6;
        int EXPIRED = 7;
        int HAS_USED = 8;
        int OUT_CODE = 9;
    }

    /**
     * 配置的类型
     */
    interface ConfigType {
        String CONFIGURATIONS = "com.rongyi.easy.gcc.Configurations";
        String TRANS_CONFIGURATIONS = "com.rongyi.easy.gcc.TransConfigurations";
        /** 买手交易配置*/
        String BUYER_TRANS_CONFIGURATIONS = "com.rongyi.easy.gcc.BuyerTransConfigurations";
        /** 微商城买手参数配置 */
        String BUYER_CONFIGURATIONS = "com.rongyi.easy.gcc.BuyerConfigurations";
    }
    interface AppType{
    	/**
    	 * 容易逛
    	 */
    	Integer MALLLIFE = 1;
    	/**
    	 * 摩店
    	 */
    	Integer MALLSHOP = 2;
    }
    interface ValidCodeSuggest{
    	/**
    	 * 扫码验券建议
    	 */
    	String VALID_CODE_SUGGEST = "小提醒：如果遇到用户选择数量和本页面不一致的情况，建议采用单张验码的方式试试噢。问题反馈：400-040-8989";
    }
    interface ValidCodeNum{
    	/**
    	 * 验券的数量（默认值）
    	 */
    	Integer DEFAULT_VALID_CODE_NUM = 1;
    }
    /**
     * 优惠券验证的异常状态描述
     *
     * @author Ventures
     */
    interface AbnormalMassage {

    	String BEYONDLIMITS = "对不起，您今天已超出此类券的使用次数";
        String CODEERROR = "对不起，您输入的券码有误。";
        String REFUND = "对不起，您输入的券码状态异常";
        String BEUSED = "对不起，您输入的券码已被使用。";
        String OUTDATE = "对不起，您输入的券码已失效。";
        String UNDUEDATE = "对不起，您输入券码的活动暂未开始。";
        String NOPRINT = "抱歉，验证系统异常。";
        String NOUSEMODE = "对不起，你输入的券码，不支持此方式验证。";
        String TITLERROR = "对不起，您此次选择的优惠券名称有误，请重新选择。";
        String NOHEAR = "抱歉，本店暂未发行该类优惠券";
        String REPEAT = "您已经成功验证";
        String NOTENOUGHNUM = "没有足够的可用券码";
    }
    
    /**
     * App标识  来自于app store
     */
    interface AppId{
    	String MDC2C_IOS_APPId = "999209638";
    	String RYG_IOS_APPId = "684748850";
    }
    
    /**
     * App广告推广通道
     */
    interface AppAdvertChannel{
    	String ZHIMENG_CHANNEL = "zhimeng";//指盟
    	String WAPS_CHANNEL = "waps";//万普
    	
    }
    interface Errno{
    	int success=0;
    }

    interface NoticeStatus {
        /**
         * 未通过
         */
        Integer NOPASSED = 0;
        /**
         * 审核中
         */
        Integer REVIEWING = 1;
        /**
         * 发布中
         */
        Integer RELEASEING = 2;
        /**
         * 已过期
         */
        Integer OVERDUE = 3;
    }

    /**
     *卖家类型
     */
    interface GuideType {
        /**
         * 导购
         */
        Integer GUIDE = 1;
        /**
         * 买手
         */
        Integer BULL = 2;
    }
    interface CouponPage{
        Integer CURRENT_PAGE=1;
        Integer PAGE_SIZE=10;
    }
    
    interface UserOperate{
    	Integer HONGBAO = 1;//红包
    	Integer COUPON = 2;//代金券
    	Integer REBATE = 3;//抵扣券
    }

    interface RedenvelopStatus{
        int UNUSED=0;
        int USED=1;
        int OVERDUE=2;
    }
    //容易逛[0] 互动屏[1] 微商[2]
    interface CouponChannel{
        int RYG=0;//容易逛
        int HDP=1;//互动屏
        int WX=2;//微信
    }
    interface ExportSize{
        int EXCEL_EXPORT_MAX_SIZE=50000;
    }


    /**
     * 对账类型
     */
    interface IS_PAY_VA{
        byte IS_PAY_VA_0 = 0;//初始值，未结算
        byte IS_PAY_VA_1 = 1;//对私(打款到导购虚拟账号
        byte IS_PAY_VA_2 = 2;//对公(通过对账单结算)'
    }


    /**
     * 百度推送定义的消息类型
     */
    interface BD_PUSH_MSG_TYPE{
        int TC_MSG=0;//透传消息
        int TZ_MSG=1;//通知消息
    }


    /**
     * 百度推送设备类型
     */
    interface PUSH_DEVICE{
        int ADDROID=3;
        int IOS=4;
    }
    /**
     * 百度推送到达APP
     */
    interface PUSH_APP{
        int RYG=0;//容易逛
        int MD=1;//摩店
    }


}
