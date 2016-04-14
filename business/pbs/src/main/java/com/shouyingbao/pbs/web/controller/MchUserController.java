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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/mchUser")
public class MchUserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MchUserController.class);

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

    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    @RequestMapping(value = "/search")
    public String search() {
        return "/mchUser/mchUser";
    }

    @RequestMapping("/list")
    public String list(ModelMap model, @RequestBody Map<String, Object> map) {
        LOGGER.info("list:map={}", map);
        try {
            chcekDataPermission(map);
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            map.put("isEmployee", ConstantEnum.USER_IS_EMPLOYEE_1.getCodeByte());
            List<UserVO> userList = userService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = userService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("currpage", currpage);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("list", userList);
        } catch (PermissionException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "mchUser/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        LOGGER.info("edit:id={}", id);
        try {
            User user = getUser();
            UserRole loginUserRole = userRoleService.selectByUserId(getUser().getId());
            UserVO userVO = new UserVO();
            Map<String, Object> areaMap = new HashMap<>();
            Map<String, Object> agentMap = new HashMap<>();
            Map<String, Object> companyMap = new HashMap<>();
            Map<String, Object> subCompanyMap = new HashMap<>();
            Map<String, Object> shopMap = new HashMap<>();
            //数据权限
            if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
                LOGGER.info("permission is admin");
            } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
                agentMap.put("stockholderId",user.getStockholderId());
                companyMap.put("stockholderId",user.getStockholderId());
                subCompanyMap.put("stockholderId",user.getStockholderId());
                shopMap.put("stockholderId",user.getStockholderId());
                areaMap.put("stockholderId",user.getStockholderId());
            }else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("areaId",user.getAreaId());
                companyMap.put("areaId",user.getAreaId());
                subCompanyMap.put("areaId",user.getAreaId());
                shopMap.put("areaId",user.getAreaId());
                areaMap.put("id",user.getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("id",user.getAgentId());
                companyMap.put("agentId",user.getAgentId());
                subCompanyMap.put("agentId",user.getAgentId());
                shopMap.put("agentId",user.getAgentId());
            } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                MchCompany mchCompany = mchCompanyService.selectById(getUser().getCompanyId());
                if (mchCompany != null) {
                    agentMap.put("id", mchCompany.getAgentId());
                }
                companyMap.put("id",user.getCompanyId());
                subCompanyMap.put("companyId",user.getCompanyId());
                shopMap.put("companyId",user.getCompanyId());
            } else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
                MchCompany mchCompany = mchCompanyService.selectById(getUser().getCompanyId());
                if (mchCompany != null) {
                    agentMap.put("id", mchCompany.getAgentId());
                }
                companyMap.put("id",user.getCompanyId());
                subCompanyMap.put("id",user.getSubCompanyId());
                shopMap.put("subCompanyId",user.getSubCompanyId());
            } else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
                companyMap.put("id",user.getCompanyId());
                subCompanyMap.put("id",user.getSubCompanyId());
                shopMap.put("id",user.getShopId());
            } else {
                LOGGER.info(getUser() + ":" + ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "mchUser/edit";
            }
            if (id != null && id > 0) {
                User oldUser = userService.selectById(id);
                BeanUtils.copyProperties(oldUser, userVO);
                UserRole userRole = userRoleService.selectByUserId(userVO.getId());
                if (userRole != null) {
                    userVO.setRoleId(userRole.getRoleId());
                }
            }
            List<Role> roleList;
            if(ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())){
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_1.getCodeByte(),0);
            }else if(id != null && id.equals(user.getId()) ||  ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_1.getCodeByte(), loginUserRole.getRoleId());
            } else {
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_1.getCodeByte(), loginUserRole.getRoleId() + 1);
            }
            //代理或公司
            if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority()) || ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                //增加子公司角色
                if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                    Role subCompanyRole = new Role();
                    subCompanyRole.setId(ConstantEnum.ROLE_MCH_SUB_COMPANY.getCodeInt());
                    subCompanyRole.setName(ConstantEnum.ROLE_MCH_SUB_COMPANY.getValueStr());
                    roleList.add(subCompanyRole);
                }
               //增加门店角色
                Role role = new Role();
                role.setId(ConstantEnum.ROLE_MCH_SHOPKEEPER.getCodeInt());
                role.setName(ConstantEnum.ROLE_MCH_SHOPKEEPER.getValueStr());
                roleList.add(role);
            }
            List<Area> areaList = areaService.selectListByPage(areaMap, null, null);
            List<AgentVO> agentList = agentService.selectListByPage(agentMap, null, null);
            List<MchCompanyVO> mchCompanyList = mchCompanyService.selectListByPage(companyMap, null, null);
            List<MchSubCompanyVO> mchSubCompanyList = mchSubCompanyService.selectListByPage(subCompanyMap, null, null);
            List<MchShopVO> mchShopList = mchShopService.selectListByPage(shopMap, null, null);

            userVO.setRoleList(roleList);
            userVO.setCompanyList(mchCompanyList);
            userVO.setSubCompanyVOList(mchSubCompanyList);
            userVO.setShopList(mchShopList);
            userVO.setAreaList(areaList);
            userVO.setAgentList(agentList);
            modelMap.addAttribute("entity", userVO);
            modelMap.addAttribute("authority", getAuthority());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

        return "mchUser/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(UserVO userVO) {
        LOGGER.info("save:user={}", userVO);
        try {
            boolean result = userService.validateUserExist(userVO.getUserAccount(), userVO.getId());
            if (result) {
                return ResponseData.failure(ConstantEnum.EXCEPTION_USER_IS_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_USER_IS_EXIST.getValueStr());
            }
            User user = new User();
            BeanUtils.copyProperties(userVO, user);
            user.setIsEmployee(ConstantEnum.USER_IS_EMPLOYEE_1.getCodeByte());
            if (user.getId() == null) {
                user.setCreateAt(DateUtil.getCurrDateTime());
                user.setCreateBy(getUser().getId());
                user.setUserPwd(md5PasswordEncoder.encodePassword(ConstantEnum.DEFAULT_PASSWORD.getCodeStr(), null));
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

    private Map<String, Object> chcekDataPermission(Map<String, Object> map) {
        List<String> roleIdList = new ArrayList<>();
        if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
            LOGGER.info("permission is admin");
        } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
            map.put("stockholderId", getUser().getStockholderId());
        } else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
            map.put("areaId", getUser().getAreaId());
        } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
            map.put("agentId", getUser().getAgentId());
            roleIdList.add(ConstantEnum.ROLE_MCH_COMPANY.getCodeStr());
            roleIdList.add(ConstantEnum.ROLE_MCH_SHOPKEEPER.getCodeStr());
        } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
            map.put("companyId", getUser().getCompanyId());
            roleIdList.add(ConstantEnum.ROLE_MCH_COMPANY.getCodeStr());
            roleIdList.add(ConstantEnum.ROLE_MCH_SUB_COMPANY.getCodeStr());
            roleIdList.add(ConstantEnum.ROLE_MCH_SHOPKEEPER.getCodeStr());
        } else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
            map.put("subCompanyId", getUser().getSubCompanyId());
            roleIdList.add(ConstantEnum.ROLE_MCH_SUB_COMPANY.getCodeStr());
            roleIdList.add(ConstantEnum.ROLE_MCH_SHOPKEEPER.getCodeStr());
        } else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
            map.put("shopId", getUser().getShopId());
            roleIdList.add(ConstantEnum.ROLE_MCH_SHOPKEEPER.getCodeStr());
            roleIdList.add(ConstantEnum.ROLE_MCH_CASHIER.getCodeStr());
        } else {
            LOGGER.info(getUser().getUserAccount() + ":" + ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            throw new PermissionException(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getCodeStr(), ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
        }
        if(!roleIdList.isEmpty()) {
            map.put("roleIdList", roleIdList);
        }
        return map;
    }

}
