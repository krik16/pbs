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
            map.put("isEmployee",ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte());
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
        try{
            UserRole loginUserRole = userRoleService.selectByUserId(getUser().getId());
            UserVO userVO = new UserVO();
            Map<String,Object> areaMap = new HashMap<>();
            Map<String,Object> agentMap = new HashMap<>();
            //数据权限
            if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("areaId", getUser().getAreaId());
                areaMap.put("id",getUser().getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                agentMap.put("id", getUser().getAgentId());
                Agent agent = agentService.selectById(getUser().getAgentId());
                if(agent != null) {
                    areaMap.put("id", agent.getAreaId());
                }
            }else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "user/edit";
            }
            if (id != null && id > 0) {
                User user = userService.selectById(id);
                BeanUtils.copyProperties(user, userVO);
                UserRole userRole = userRoleService.selectByUserId(userVO.getId());
                if(userRole != null) {
                    userVO.setRoleId(userRole.getRoleId());
                }
                if(user.getAreaId() > 0){
                    agentMap.put("areaId",user.getAreaId());
                }
            }
            List<Role> roleList;
            if(id == getUser().getId() || ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){//修改自身或者管理员
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte(), loginUserRole.getRoleId());
            }else{
                roleList = roleService.selectByTypeAndIdLimit(ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte(), loginUserRole.getRoleId()+1);
            }
            List<Area> areaList = areaService.selectListByPage(areaMap, null, null);
            List<AgentVO> agentList = agentService.selectListByPage(agentMap,null,null);

            userVO.setRoleList(roleList);
            userVO.setAreaList(areaList);
            userVO.setAgentList(agentList);
            modelMap.addAttribute("entity", userVO);
            modelMap.addAttribute("authority", getAuthority());
        }
        catch (Exception e){
            LOGGER.info(e.getMessage());
            e.printStackTrace();
        }

        return "user/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(UserVO userVO){
        LOGGER.info("save:user={}", userVO);
        try {
            boolean result = userService.validateUserExist(userVO.getUserAccount(),userVO.getId());
            if(result){
                return ResponseData.failure(ConstantEnum.EXCEPTION_USER_IS_EXIST.getCodeStr(),ConstantEnum.EXCEPTION_USER_IS_EXIST.getValueStr());
            }
            User user = new User();
            BeanUtils.copyProperties(userVO,user);
            user.setIsEmployee(ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte());
            if (user.getId() == null) {
                user.setCreateAt(DateUtil.getCurrDateTime());
                user.setCreateBy(getUser().getId());
                user.setUserPwd(md5PasswordEncoder.encodePassword(ConstantEnum.DEFAULT_PASSWORD.getCodeStr(), null));
                user.setUserPhone(userVO.getUserAccount());
                userService.insert(user);
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(userVO.getRoleId());
                userRoleService.insert(userRole);
            } else {
                user.setUserPhone(userVO.getUserAccount());
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
    private Map<String,Object> chcekDataPermission(Map<String,Object> map ){
        if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
            LOGGER.info("permission is admin");
        } else if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
            map.put("areaId", getUser().getAreaId());
        } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
            map.put("agentId", getUser().getAgentId());
        } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
            map.put("companyId", getUser().getCompanyId());
        }else if (ConstantEnum.AUTHORITY_MCH_SUB_COMPANY.getCodeStr().equals(getAuthority())) {
            map.put("subCompanyId", getUser().getSubCompanyId());
        }else if (ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr().equals(getAuthority())) {
            map.put("shopId", getUser().getShopId());
        }  else {
            LOGGER.info(getUser().getUserAccount()+":"+ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
            throw new PermissionException(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getCodeStr(),ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
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
     * @return
     */
    @RequestMapping(value = "/saveChangePwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveChangePwd(UserParam userParam) {
        LOGGER.info("changePwd:userParam={}", userParam);
        try {
            User user = getUser();
            String md5OldPwd = md5PasswordEncoder.encodePassword(userParam.getOldPwd(), null);
            if(!md5OldPwd.equals(user.getUserPwd())){
                return ResponseData.failure(ConstantEnum.EXCEPTION_OLD_PWD_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_OLD_PWD_FAIL.getValueStr());
            }
            if(!userParam.getNewPwd1().equals(userParam.getNewPwd2())){
                return ResponseData.failure(ConstantEnum.EXCEPTION_NEW_PWD_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_NEW_PWD_FAIL.getValueStr());
            }
            user.setUserPwd(md5PasswordEncoder.encodePassword(userParam.getNewPwd1(), null));
            userService.update(user);
            return ResponseData.success();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_REFUND_AUTHORITY.getCodeStr(),ConstantEnum.EXCEPTION_REFUND_AUTHORITY.getValueStr());
        }
    }

    @RequestMapping("/getByShopId")
    @ResponseBody
    public ResponseData getByShopId(Integer parentId) {
        LOGGER.info("getByShopId:parentId={}", parentId);
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("shopId", parentId);
            map.put("roleId",ConstantEnum.ROLE_MCH_CASHIER.getCodeInt());
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

}
