package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Agent;
import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.AgentService;
import com.shouyingbao.pbs.service.MchCompanyService;
import com.shouyingbao.pbs.vo.AgentVO;
import com.shouyingbao.pbs.vo.MchCompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/mchCompany")
public class MchCompanyController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MchCompanyController.class);

    @Autowired
    MchCompanyService mchCompanyService;

    @Autowired
    AgentService agentService;

    @RequestMapping(value = "/search")
    public String search() {
        return "/mchCompany/mchCompany";
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
                map.put("id", getUser().getCompanyId());
            } else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "mchCompany/list";
            }
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<MchCompanyVO> mchCompanyList = mchCompanyService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = mchCompanyService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", mchCompanyList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "mchCompany/list";
    }


    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        MchCompany mchCompany = null;
        User user = getUser();
        Map<String,Object> agentMap = new HashMap<>();
        //数据权限
        if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
            LOGGER.info("permission is admin");
        }else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
            agentMap.put("areaId", user.getAreaId());
        } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
            agentMap.put("id", user.getAgentId());
        }else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
            MchCompany loginMchCompany = mchCompanyService.selectById(user.getCompanyId());
            if(loginMchCompany != null) {
                agentMap.put("id", loginMchCompany.getAgentId());
            }
        } else {
            LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            return "mchCompany/edit";
        }
        if (id != null && id > 0) {
            mchCompany = mchCompanyService.selectById(id);
        }
        List<AgentVO> agentList = agentService.selectListByPage(agentMap, null, null);
        modelMap.addAttribute("entity", mchCompany);
        modelMap.addAttribute("agentList", agentList);
        return "mchCompany/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(MchCompany mchCompany) {
        LOGGER.info("save:mchCompany={}", mchCompany);
        try {
            if (mchCompany.getId() == null) {
                mchCompany.setCreateAt(DateUtil.getCurrDateTime());
                mchCompany.setCreateBy(getUser().getId());
                mchCompanyService.insert(mchCompany);
            } else {
                mchCompany.setUpdateAt(DateUtil.getCurrDateTime());
                mchCompany.setUpdateBy(getUser().getId());
                mchCompanyService.update(mchCompany);
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
            MchCompany mchCompany = mchCompanyService.selectById(id);
            mchCompany.setUpdateAt(DateUtil.getCurrDateTime());
            mchCompany.setUpdateBy(getUser().getId());
            mchCompany.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            mchCompanyService.update(mchCompany);
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
            Map<String,Object> map = new HashMap<>();
            //数据权限
            if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else  if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("areaId", getUser().getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("agentId", getUser().getAgentId());
            }  else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                map.put("id", getUser().getCompanyId());
            } else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
                map.put("id", getUser().getCompanyId());
            }else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return ResponseData.success();
            }
            List<MchCompanyVO> list = mchCompanyService.selectListByPage(map, null, null);
            return ResponseData.success(list);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }
    }

    @RequestMapping("/getByAgentId")
    @ResponseBody
    public ResponseData getByAgentId(Integer parentId) {
        LOGGER.info("getByAgentId:parentId={}", parentId);
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("agentId", parentId);
            List<MchCompanyVO> list = mchCompanyService.selectListByPage(map, null, null);
            return ResponseData.success(list);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }
    }

    @RequestMapping("/getAgentById")
    @ResponseBody
    public ResponseData getAgentById(Integer parentId) {
        LOGGER.info("getAgentById:parentId={}", parentId);
        try {
            List<Agent> agentList = new ArrayList<>();
            MchCompany mchCompany = mchCompanyService.selectById(parentId);
            if (mchCompany != null) {
                Agent agent = agentService.selectById(mchCompany.getAgentId());
                agentList.add(agent);
            }
            return ResponseData.success(agentList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }
    }
}
