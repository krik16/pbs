package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.MchCompanyService;
import com.shouyingbao.pbs.service.MchShopService;
import com.shouyingbao.pbs.service.MchSubCompanyService;
import com.shouyingbao.pbs.vo.MchShopVO;
import com.shouyingbao.pbs.vo.MchSubCompanyVO;
import com.shouyingbao.pbs.vo.UserVO;
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
@RequestMapping("/user")
public class UserController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    MchCompanyService mchCompanyService;

    @Autowired
    MchSubCompanyService mchSubCompanyService;

    @Autowired
    MchShopService mchShopService;

    @RequestMapping(value = "/search")
    public String search() {
        return "/user/user";
    }

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<User> userList = userService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = userService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("currpage", currpage);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("list", userList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "area/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        LOGGER.info("edit:id={}",id);
        UserVO userVO = new UserVO();
        if (id != null && id > 0) {
            User user = userService.selectById(id);
            BeanUtils.copyProperties(user, userVO);
        }
        List<MchCompany> mchCompanyList = mchCompanyService.selectListByPage(new HashMap<String, Object>(), null, null);
        List<MchSubCompanyVO> mchSubCompanyList = mchSubCompanyService.selectListByPage(new HashMap<String, Object>(), null, null);
        List<MchShopVO> mchShopList = mchShopService.selectListByPage(new HashMap<String, Object>(), null, null);
        userVO.setMchCompanieList(mchCompanyList);
        userVO.setMchSubCompanyList(mchSubCompanyList);
        userVO.setMchShopList(mchShopList);
        modelMap.addAttribute("entity", userVO);
        return "user/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(User user){
        LOGGER.info("save:user={}", user);
        try {

            if (user.getId() == null) {
                user.setCreateAt(DateUtil.getCurrDateTime());
                user.setCreateBy(getUser().getId());
                userService.insert(user);
            } else {
                user.setUpdateAt(DateUtil.getCurrDateTime());
                user.setUpdateBy(getUser().getId());
                userService.update(user);
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
}
