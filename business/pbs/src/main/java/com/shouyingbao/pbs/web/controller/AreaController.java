package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.entity.Stockholder;
import com.shouyingbao.pbs.service.AreaService;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.service.StockholderService;
import com.shouyingbao.pbs.vo.AreaVO;
import com.shouyingbao.pbs.vo.TradeTotal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    AreaService areaService;

    @Autowired
    StockholderService stockholderService;

    @Autowired
    PaymentBillService paymentBillService;

    @RequestMapping(value = "/search")
    public String search() {
        return "/area/area";
    }

    @RequestMapping("/list")
    public String list(ModelMap model, @RequestBody Map<String, Object> map) {
        LOGGER.info("list:map={}", map);
        try {
            //数据权限
            if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
                LOGGER.info("permission is admin");
            } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
                map.put("stockholderId", getUser().getStockholderId());
            } else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "area/list";
            }
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<AreaVO> areaList = areaService.selectListVOByPage(map, null,null);
            Map<String, Object> tradeMap = new HashMap<>();
            TradeTotal tradeTotal;
            for (AreaVO areaVO : areaList) {
                tradeMap.put("areaId", areaVO.getId());
                tradeMap.put("tradeType", ConstantEnum.PAY_TRADE_TYPE_0.getCodeInt());
                tradeTotal = paymentBillService.selectTradeTotal(tradeMap);
                if (tradeTotal == null || tradeTotal.getAmountTotal() == null) {
                    tradeTotal.setAmountTotal(0.00d);
                }
                areaVO.setInTotalCount(tradeTotal.getAmountTotal());
            }
            Collections.sort(areaList);
            //分页(由于要统计交易额排序，无法在数据库存统计，故不在数据库做分页)
            int startIndex = getStartIndex(currpage);
            int endIndex = getEndIndex(currpage,areaList.size());
            List<AreaVO> subList = areaList.subList(startIndex,endIndex);
            Integer totalCount = areaList.size();
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("currpage", currpage);
            model.addAttribute("totalCount", totalCount);

            model.addAttribute("list",subList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "area/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        AreaVO areaVO = new AreaVO();
        Map<String, Object> stockholderMap = new HashMap<>();
        //数据权限
        if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
            LOGGER.info("permission is admin");
        } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
            stockholderMap.put("id", getUser().getStockholderId());
        } else {
            LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            return "area/edit";
        }
        if (id != null && id > 0) {
            Area area = areaService.selectById(id);
            BeanUtils.copyProperties(area, areaVO);
        }
        List<Stockholder> stockholderList = stockholderService.selectListByPage(stockholderMap, null, null);
        areaVO.setStockholderList(stockholderList);
        modelMap.addAttribute("entity", areaVO);
        return "area/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(Area area) {
        LOGGER.info("save:area={}", area);
        try {

            if (area.getId() == null) {
                area.setCreateAt(DateUtil.getCurrDateTime());
                area.setCreateBy(getUser().getId());
                areaService.insert(area);
            } else {
                area.setUpdateAt(DateUtil.getCurrDateTime());
                area.setUpdateBy(getUser().getId());
                areaService.update(area);
            }
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }

    @RequestMapping("/cance")
    @ResponseBody
    public ResponseData cance(Integer id) {
        LOGGER.info("cance:id={}", id);
        try {
            Area area = areaService.selectById(id);
            area.setUpdateAt(DateUtil.getCurrDateTime());
            area.setUpdateBy(getUser().getId());
            area.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            areaService.update(area);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }

    }

    @RequestMapping("/getAll")
    @ResponseBody
    public ResponseData getAll() {
        LOGGER.info("getAll");
        try {
            //数据权限
            Map<String, Object> map = new HashMap<>();
            if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
                LOGGER.info("permission is admin");
            } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
                map.put("stockholderId", getUser().getStockholderId());
            } else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("areaId", getUser().getAreaId());
            } else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return ResponseData.success();
            }
            List<Area> areaList = areaService.selectListByPage(map, null, null);
            return ResponseData.success(areaList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }

    }
}
