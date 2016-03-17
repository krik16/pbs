package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.service.AreaService;
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
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    AreaService areaService;

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<Area> areaList = areaService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer pageTotal = areaService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(pageTotal));
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", areaList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "area/list";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public ResponseData insert(@RequestBody Area area){
        LOGGER.info("insert:area={}",area);
        try {
            area.setCreateAt(DateUtil.getCurrDateTime());
            area.setCreateBy(getUser().getId());
            areaService.insert(area);
            return ResponseData.success();
        }catch (UserNotFoundException e){
            return ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseData update(@RequestBody Area area){
        LOGGER.info("update:area={}",area);
        try {
            area.setUpdateAt(DateUtil.getCurrDateTime());
            area.setUpdateBy(getUser().getId());
            areaService.update(area);
            return ResponseData.success();
        }catch (UserNotFoundException e){
            return ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }
}
