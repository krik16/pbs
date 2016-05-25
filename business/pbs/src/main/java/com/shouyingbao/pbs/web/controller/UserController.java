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
import org.springframework.web.bind.annotation.RequestMethod;
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
public class UserController extends BaseController {

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

    @Autowired
    StockholderService stockholderService;

    @Autowired
    UserExtendInfoService userExtendInfoService;

    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    @RequestMapping(value = "/search")
    public String search() {
        return "/user/user";
    }

    @RequestMapping("/list")
    public String list(ModelMap model, @RequestBody Map<String, Object> map) {
        LOGGER.info("list:map={}", map);
        try {
            chcekDataPermission(map);
            map.put("isEmployee", ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte());
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
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
        return "user/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        LOGGER.info("edit:id={}", id);
        try {
            Map<String, Object> stockholderMap = new HashMap<>();
            Map<String, Object> areaMap = new HashMap<>();
            Map<String, Object> agentMap = new HashMap<>();
            //数据权限
            if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
                LOGGER.info("permission is admin");
            } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
                stockholderMap.put("id", getUser().getStockholderId());
                agentMap.put("stockholderId", getUser().getStockholderId());
                areaMap.put("stockholderId", getUser().getStockholderId());
            } else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("areaId", getUser().getAreaId());
                areaMap.put("id", getUser().getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("id", getUser().getAgentId());
                Agent agent = agentService.selectById(getUser().getAgentId());
                if (agent != null) {
                    areaMap.put("id", agent.getAreaId());
                }
            } else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "user/edit";
            }
            UserRole loginUserRole = userRoleService.selectByUserId(getUser().getId());
            UserVO userVO = new UserVO();
            if (id != null && id > 0) {
                userVO = userService.selectExtendInfoById(id);
                UserRole userRole = userRoleService.selectByUserId(userVO.getId());
                if (userRole != null) {
                    userVO.setRoleId(userRole.getRoleId());
                }
                if (userVO.getAreaId() > 0) {
                    agentMap.put("areaId", userVO.getAreaId());
                }
            }
            List<Role> roleList;
            if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte(), 0);
            } else if (getUser().getId().equals(id) || ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {//修改自身或者股东
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte(), loginUserRole.getRoleId());
            } else {
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte(), loginUserRole.getRoleId() + 1);
            }
            List<Stockholder> stockholderList = stockholderService.selectListByPage(stockholderMap, null, null);
            List<Area> areaList = areaService.selectListByPage(areaMap, null, null);
            List<AgentVO> agentList = agentService.selectListByPage(agentMap, null, null);

            userVO.setRoleList(roleList);
            userVO.setStockholderList(stockholderList);
            userVO.setAreaList(areaList);
            userVO.setAgentList(agentList);
            modelMap.addAttribute("entity", userVO);
            modelMap.addAttribute("authority", getAuthority());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

        return "user/edit";
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
            user.setIsEmployee(ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte());
            userService.save(user, userVO, getUser().getId());
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
        if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(getAuthority())) {
            LOGGER.info("permission is admin");
        } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())) {
            map.put("stockholderId", getUser().getStockholderId());
        } else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
            map.put("areaId", getUser().getAreaId());
        } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
            map.put("agentId", getUser().getAgentId());
        } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
            map.put("companyId", getUser().getCompanyId());
        } else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
            map.put("subCompanyId", getUser().getSubCompanyId());
        } else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
            map.put("shopId", getUser().getShopId());
        } else {
            LOGGER.info(getUser().getUserAccount() + ":" + ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            throw new PermissionException(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getCodeStr(), ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
        }
        return map;
    }


    @RequestMapping("/changePwd")
    public String changePwd() {
        return "changePwd";
    }

    /**
     * 修改密码
     *
     *
     */
    @RequestMapping(value = "/saveChangePwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveChangePwd(UserParam userParam) {
        LOGGER.info("changePwd:userParam={}", userParam);
        try {
            User user = getUser();
            String md5OldPwd = md5PasswordEncoder.encodePassword(userParam.getOldPwd(), null);
            if (!md5OldPwd.equals(user.getUserPwd())) {
                return ResponseData.failure(ConstantEnum.EXCEPTION_OLD_PWD_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_OLD_PWD_FAIL.getValueStr());
            }
            if (!userParam.getNewPwd1().equals(userParam.getNewPwd2())) {
                return ResponseData.failure(ConstantEnum.EXCEPTION_NEW_PWD_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_NEW_PWD_FAIL.getValueStr());
            }
            user.setUserPwd(md5PasswordEncoder.encodePassword(userParam.getNewPwd1(), null));
            userService.update(user);
            return ResponseData.success();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_REFUND_AUTHORITY.getCodeStr(), ConstantEnum.EXCEPTION_REFUND_AUTHORITY.getValueStr());
        }
    }

    @RequestMapping("/getByShopId")
    @ResponseBody
    public ResponseData getByShopId(Integer parentId) {
        LOGGER.info("getByShopId:parentId={}", parentId);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("shopId", parentId);
            map.put("roleId", ConstantEnum.ROLE_MCH_CASHIER.getCodeInt());
            List<UserVO> list = userService.selectListByPage(map, null, null);
            return ResponseData.success(list);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_OPERATION_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_OPERATION_FAIL.getValueStr());
        }
    }

    @RequestMapping("/info")
    public String edit(ModelMap modelMap) {
        try {
            User loginUser = getUser();
            UserVO userVO = userService.selectExtendInfoById(getUser().getId());
            String authority = getAuthority();
            String roleName = null;
            Stockholder stockholder = null;
            Area area = null;
            Agent agent = null;
            MchCompany mchCompany = null;
            MchSubCompany mchSubCompany = null;
            MchShop mchShop = null;
            //管理员
            if (ConstantEnum.AUTHORITY_ADMINISTRATOR.getCodeStr().equals(authority)) {
                roleName = ConstantEnum.AUTHORITY_ADMINISTRATOR.getValueStr();
                //股东
            } else if (ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(authority)) {
                roleName = ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getValueStr();
                stockholder = stockholderService.selectById(loginUser.getStockholderId());
                //区域代理
            } else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(authority)) {
                roleName = ConstantEnum.AUTHORITY_AREA_AGENT.getValueStr();
                area = areaService.selectById(loginUser.getAreaId());
                if (area != null) {
                    stockholder = stockholderService.selectById(area.getStockholderId());
                }
                //分销代理
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(authority)) {
                roleName = ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getValueStr();
                agent = agentService.selectById(loginUser.getAgentId());
                if (agent != null) {
                    area = areaService.selectById(agent.getAreaId());
                    if (area != null) {
                        stockholder = stockholderService.selectById(area.getStockholderId());
                    }
                }
                //总公司
            } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(authority)) {
                roleName = ConstantEnum.AUTHORITY_MCH_COMPANY.getValueStr();
                mchCompany = mchCompanyService.selectById(loginUser.getCompanyId());
                if (mchCompany != null) {
                    agent = agentService.selectById(mchCompany.getAgentId());
                    if (agent != null) {
                        area = areaService.selectById(agent.getAreaId());
                        if (area != null) {
                            stockholder = stockholderService.selectById(area.getStockholderId());
                        }
                    }
                }
                //子公司
            } else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(authority)) {
                roleName = ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getValueStr();
                mchSubCompany = mchSubCompanyService.selectById(loginUser.getSubCompanyId());
                if (mchSubCompany != null) {
                    mchCompany = mchCompanyService.selectById(mchSubCompany.getCompanyId());
                    if (mchCompany != null) {
                        agent = agentService.selectById(mchCompany.getAgentId());
                        if (agent != null) {
                            area = areaService.selectById(agent.getAreaId());
                            if (area != null) {
                                stockholder = stockholderService.selectById(area.getStockholderId());
                            }
                        }
                    }
                }
                //店长或收银员
            } else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(authority) || ConstantEnum.AUTHORITY_MCH_CASHIER.getCodeStr().equals(authority)) {
                if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(authority)) {
                    roleName = ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getValueStr();
                } else {
                    roleName = ConstantEnum.AUTHORITY_MCH_CASHIER.getValueStr();
                }
                mchShop = mchShopService.selectById(loginUser.getShopId());
                if (mchShop != null) {
                    mchSubCompany = mchSubCompanyService.selectById(mchShop.getSubCompanyId());
                    if (mchSubCompany != null) {//存在所属子公司
                        mchCompany = mchCompanyService.selectById(mchSubCompany.getCompanyId());
                        if (mchCompany != null) {
                            agent = agentService.selectById(mchCompany.getAgentId());
                            if (agent != null) {
                                area = areaService.selectById(agent.getAreaId());
                                if (area != null) {
                                    stockholder = stockholderService.selectById(area.getStockholderId());
                                }
                            }
                        }
                    } else {//存在所属总公司
                        mchCompany = mchCompanyService.selectById(mchShop.getCompanyId());
                        if (mchCompany != null) {
                            agent = agentService.selectById(mchCompany.getAgentId());
                            if (agent != null) {
                                area = areaService.selectById(agent.getAreaId());
                                if (area != null) {
                                    stockholder = stockholderService.selectById(area.getStockholderId());
                                }
                            }
                        } else {//存在所属分销代理
                            agent = agentService.selectById(mchShop.getAgentId());
                            if (agent != null) {
                                area = areaService.selectById(agent.getAreaId());
                                if (area != null) {
                                    stockholder = stockholderService.selectById(area.getStockholderId());
                                }
                            }
                        }
                    }
                }
            }
            userVO.setRoleName(roleName);
            if (stockholder != null) {
                userVO.setStockholderName(stockholder.getName());
            }
            if(area != null) {
                userVO.setAreaName(area.getName());
            }
            if(agent != null) {
                userVO.setAgentName(agent.getName());
            }
            if(mchCompany != null) {
                userVO.setCompanyName(mchCompany.getName());
            }
            if(mchSubCompany != null) {
                userVO.setSubCompanyName(mchSubCompany.getName());
            }
            if(mchShop != null) {
                userVO.setShopName(mchShop.getName());
            }
            modelMap.addAttribute("entity", userVO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return "/user/info";
    }
}
