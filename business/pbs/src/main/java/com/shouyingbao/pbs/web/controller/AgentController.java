package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Agent;
import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.service.AgentService;
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
 * 2016/3/17 14:53
 **/
@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    AgentService agentService;

    @Autowired
    AreaService areaService;


    @RequestMapping(value = "/search")
    public String search() {
        return "/agent/agent";
    }

    @RequestMapping("/list")
    public String list(ModelMap model, @RequestBody Map<String, Object> map) {
        LOGGER.info("list:map={}", map);
        try {
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<AgentVO> areaList = agentService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = agentService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", areaList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "agent/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        AgentVO agentVO = new AgentVO();
        if (id != null && id > 0) {
            Agent agent = agentService.selectById(id);
            BeanUtils.copyProperties(agent, agentVO);
        }
        List<Area> areaList = areaService.selectListByPage(new HashMap<String, Object>(), null, null);
        agentVO.setAreaList(areaList);
        modelMap.addAttribute("agent", agentVO);
        return "agent/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(Agent agent) {
        LOGGER.info("save:agent={}", agent);
        try {
            if (agent.getId() == null) {
                agent.setCreateAt(DateUtil.getCurrDateTime());
                agent.setCreateBy(getUser().getId());
                agentService.insert(agent);
            } else {
                agent.setUpdateAt(DateUtil.getCurrDateTime());
                agent.setUpdateBy(getUser().getId());
                agentService.update(agent);
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
            Agent agent = agentService.selectById(id);
            agent.setUpdateAt(DateUtil.getCurrDateTime());
            agent.setUpdateBy(getUser().getId());
            agent.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            agentService.update(agent);
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
