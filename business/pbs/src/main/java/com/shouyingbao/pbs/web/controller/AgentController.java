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
            //数据权限
            if(ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                map.put("stockholderId", getUser().getStockholderId());
            }else  if(ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())){
                map.put("areaId",getUser().getAreaId());
            }else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "agent/list";
            }
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
        Map<String,Object> areaMap = new HashMap<>();
        //数据权限
        if(ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())){
            LOGGER.info("permission is admin");
        }else if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
            areaMap.put("stockholderId",getUser().getStockholderId());
        }else  if(ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())){
            areaMap.put("id",getUser().getAreaId());
        }else {
            LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            return "agent/edit";
        }
        List<Area> areaList = areaService.selectListByPage(areaMap, null, null);
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

    @RequestMapping("/getAll")
    @ResponseBody
    public ResponseData getAll() {
        LOGGER.info("getAll");
        try {
            //数据权限
            Map<String,Object> map = new HashMap<>();
            if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else  if(ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())){
                map.put("areaId",getUser().getAreaId());
            }else  if(ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())){
                map.put("id",getUser().getAgentId());
            }else  if(ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())){
//                map.put("id",getUser().getAgentId());
            }else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return ResponseData.success();
            }
            List<AgentVO> list = agentService.selectListByPage(map, null, null);
            return ResponseData.success(list);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }
    }

    @RequestMapping("/getByAreaId")
    @ResponseBody
    public ResponseData getByAreaId(Integer parentId) {
        LOGGER.info("getByAreaId:parentId={}", parentId);
        try {
            if(parentId==0){
                return  ResponseData.success();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("areaId",parentId);
            List<AgentVO> agentList = agentService.selectListByPage(map,null,null);
            return ResponseData.success(agentList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_OPERATION_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_OPERATION_FAIL.getValueStr());
        }

    }
}
