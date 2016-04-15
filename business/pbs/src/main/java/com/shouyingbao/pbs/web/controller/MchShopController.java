package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.*;
import com.shouyingbao.pbs.service.*;
import com.shouyingbao.pbs.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * kejun
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/mchShop")
public class MchShopController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(MchShopController.class);

    @Autowired
    MchShopService mchShopService;

    @Autowired
    MchCompanyService mchCompanyService;

    @Autowired
    MchSubCompanyService mchSubCompanyService;

    @Autowired
    WeixinMchService weixinMchService;

    @Autowired
    AliMchService aliMchService;

    @Autowired
    AgentService agentService;

    @Autowired
    PaymentBillService paymentBillService;

    @RequestMapping(value = "/search")
    public String search(Integer subCompanyId,ModelMap model) {
        model.addAttribute("subCompanyId",subCompanyId);
        return "/mchShop/mchShop";
    }

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            User user = getUser();
            //数据权限
            if(ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            } else if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                map.put("stockholderId",user.getStockholderId());
            } else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("areaId", user.getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("agentId",user.getAgentId());
            } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                map.put("companyId", user.getCompanyId());
            }else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
                map.put("subCompanyId", user.getSubCompanyId());
            }else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
                map.put("id", user.getShopId());
            }else if (ConstantEnum.AUTHORITY_MCH_CASHIER.getCodeStr().equals(getAuthority())) {
                map.put("id",user.getShopId());
            }   else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "mchShop/list";
            }
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<MchShopVO> shopVOList = mchShopService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Map<String, Object> tradeMap = new HashMap<>();
            TradeTotal tradeTotal;
            for (MchShopVO mchShopVO : shopVOList) {
                tradeMap.put("shopId", mchShopVO.getId());
                tradeMap.put("tradeType", ConstantEnum.PAY_TRADE_TYPE_0.getCodeInt());
                tradeTotal = paymentBillService.selectTradeTotal(tradeMap);
                if (tradeTotal == null || tradeTotal.getAmountTotal() == null) {
                    tradeTotal.setAmountTotal(0.00d);
                }
                mchShopVO.setInTotalCount(tradeTotal.getAmountTotal());
            }
            Collections.sort(shopVOList);
            //分页(由于要统计交易额排序，无法在数据库存统计，故不在数据库做分页)
            int startIndex = getStartIndex(currpage);
            int endIndex = getEndIndex(currpage, shopVOList.size());
            List<MchShopVO> subList = shopVOList.subList(startIndex, endIndex);
            Integer totalCount =shopVOList.size();
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", subList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "mchShop/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        LOGGER.info("edit:id={}",id);
        try{
            User user = getUser();
            Map<String,Object> agentMap = new HashMap<>();
            Map<String,Object> companyMap = new HashMap<>();
            Map<String,Object> subCompanyMap = new HashMap<>();
            MchShopVO mchShopVO = new MchShopVO();
            //数据权限
            if(ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            } else if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                agentMap.put("stockholderId",user.getAreaId());
                companyMap.put("stockholderId", user.getAreaId());
                subCompanyMap.put("stockholderId", user.getAreaId());
            }else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("areaId", user.getAreaId());
                companyMap.put("areaId", user.getAreaId());
                subCompanyMap.put("areaId", user.getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("id", user.getAgentId());
                companyMap.put("agentId", user.getAgentId());
                subCompanyMap.put("agentId", user.getAgentId());
            } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                MchCompany mchCompany = mchCompanyService.selectById(user.getCompanyId());
                if(mchCompany != null) {
                    agentMap.put("id", mchCompany.getAgentId());
                }
                companyMap.put("id", user.getCompanyId());
                subCompanyMap.put("companyId", user.getCompanyId());
            }else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
                MchCompany mchCompany = mchCompanyService.selectById(user.getCompanyId());
                if(mchCompany != null) {
                    agentMap.put("id", mchCompany.getAgentId());
                }
                companyMap.put("id", user.getCompanyId());
                subCompanyMap.put("id", user.getSubCompanyId());
            }  else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
                companyMap.put("id", user.getCompanyId());
                subCompanyMap.put("id", user.getSubCompanyId());
            } else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "mchShop/edit";
            }
            if (id != null && id > 0) {
                MchShop mchShop = mchShopService.selectById(id);
                BeanUtils.copyProperties(mchShop, mchShopVO);
                AliMch aliMch = aliMchService.selectByShopId(mchShop.getId());
                if(aliMch != null){
                    mchShopVO.setAliKey(aliMch.getKey());
                    mchShopVO.setAliPid(aliMch.getPid());
                }
                WeixinMch weixinMch = weixinMchService.selectByShopId(mchShop.getId());
                if(weixinMch != null){
                    mchShopVO.setWeixinMchId(weixinMch.getMchId());
                }
            }

            List<AgentVO> agentVOList = agentService.selectListByPage(agentMap,null,null);
            List<MchCompanyVO> mchCompanyList = mchCompanyService.selectListByPage(companyMap, null, null);
            List<MchSubCompanyVO> mchSubCompanyVOList = mchSubCompanyService.selectListByPage(subCompanyMap, null, null);


            mchShopVO.setAgentVOList(agentVOList);
            mchShopVO.setCompanyList(mchCompanyList);
            mchShopVO.setSubCompanyVOList(mchSubCompanyVOList);
            modelMap.addAttribute("entity", mchShopVO);
            modelMap.addAttribute("authority",getAuthority());

        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return "mchShop/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(MchShopVO mchShopVO){
        LOGGER.info("save:mchShop={}", mchShopVO);
        try {
            User user = getUser();
            AliMch aliMch;
            WeixinMch weixinMch;
            MchShop mchShop = new MchShop();
            BeanUtils.copyProperties(mchShopVO,mchShop);
            if (mchShopVO.getId() == null) {
                mchShop.setCreateAt(DateUtil.getCurrDateTime());
                mchShop.setCreateBy(user.getId());
                aliMch = new AliMch();
                aliMch.setCreateAt(DateUtil.getCurrDateTime());
                aliMch.setCreateBy(user.getId());
                weixinMch = new WeixinMch();
                weixinMch.setCreateAt(DateUtil.getCurrDateTime());
                weixinMch.setCreateBy(getUser().getId());
            } else {
                mchShop.setUpdateAt(DateUtil.getCurrDateTime());
                mchShop.setUpdateBy(getUser().getId());
                aliMch = aliMchService.selectByShopId(mchShopVO.getId());
                if(aliMch != null) {
                    aliMch.setUpdateAt(DateUtil.getCurrDateTime());
                    aliMch.setUpdateBy(user.getId());
                }else {
                    aliMch = new AliMch();
                    aliMch.setCreateAt(DateUtil.getCurrDateTime());
                    aliMch.setCreateBy(user.getId());
                }
                weixinMch = weixinMchService.selectByShopId(mchShopVO.getId());
                if(weixinMch != null) {
                    weixinMch.setUpdateAt(DateUtil.getCurrDateTime());
                    weixinMch.setUpdateBy(user.getId());
                }
                else {
                    weixinMch = new WeixinMch();
                    weixinMch.setCreateAt(DateUtil.getCurrDateTime());
                    weixinMch.setCreateBy(user.getId());
                }
            }
            aliMch.setKey(mchShopVO.getAliKey());
            aliMch.setPid(mchShopVO.getAliPid());
            weixinMch.setMchId(mchShopVO.getWeixinMchId());
            mchShopService.save(mchShop,aliMch,weixinMch);
            return ResponseData.success();
        }catch (UserNotFoundException e){
            return ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }

    @RequestMapping("/cance")
    @ResponseBody
    public ResponseData cance(Integer id) {
        LOGGER.info("cance:id={}", id);
        try {
            MchShop mchShop = mchShopService.selectById(id);
            mchShop.setUpdateAt(DateUtil.getCurrDateTime());
            mchShop.setUpdateBy(getUser().getId());
            mchShop.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            mchShopService.update(mchShop);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }
    }

    @RequestMapping("/getByCompanyId")
    @ResponseBody
    public ResponseData getByCompanyId(Integer parentId) {
        LOGGER.info("getByCompanyId:parentId={}", parentId);
        try {
            List<MchShop> mchShopList = new ArrayList<>();
            if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
                mchShopList.add(mchShopService.selectById(getUser().getShopId()));
            }else {
                mchShopList = mchShopService.selectByCompanyId(parentId);
            }
            return ResponseData.success(mchShopList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_OPERATION_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_OPERATION_FAIL.getValueStr());
        }

    }

    @RequestMapping("/getBySubCompanyId")
    @ResponseBody
    public ResponseData getBySubCompanyId(Integer parentId) {
        LOGGER.info("getBySubCompanyId:parentId={}", parentId);
        try {
            List<MchShop> mchShopList = new ArrayList<>();
            if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
                mchShopList.add(mchShopService.selectById(getUser().getShopId()));
            }else {
                mchShopList = mchShopService.selectBySubCompanyId(parentId);
            }
            return ResponseData.success(mchShopList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_OPERATION_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_OPERATION_FAIL.getValueStr());
        }

    }

}
