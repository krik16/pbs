package service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;

import base.BaseTest;

import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentItemEntity;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.service.AliPaymentService;
import com.rongyi.rpb.service.PCWebPageAlipayService;
import com.rongyi.rpb.service.PaymentItemService;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rpb.service.impl.RpbServiceImpl;

public class PaymentServiceTest extends BaseTest {

	@Autowired
	PaymentService paymentService;
	@Autowired
	PaymentItemService paymentItemService;

	@Autowired
	AliPaymentService aliPaymentService;

	@Autowired
	PCWebPageAlipayService pcWebPageAlipayService;

	@Autowired
	RpbServiceImpl rpbServiceImpl;

	//	 @Test
	public void testSelectPageListBySearch() {
		Map<String, Object> searchValueMap = null;
		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("currpage", 1);
		List<PaymentEntityVO> list = null;
		list = paymentService.selectPageListBySearch(searchValueMap);
		Assert.assertEquals(list.size(), 10);

		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("payNo", "1000002015101592");
		list = paymentService.selectPageListBySearch(searchValueMap);
		Assert.assertEquals(1, list.size());

		searchValueMap = new HashMap<String, Object>();
		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("orderNum", "1000001119347726");
		list = paymentService.selectPageListBySearch(searchValueMap);
		Assert.assertEquals(1, list.size());

		searchValueMap = new HashMap<String, Object>();
		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("status", "0");
		list = paymentService.selectPageListBySearch(searchValueMap);
		System.err.println("status is 0 count=" + list.size());

		searchValueMap = new HashMap<String, Object>();
		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("tradeType", "0");
		list = paymentService.selectPageListBySearch(searchValueMap);
		System.err.println("tradeType is 0 count=" + list.size());

	}

	// @Test
	public void testSelectPageListCountBySearch() {
		long count = 0;
		Map<String, Object> searchValueMap = null;
		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("payNo", "1000002015101592");
		count = paymentService.selectPageListCountBySearch(searchValueMap);
		Assert.assertEquals(1, count);

		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("orderNum", "1000001119347726");
		count = paymentService.selectPageListCountBySearch(searchValueMap);
		Assert.assertEquals(1, count);

		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("status", "0");
		count = paymentService.selectPageListCountBySearch(searchValueMap);
		System.err.println("status is 0 count=" + count);

		searchValueMap = new HashMap<String, Object>();
		searchValueMap.put("tradeType", "0");
		count = paymentService.selectPageListCountBySearch(searchValueMap);
		System.err.println("tradeType is 0 count=" + count);

	}

	// @Test
	public void testInsertByOrderDetailNum() {
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setPayNo("0001");
		paymentService.insertByOrderDetailNum(paymentEntity, "001,002");
	}

	//	 @Test
	public void testSelectByPrimaryKey() {
		PaymentEntity paymentEntity = paymentService.selectByPrimaryKey("80");
		System.err.println(paymentEntity.getPayNo());
	}

	// @Test
	public void testGetSendMessage() {

		System.err.println("支付宝网页支付");
		MessageEvent rpbEvent = getRpbEvent("rob", "1");
		paymentService.getSendMessage(rpbEvent);

		System.err.println("支付宝APP支付");
		MessageEvent rpbEvent1 = getRpbEvent("rob", "3");
		paymentService.getSendMessage(rpbEvent1);

		System.err.println("微信支付");
		MessageEvent rpbEvent2 = getRpbEvent("rob", "5");
		paymentService.getSendMessage(rpbEvent2);

		System.err.println("买家申请退款");
		MessageEvent rpbEvent3 = getRpbEvent("osm", "2");
		paymentService.getSendMessage(rpbEvent3);

		System.err.println("打款给卖家");
		MessageEvent rpbEvent4 = getRpbEvent("osm", "1");
		paymentService.getSendMessage(rpbEvent4);

		// System.err.println("支付时价格为0");
		// RpbEvent rpbEvent5 = getRpbEvent("rob", "1");
		// Map<String, Object> bodyMap = (Map<String,
		// Object>)rpbEvent5.getBody();
		// bodyMap.put("totalPrice","0");
		// paymentService.getSendMessage( rpbEvent5);

	}

	//	 @Test
	public void testValidateOrderNumExist() {
		PaymentEntity paymentEntity = paymentService.validateOrderNumExist("1000000126496953,1000000272983889", 1, 1);
		System.err.println("payNo=" + paymentEntity.getPayNo());
	}

	@SuppressWarnings("unchecked")
	 @Test
	public void testInsertOrderMessage() {
		PaymentEntityVO paymentEntityVO = null;
		MessageEvent rpbEvent = getRpbEvent("osm", "2");
		Map<String, Object> bodyMap = (Map<String, Object>) rpbEvent.getBody();
//		bodyMap.put("orderNum", "4011957155328124615");
//		bodyMap.put("totalPrice","0.01");
//		bodyMap.put("parmentId","0011924271872124947");
		rpbEvent.setBody(bodyMap);
		paymentEntityVO = paymentService.insertOrderMessage(rpbEvent);
//		Assert.assertEquals(paymentEntityVO.getPayNo(), "1000001352472469");
//		paymentEntityVO = paymentService.insertOrderMessage(rpbEvent);
//		Assert.assertEquals(paymentEntityVO.getOrderNum(), "000001");

	}

	// @Test
	public void testGetOrderNumStrsByPayNo() {
//		List<PaymentEntity> list = paymentService.getOrderNumStrsByPayNo("1000001597030813",Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);

	}

	//	@Test
	public void testInsert() {
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setOrderNum("11111");
		paymentEntity.setCreateTime(new Date());
		paymentService.insert(paymentEntity);
	}

	public void testSelectItemByPaymentId(){
		List<PaymentItemEntity> list = paymentItemService.selectByPaymentId(2171);
		System.err.println(list.get(0).getDetailNum());
	}

	//	@Test
	public void testQueryOrder(){
		System.err.println(aliPaymentService.queryOrder("1000001063768572", "2015080700001000480061032614").toString());
	}

	public MessageEvent getRpbEvent(String soruce, String type) {
		MessageEvent event = new MessageEvent();
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("orderNum", "4011957155328124615");
		bodyMap.put("totalPrice","0.01");
		bodyMap.put("paymentId","0011924271872124947");
		event.setBody(JSONObject.fromObject(bodyMap));
		event.setSource(soruce);
		event.setTarget("rpb");
		event.setTimestamp(Long.valueOf("1428565002336"));
		event.setType(type);
		return event;
	}

	//	@Test
	public void testGetBatchRefundBuyerMessage(){
		String[] idArray = new String[]{"4693"};
		//20150812192257707
		pcWebPageAlipayService.getBatchRefundBuyerMessage(idArray, "test");
	}

	//	@Test
	public void testPaysuccess(){
		rpbServiceImpl.paySuccessNotify("0818930009601625", 0.00);
	}

	@Test
	public void testSelectByBatchNoAndStatus(){
		System.err.println(paymentService.selectByBatchNoAndStatus("20150812192552139", 2).size());
	}

	//	@Test
	public void testSelectByTradeType(){
		PaymentEntity paymentEntity = paymentService.selectByOrderNumAndTradeType("0825255175681151", Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE5, Constants.PAYMENT_STATUS.STAUS2,0);
		System.err.println(paymentEntity.getId());
	}

	//	@Test
	public void testBodyToEntity(){
		String body = "{\"body\":{\"title\":\"c5555555\",\"orderDetailNum\":\"626676\",\"orderType\":\"1\",\"paymentId\":\"1000000459740290\","
				+ "\"orderNum\":\"0827630333441716\",\"totalPrice\":\"0.01\"},\"source\":\"coupon_order_queue\",\"target\":\"rpb\",\"timestamp\":1440667020557,\"type\":\"2\"}";
		paymentService.bodyToPaymentEntity(body, "2");
	}

	//	@Test
	@Rollback(false)
	public void testUpdateRefundRejected(){
		paymentService.updateRefundRejected(4690, 1);
	}

//	@Test
	public void testValiadteStatus(){
		String[] ids = new String[]{"1","2","3"};
		paymentService.valiadteStatus(ids, 2);
	}

//	@Test
	public void testselectByOrderNum(){
		List<PaymentEntity> list = paymentService.selectByOrderNum("120468903680153834", 0, 0);
		System.err.println("listSize=" + list.size());
	}

//	@Test
	public void testselectByPayNoAndPayChannelAndTradeType(){
		PaymentEntity paymentEntity = paymentService.selectByPayNoAndPayChannelAndTradeType("0120419209728164658", null, 0, 2);
		System.err.println("payNo={}"+paymentEntity.getPayNo());
	}

	@Test
	public void testGetWeixinSendMessage(){
		MessageEvent rpbEvent2 = getRpbEvent("rob", "5");
		paymentService.getSendMessage(rpbEvent2);
	}

	public static void main(String[] args){
		String id="1";
		String ids="1,2";
		String[] idArray = id.split(",");
		System.err.println("id="+idArray.toString());
		idArray = ids.split(",");
		System.err.println("ids="+idArray.toString());
	}
}
