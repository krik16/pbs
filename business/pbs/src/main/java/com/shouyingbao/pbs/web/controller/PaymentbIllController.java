package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.entity.PaymentBill;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.vo.PaymentBillVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/8 17:33
 **/
@Controller
@RequestMapping("/bill")
public class PaymentbIllController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentbIllController.class);
    @Autowired
    PaymentBillService paymentBillService;

    @RequestMapping(value = "/search")
    public String search() {
        return "/bill/bill";
    }

    @RequestMapping("/list")
    public String list(ModelMap model, @RequestBody Map<String, Object> map) {
        LOGGER.info("list:map={}", map);
        try {
            //数据权限
            if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else  if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("areaId", getUser().getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("agentId", getUser().getAgentId());
            }  else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                map.put("companyId", getUser().getCompanyId());
            } else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
                map.put("subCompanyId", getUser().getSubCompanyId());
            }else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
                map.put("shopId", getUser().getShopId());
            }else if (ConstantEnum.AUTHORITY_MCH_CASHIER.getCodeStr().equals(getAuthority())) {
                map.put("shopId", getUser().getShopId());
            }else {
                LOGGER.info(getUser().getUserAccount()+":"+ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "bill/list";
            }
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<PaymentBillVO> mchCompanyList = paymentBillService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = paymentBillService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", mchCompanyList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "bill/list";
    }


    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(@RequestBody Map<String,Object> map){
        LOGGER.info("detail:map={}",map);
        ResponseData responseData;
        try {
            PaymentBill paymentBill = paymentBillService.selectById(Integer.valueOf(map.get("id").toString()));
            responseData = ResponseData.success(paymentBill);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(ConstantEnum.EXCEPTION_BILL_DETAIL.getCodeStr(),ConstantEnum.EXCEPTION_BILL_DETAIL.getValueStr());
        }
        return responseData;
    }
}
