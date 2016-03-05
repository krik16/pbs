package com.rongyi.easy.rpb.entity;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.sound.midi.Synthesizer;
import org.springframework.beans.BeanUtils;
import com.rongyi.core.common.util.DateUtil;

public class UnionActivityRegister extends UnionBaseEntity implements Serializable{
    private Integer id;
   // private String msgCrrltnId;
   // private String msgTxnCode;
   // private String msgType;
   // private String msgSender;
   // private Date msgTime;
   // private String msgSysSn;
   // private String msgVer;
    private String msgFlg;
    private String spChnlNo;

    private String eventNo;

    private String shopNo;

    private String eventTitle;

    private String eventDesc;

    private Date createAt;

    private Date endAt;

    private String eventLink;

    private String eventRule;

    private String ruleDesc;

    private String specBankFlag;

    private String eventStatus;

	private String sign;
	
	public static UnionActivityRegister deleteActivity(Map<String, String[]> paramMap) throws ParseException {
		UnionActivityRegister unionCouponLogEntity = new UnionActivityRegister();
		UnionBaseEntity unionBaseEntity = UnionBaseEntity.mapToBaseEntity(paramMap);
		BeanUtils.copyProperties(unionBaseEntity, unionCouponLogEntity);
		if (paramMap.get("sp_chnl_no") != null && paramMap.get("sp_chnl_no").length > 0)
			unionCouponLogEntity.setSpChnlNo(paramMap.get("sp_chnl_no")[0].toString());
		if (paramMap.get("msg_flg") != null && paramMap.get("msg_flg").length > 0)
			unionCouponLogEntity.setMsgFlg(paramMap.get("msg_flg")[0].toString());
		if (paramMap.get("event_no") != null && paramMap.get("event_no").length > 0)
			unionCouponLogEntity.setEventNo(paramMap.get("event_no")[0].toString());
		if (paramMap.get("shop_no") != null && paramMap.get("shop_no").length > 0)
			unionCouponLogEntity.setShopNo(paramMap.get("shop_no")[0].toString());
		if (paramMap.get("event_title") != null && paramMap.get("event_title").length > 0)
			unionCouponLogEntity.setEventTitle(paramMap.get("event_title")[0].toString());
		if (paramMap.get("event_rule") != null && paramMap.get("event_rule").length > 0)
			unionCouponLogEntity.setEventRule(paramMap.get("event_rule")[0].toString());
		if (paramMap.get("begin_date") != null && paramMap.get("begin_date").length > 0)
			unionCouponLogEntity.setCreateAt(DateUtil.getCurrDateTime());
		if (paramMap.get("end_date") != null && paramMap.get("end_date").length > 0)
			unionCouponLogEntity.setEndAt(DateUtil.getCurrDateTime());	
		if (paramMap.get("event_status") != null && paramMap.get("event_status").length > 0)
			unionCouponLogEntity.setEventStatus(paramMap.get("event_status")[0].toString());
		if (paramMap.get("sign") != null && paramMap.get("sign").length > 0)
			unionCouponLogEntity.setSign(paramMap.get("sign")[0].toString());
		return unionCouponLogEntity;
	}  
public static UnionActivityRegister mapToEntity(Map<String, String[]> paramMap) throws ParseException {
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
		//String str="2011-5-31 14:40:50";	
		//Date d=(Date) sim.parse(str);
		UnionActivityRegister unionCouponLogEntity = new UnionActivityRegister();
		UnionBaseEntity unionBaseEntity = UnionBaseEntity.mapToBaseEntity(paramMap);
		BeanUtils.copyProperties(unionBaseEntity, unionCouponLogEntity);
		if (paramMap.get("sp_chnl_no") != null && paramMap.get("sp_chnl_no").length > 0)
			unionCouponLogEntity.setSpChnlNo(paramMap.get("sp_chnl_no")[0].toString());
		if (paramMap.get("event_no") != null && paramMap.get("event_no").length > 0)
			unionCouponLogEntity.setEventNo(paramMap.get("event_no")[0].toString());
		if (paramMap.get("msg_flg") != null && paramMap.get("msg_flg").length > 0)
			unionCouponLogEntity.setMsgFlg(paramMap.get("msg_flg")[0].toString());
		if (paramMap.get("shop_no") != null && paramMap.get("shop_no").length > 0)
			unionCouponLogEntity.setShopNo(paramMap.get("shop_no")[0].toString());
		if (paramMap.get("end_date") != null && paramMap.get("end_date").length > 0)
			unionCouponLogEntity.setEndAt(DateUtil.getCurrDateTime());	
		if (paramMap.get("begin_date") != null && paramMap.get("begin_date").length > 0)
			unionCouponLogEntity.setCreateAt(DateUtil.getCurrDateTime());
		if (paramMap.get("event_desc") != null && paramMap.get("event_desc").length > 0)
			unionCouponLogEntity.setEventDesc(paramMap.get("event_desc")[0].toString());
		if (paramMap.get("event_link") != null && paramMap.get("event_link").length > 0)
			unionCouponLogEntity.setEventLink(paramMap.get("event_link")[0].toString());
		if (paramMap.get("event_title") != null && paramMap.get("event_title").length > 0)
			unionCouponLogEntity.setEventTitle(paramMap.get("event_title")[0].toString());
		if (paramMap.get("event_rule") != null && paramMap.get("event_rule").length > 0)
			unionCouponLogEntity.setEventRule(paramMap.get("event_rule")[0].toString());
		if (paramMap.get("rule_desc") != null && paramMap.get("rule_desc").length > 0)
			unionCouponLogEntity.setRuleDesc(paramMap.get("rule_desc")[0].toString());
		if (paramMap.get("spec_bank_flag") != null && paramMap.get("spec_bank_flag").length > 0)
			unionCouponLogEntity.setSpecBankFlag(paramMap.get("spec_bank_flag")[0].toString());
		if (paramMap.get("event_status") != null && paramMap.get("event_status").length > 0)
			unionCouponLogEntity.setEventStatus(paramMap.get("event_status")[0].toString());
		if (paramMap.get("sign") != null && paramMap.get("sign").length > 0)
			unionCouponLogEntity.setSign(paramMap.get("sign")[0].toString());
		return unionCouponLogEntity;
	}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getMsgFlg() {
        return msgFlg;
    }

    public void setMsgFlg(String msgFlg) {
        this.msgFlg = msgFlg;
    }


    public String getSpChnlNo() {
        return spChnlNo;
    }

    public void setSpChnlNo(String spChnlNo) {
        this.spChnlNo = spChnlNo;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public String getEventRule() {
        return eventRule;
    }

    public void setEventRule(String eventRule) {
        this.eventRule = eventRule;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getSpecBankFlag() {
        return specBankFlag;
    }

    public void setSpecBankFlag(String specBankFlag) {
        this.specBankFlag = specBankFlag;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}