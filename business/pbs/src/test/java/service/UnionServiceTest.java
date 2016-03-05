/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年7月3日上午10:46:29
 * @Description: TODO
 *
 **/

package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import base.BaseTest;

import com.rongyi.easy.rmmm.vo.OrderCouponInfo;
import com.rongyi.easy.rmmm.vo.OrderWithCouponInfoVO;
import com.rongyi.easy.rpb.entity.UnionCouponLogEntity;
import com.rongyi.rpb.common.pay.union.UnionUtil;
import com.rongyi.rpb.service.UnionCouponService;
import com.rongyi.rss.mallshop.order.ROAOrderFormServiceWithCouponInfo;
import com.unionpay.acp.sdk.HttpClient;

/**
 * @Author: 柯军
 * @Description: TODO
 * @datetime:2015年7月3日上午10:46:29
 * 
 **/
public class UnionServiceTest extends BaseTest{

	@Autowired
	UnionCouponService unionCouponService;
	
	@Autowired
	ROAOrderFormServiceWithCouponInfo rOAOrderFormServiceWithCouponInfoImpl;

//	@Test
	public void testKey() {
//		String paramData="msg_type=00&msg_txn_code=002002&msg_crrltn_id=20130921155443030785&msg_flg=0&msg_sender=1&msg_time";
//		String sign = Service.sign(paramData, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
//		System.err.println("sign=" + sign);
//		boolean result = Service.verify(paramData, sign,ConstantEnum.UNION_PUBLIC_KEY.getValueStr());
//		System.err.println("result="+result);
		String orderNo = "31731000001972699331";
		System.err.println(orderNo.substring(4));
		
		
//		Map<String,String[]> map = new HashMap<String,String[]>();
//		String[] s1 = new String[]{"00"};
//		String[] s2 = new String[]{"002002"};
//		String[] s3 = new String[]{"20130921155443030785"};
//		map.put("msg_type", s1);
//		map.put("msg_txn_code", s2);
//		map.put("msg_crrltn_id", s3);
//		String paramData = UnionUtil.getParamPlain2(map);
//		String sign = Service.sign(paramData, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
//		System.err.println("sign=" + sign);
//		boolean result = Service.verify(paramData, sign,ConstantEnum.UNION_PUBLIC_KEY.getValueStr());
//		System.err.println("result="+result);
		
	}

//	@Test
	public void testSign() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test1", "123");
		map.put("test2", "测试");
		map.put("test3", "test");
		System.err.println(UnionUtil.getParamPlain(map));
	}

//	@Test
	public void testSelectByMsgtxCodeAndTransNo(){
//		UnionCouponLogEntity unionCouponLogEntity = unionCouponService.selectByMsgtxCodeAndTransNo("002003", "1234567890");
//		System.err.println(unionCouponLogEntity.getId());
	}
//	@Test
	public void testHttpValidate() {
		Map<String, String> submitFromData = new HashMap<String, String>();
		submitFromData.put("msg_type", "00");
		submitFromData.put("msg_txn_code", "002002");
		submitFromData.put("msg_crrltn_id", "20130921155443030785");
		submitFromData.put("msg_flg", "0");
		submitFromData.put("msg_sender", "1");
		submitFromData.put("msg_time", "20130921155423");
		submitFromData.put("msg_sys_sn", "20130921155443030785");
		submitFromData.put("msg_ver", "0.1");
		submitFromData.put("req_serial_no", "20130921155443030785");
		submitFromData.put("shop_no", "086310100000000099");
		submitFromData.put("term_no", "12340004");
		submitFromData.put("coupon_no", "3173123400041");
		submitFromData.put("term_sp_chnl_no", "001");
		submitFromData.put("trans_crrltn_no", "1234567890");
		HttpClient hc = new HttpClient("http://localhost:8081/easy-rpb/v5/union/validateCoupon", 30000, 30000);
		try {
			int status = hc.send(submitFromData, "utf-8");
			System.err.println("status="+status);
			String response = hc.getResult();
			System.err.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testRaoGetOrderCouponInfo(){
		try {
			OrderWithCouponInfoVO orderWithCouponInfoVO=rOAOrderFormServiceWithCouponInfoImpl.getOrderCouponInfo("1000000859432068");
			System.err.println("orderNo="+orderWithCouponInfoVO.getOrderNo());
			System.err.println("totalAmount="+orderWithCouponInfoVO.getTotalAmount());
			 List<OrderCouponInfo> list = orderWithCouponInfoVO.getCouponInfoList();
			 System.err.println(list.get(0).getCouponCode());
			 System.err.println(list.get(0).getCouponId());
			 System.err.println(list.get(0).getCouponDiscount());
			 System.err.println(list.get(0).getRecommend());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}
