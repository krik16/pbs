package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.PermissionException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.entity.Authority;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.AuthorityService;
import com.shouyingbao.pbs.service.MchShopService;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.vo.MchShopVO;
import com.shouyingbao.pbs.vo.PaymentBillVO;
import com.shouyingbao.pbs.vo.TradeTotal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/8 17:33
 **/
@Controller
@RequestMapping("/mchBill")
public class MchPaymentbIllController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(MchPaymentbIllController.class);
    @Autowired
    PaymentBillService paymentBillService;
    @Autowired
    AuthorityService authorityService;

    @Autowired
    MchShopService mchShopService;

    @RequestMapping(value = "/search")
    public String search(ModelMap model) {
        Map<String,Object> map = new HashMap<>();
        getDataPermission(map,getUser(),getAuthority());
        List<MchShopVO> mchShopVOList = mchShopService.selectListByPage(map,null,null);
        model.addAttribute("shopList",mchShopVOList);
        return "/mchBill/bill";
    }

    @RequestMapping("/list")
    public String list(ModelMap model, @RequestBody Map<String, Object> map) {
        LOGGER.info("list:map={}", map);
        try {
            getDataPermission(map,getUser(),getAuthority());
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<PaymentBillVO> paymentBillVOList = paymentBillService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = paymentBillService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", paymentBillVOList);
            model.addAttribute("inTradeTotal",getTradeTotal(map,ConstantEnum.PAY_TRADE_TYPE_0.getCodeInt()));
            model.addAttribute("outTradeTotal",getTradeTotal(map,ConstantEnum.PAY_TRADE_TYPE_1.getCodeInt()));
        }catch (PermissionException e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "mchBill/list";
    }
    /**
     *  Description: 移动端交易查询
     *  @param map 查询参数
     **/
    @RequestMapping(value = "/mobileBillList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData mobileBillList(@RequestBody Map<String, Object> map){
        LOGGER.info("mobileBillList:map={}",map);
        if(map.get("currpage") == null || map.get("userId") == null){
            return ResponseData.failure(ConstantEnum.EXCEPTION_PARAM_NULL.getCodeStr(),ConstantEnum.EXCEPTION_PARAM_NULL.getValueStr());
        }
        User user = userService.selectById(Integer.valueOf(map.get("userId").toString()));
        if(user == null){
            return ResponseData.failure(ConstantEnum.EXCEPTION_USER_NOT_FOUND.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND.getValueStr());
        }
        List<Authority> authorityList = authorityService.selectByUserId(user.getId());
        if(authorityList == null || authorityList.isEmpty()){
            return ResponseData.failure(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getCodeStr(),ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
        }
        getDataPermission(map,getUser(),authorityList.get(0).getValue());
        Integer currpage = Integer.valueOf(map.get("currpage").toString());
        List<PaymentBillVO> paymentBillVOList = paymentBillService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
        Integer totalCount = paymentBillService.selectListCount(map);
        return  ResponseData.success(paymentBillVOList,currpage,ConstantEnum.LIST_PAGE_SIZE.getCodeInt(),totalCount);
    }


    private Map<String,Object> getDataPermission(Map<String,Object> map,User user,String authority){
        //数据权限
        if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(authority)){
            LOGGER.info("permission is admin");
        }else  if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(authority)) {
            map.put("areaId", user.getAreaId());
        } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(authority)) {
            map.put("agentId", user.getAgentId());
        }  else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(authority)) {
            map.put("companyId", user.getCompanyId());
        } else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(authority)) {
            map.put("subCompanyId", user.getSubCompanyId());
        }else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(authority)) {
            map.put("shopId", user.getShopId());
        }else if (ConstantEnum.AUTHORITY_MCH_CASHIER.getCodeStr().equals(authority)) {
            map.put("shopId", user.getShopId());
        }else {
            LOGGER.info(getUser().getUserAccount()+":"+ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            throw new PermissionException(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getCodeStr(),ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
        }
        return map;
    }
    /**
     * @Description: 收入（支出）总数
     * @param map
     * @param tradeType
     * @return
     * @Author: 柯军
     * @datetime:2015年6月12日下午2:12:55
     **/
    private TradeTotal getTradeTotal(Map<String, Object> map, Integer tradeType) {
        TradeTotal tradeTotal = new TradeTotal();
        if(Integer.valueOf(map.get("tradeType").toString()) < 0 || (Integer.valueOf(map.get("tradeType").toString()) >=0 && map.get("tradeType").equals(tradeType.toString()))){
            map.put("tradeType",tradeType);
            tradeTotal = paymentBillService.selectTradeTotal(map);
        }
        if (tradeTotal.getAmountTotal() == null)
            tradeTotal.setAmountTotal(0d);
        if (tradeTotal.getCountTotal() == null)
            tradeTotal.setCountTotal(0);
        return tradeTotal;
    }
}
