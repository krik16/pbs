/**
 * @Copyright (C),上海容易网电子商务有限公司
 * @Author: 柯军
 * @datetime:2015年5月14日下午5:42:45
 * @Description: TODO
 *
 **/

package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import base.BaseTest;

import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.rpb.service.impl.PCWebPageAlipayServiceImpl;
import com.rongyi.rpb.service.impl.PaymentServiceImpl;
import com.rongyi.rpb.web.controller.v5.PCWebPageAlipayController;

/**
 * @Author:  柯军
 * @Description: 交易
 * @datetime:2015年5月14日下午5:42:45
 *
 **/
public class TradeServiceTest extends BaseTest{

//    @Autowired
//    RpbTradeService tradeService;
//    
//    @Autowired
//    ROAMallLifeUserService roaMallLifeUserService;


    @Autowired
    PCWebPageAlipayServiceImpl pcWebPageAlipayService;

    @Autowired
    PCWebPageAlipayController pCWebPageAlipayController;

    @Autowired
    PaymentServiceImpl paymentService;


    //    @Test
//    public void selectTradePageListTest(){
//        Map<String,Object> map = getParamMap();
//        List<TradeVO> list = tradeService.selectTradePageList(map,1,10);
//        for (TradeVO tradeVO : list) {
//            System.err.println("tradeNo="+tradeVO.getTradeNo());
//        }
//    }
////    @Test
//    public void selectTradePageListCountTest(){
//        Map<String,Object> map = getParamMap();
//        Integer count = tradeService.selectTradePageListCount(map);
//        System.err.println("list size："+count);
//    }
//    
////    @Test
//    public void roaGetUserDetailByNameTest(){
////      roa暂未部署
//        try {
//            List<UserVO> list =  roaMallLifeUserService.getUserDetailByName("1");
//            System.err.println(list.size());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//    @Test
    public void sendMessageTest(){
//        messageSender.sendEvent(new RpbEvent());
    }

    //    @Test
    public void pcWebPageAlipayServiceTest(){
        MessageEvent rpbEvent = new MessageEvent();
        rpbEvent.setBody("{\"paymentId\":\"655,656\",\"type\":\"2\"}");
        System.err.println(pcWebPageAlipayService.getHtmlMap(rpbEvent));

    }

//    @Test
//    public void paySuccessToMessageTest(){
//        List<PaymentEntity> list = new ArrayList<PaymentEntity>();
//        list.add(paymentService.selectByPrimaryKey("4747"));
//        list.add(paymentService.selectByPrimaryKey("4746"));
//        list.add(paymentService.selectByPrimaryKey("4745"));
//        list.add(paymentService.selectByPrimaryKey("4744"));
//        pCWebPageAlipayController.paySuccessToMessage(list);
//
//    }

    private Map<String,Object> getParamMap(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("tradeNo", "2015050400001000240050459338");
        map.put("orderNo", "0");
        map.put("mallName", "0");
        map.put("shopName", "0");
        map.put("sellerName", "0");
        map.put("status", "0");
        map.put("tradeStartTime",new Date());
        map.put("tradeEndTime",new Date());
        map.put("minTotalPrice",10);
        map.put("maxTotalPrice",100);
        return map;
    }
}
