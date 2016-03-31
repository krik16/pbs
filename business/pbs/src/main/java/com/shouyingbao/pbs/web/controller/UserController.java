package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.PermissionException;
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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
@RequestMapping("/user")
public class UserController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    MchCompanyService mchCompanyService;

    @Autowired
    MchSubCompanyService mchSubCompanyService;

    @Autowired
    MchShopService mchShopService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    AreaService areaService;

    @Autowired
    AgentService agentService;

    private  Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    @RequestMapping(value = "/search")
    public String search() {
        return "/user/user";
    }

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            chcekDataPermission(map);
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<UserVO> userList = userService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = userService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("currpage", currpage);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("list", userList);
        }catch (PermissionException e){
            LOGGER.error(e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "user/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        LOGGER.info("edit:id={}",id);
        User loginUser = getUser();
        UserVO userVO = new UserVO();
        List<MchShop> mchShopList;
        List<Role> roleList;
        Map<String,Object> areaMap = new HashMap<>();
        Map<String,Object> agentMap = new HashMap<>();
        Map<String,Object> companyMap = new HashMap<>();
        Map<String,Object> subCompanyMap = new HashMap<>();
        MchShopVO mchShopVO = new MchShopVO();
        //数据权限
        if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
            LOGGER.info("permission is admin");
        }else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
            agentMap.put("areaId", getUser().getAreaId());
            companyMap.put("areaId", getUser().getAreaId());
            subCompanyMap.put("areaId", getUser().getAreaId());
        } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
            agentMap.put("id", getUser().getAgentId());
            companyMap.put("agentId", getUser().getAgentId());
            subCompanyMap.put("agentId", getUser().getAgentId());
        } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority()) || ConstantEnum.AUTHORITY_MCH_FINANCE.getCodeStr().equals(getAuthority())) {
            MchCompany mchCompany = mchCompanyService.selectById(getUser().getCompanyId());
            if(mchCompany != null) {
                agentMap.put("id", mchCompany.getAgentId());
            }
            companyMap.put("id", getUser().getCompanyId());
            subCompanyMap.put("companyId", getUser().getCompanyId());
        }else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
            MchCompany mchCompany = mchCompanyService.selectById(getUser().getCompanyId());
            if(mchCompany != null) {
                agentMap.put("id", mchCompany.getAgentId());
            }
            companyMap.put("id", getUser().getCompanyId());
            subCompanyMap.put("id", getUser().getSubCompanyId());
        }  else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
            companyMap.put("id", getUser().getCompanyId());
            subCompanyMap.put("companyId", getUser().getCompanyId());
        } else {
            LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            return "mchShop/edit";
        }
        if (id != null && id > 0) {
            User user = userService.selectById(id);
            BeanUtils.copyProperties(user, userVO);
            Map<String,Object> shopMap = new HashMap<>();
            mchShopList = mchShopService.selectAllList(shopMap);
            UserRole userRole = userRoleService.selectByUserId(userVO.getId());
            if(userRole != null) {
                userVO.setRoleId(userRole.getRoleId());
            }
            roleList = roleService.selectByType(user.getIsEmployee());
        } else {
            mchShopList = mchShopService.selectOnlySelf();
            roleList = roleService.selectListByPage(new HashMap<String, Object>(), null, null);
        }
        List<Area> areaList = areaService.selectListByPage(areaMap,null,null);
        List<AgentVO> agentList = agentService.selectListByPage(agentMap,null,null);
        List<MchCompanyVO> mchCompanyList = mchCompanyService.selectListByPage(companyMap, null, null);
        List<MchSubCompanyVO> mchSubCompanyList = mchSubCompanyService.selectListByPage(subCompanyMap, null, null);
        userVO.setCompanyList(mchCompanyList);
        userVO.setSubCompanyVOList(mchSubCompanyList);
        userVO.setShopList(mchShopList);
        userVO.setRoleList(roleList);
        userVO.setAreaList(areaList);
        userVO.setAgentList(agentList);
        modelMap.addAttribute("entity", userVO);
        modelMap.addAttribute("authority", getAuthority());
        return "user/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(UserVO userVO){
        LOGGER.info("save:user={}", userVO);
        try {
            User user = new User();
            BeanUtils.copyProperties(userVO,user);
            if (user.getId() == null) {
                user.setCreateAt(DateUtil.getCurrDateTime());
                user.setCreateBy(getUser().getId());
                user.setUserPwd(md5PasswordEncoder.encodePassword(ConstantEnum.DEFAULT_PASSWORD.getCodeStr(),null));
                userService.insert(user);
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(userVO.getRoleId());
                userRoleService.insert(userRole);
            } else {
                user.setUpdateAt(DateUtil.getCurrDateTime());
                user.setUpdateBy(getUser().getId());
                userService.update(user);
                UserRole userRole = userRoleService.selectByUserId(user.getId());
                userRole.setRoleId(userVO.getRoleId());
                userRoleService.update(userRole);
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
            User user = userService.selectById(id);
            user.setUpdateAt(DateUtil.getCurrDateTime());
            user.setUpdateBy(getUser().getId());
            user.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            userService.update(user);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }
    }

    @RequestMapping("/resetPwd")
    @ResponseBody
    public ResponseData resetPwd(Integer id) {
        LOGGER.info("resetPwd:id={}", id);
        try {
            User user = userService.selectById(id);
            user.setUpdateAt(DateUtil.getCurrDateTime());
            user.setUpdateBy(getUser().getId());
            user.setUserPwd(md5PasswordEncoder.encodePassword(ConstantEnum.DEFAULT_PASSWORD.getCodeStr(), null));
            userService.update(user);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }
    }

    @RequestMapping("/getRoleByType")
    @ResponseBody
    public ResponseData getRoleByType(Integer parentId) {
        LOGGER.info("getRoleByType:parentId={}", parentId);
        try {
            List<Role> roleList = roleService.selectByType(parentId.byteValue());
            return ResponseData.success(roleList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_OPERATION_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_OPERATION_FAIL.getValueStr());
        }

    }

}
