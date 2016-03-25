package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.service.AreaService;
import com.shouyingbao.pbs.vo.AgentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/area")
public class AreaController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    AreaService areaService;

    @RequestMapping(value = "/search")
    public String search() {
        return "/area/area";
    }

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<Area> areaList = areaService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = areaService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("currpage", currpage);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("list", areaList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "area/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        AgentVO agentVO = new AgentVO();
        if (id != null && id > 0) {
            Area area = areaService.selectById(id);
            BeanUtils.copyProperties(area, agentVO);
        }
        List<Area> areaList = areaService.selectListByPage(new HashMap<String, Object>(), null, null);
        agentVO.setAreaList(areaList);
        modelMap.addAttribute("entity", agentVO);
        return "area/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(Area area){
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
}
