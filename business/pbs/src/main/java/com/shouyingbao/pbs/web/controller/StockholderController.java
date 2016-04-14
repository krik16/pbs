package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Stockholder;
import com.shouyingbao.pbs.service.StockholderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/stockholder")
public class StockholderController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(StockholderController.class);

    @Autowired
    StockholderService stockholderService;

    @RequestMapping(value = "/search")
    public String search() {
        return "/stockholder/stockholder";
    }

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            //数据权限
            if(ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                map.put("stockholderId",getUser().getStockholderId());
            }else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "stockholder/list";
            }
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<Stockholder> stockholderList = stockholderService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = stockholderService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("currpage", currpage);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("list", stockholderList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "stockholder/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        Stockholder stockholder = new Stockholder();
        if (id != null && id > 0) {
            stockholder = stockholderService.selectById(id);
        }
        modelMap.addAttribute("entity", stockholder);
        return "stockholder/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(Stockholder stockholder){
        LOGGER.info("save:area={}", stockholder);
        try {

            if (stockholder.getId() == null) {
                stockholder.setCreateAt(DateUtil.getCurrDateTime());
                stockholder.setCreateBy(getUser().getId());
                stockholderService.insert(stockholder);
            } else {
                stockholder.setUpdateAt(DateUtil.getCurrDateTime());
                stockholder.setUpdateBy(getUser().getId());
                stockholderService.update(stockholder);
            }
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
            Stockholder stockholder = stockholderService.selectById(id);
            stockholder.setUpdateAt(DateUtil.getCurrDateTime());
            stockholder.setUpdateBy(getUser().getId());
            stockholder.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            stockholderService.update(stockholder);
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
            Map<String,Object> map = new HashMap<>();
            if(ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                map.put("id", getUser().getStockholderId());
            }else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return ResponseData.success();
            }
            List<Stockholder> stockholderList = stockholderService.selectListByPage(map,null,null);
            return ResponseData.success(stockholderList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }

    }
}
