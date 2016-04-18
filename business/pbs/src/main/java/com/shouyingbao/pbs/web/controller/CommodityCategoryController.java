package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.Commodity;
import com.shouyingbao.pbs.entity.CommodityCategory;
import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.CommodityCategoryService;
import com.shouyingbao.pbs.service.CommodityService;
import com.shouyingbao.pbs.service.MchShopService;
import com.shouyingbao.pbs.service.UserService;
import com.shouyingbao.pbs.vo.CommodityCategoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/commodityCategory")
public class CommodityCategoryController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityCategoryController.class);

    @Autowired
    CommodityCategoryService commodityCategoryService;

    @Autowired
    CommodityService commodityService;

    @Autowired
    MchShopService mchShopService;

    @Autowired
    UserService userService;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseData list(Integer userId){
        LOGGER.info("list:userId={}", userId);
        ResponseData responseData;
        try {
            User user = userService.selectById(userId);
            if(user == null){
                throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_FOUND.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND.getValueStr());
            }
            MchShop mchShop = mchShopService.selectById(user.getShopId());
            if(mchShop == null){
               LOGGER.warn("userId={},"+ConstantEnum.EXCEPTION_USER_NOT_FOUND_SHOP.getCodeStr(),userId);
                return  ResponseData.success();
            }
            Map<String,Object> categoryMap = new HashMap<>();
            categoryMap.put("shopId", mchShop.getId());
            Map<String,Object> commodityMap = new HashMap<>();
            List<CommodityCategory>commodityCategoryList = commodityCategoryService.selectListByPage(categoryMap, null,null);
            List<CommodityCategoryVO> commodityCategoryVOList = new ArrayList<>();
            for(CommodityCategory commodityCategory : commodityCategoryList){
                commodityMap.put("categoryId",commodityCategory.getId());
                List<Commodity> commodityList = commodityService.selectListByPage(commodityMap,null,null);
                CommodityCategoryVO commodityCategoryVO = new CommodityCategoryVO();
                BeanUtils.copyProperties(commodityCategory,commodityCategoryVO);
                commodityCategoryVO.setCommodityList(commodityList);
                commodityCategoryVOList.add(commodityCategoryVO);
            }
            responseData = ResponseData.success(commodityCategoryVOList);
        }catch (UserNotFoundException e){
            LOGGER.error(e.getMessage());
            responseData = ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(ConstantEnum.EXCEPTION_SYSTEM_ERROR.getCodeStr(),ConstantEnum.EXCEPTION_SYSTEM_ERROR.getValueStr());
        }
        return responseData;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(@RequestBody CommodityCategoryVO commodityCategoryVO){
        LOGGER.info("save:commodityCategoryVO={}", commodityCategoryVO);
        try {
            User user = userService.selectById(commodityCategoryVO.getUserId());
            if(user == null){
                throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_FOUND.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND.getValueStr());
            }
            MchShop mchShop = mchShopService.selectById(user.getShopId());
            if(mchShop == null){
                throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_FOUND_SHOP.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND_SHOP.getValueStr());
            }
            CommodityCategory commodityCategory = new CommodityCategory();
            BeanUtils.copyProperties(commodityCategoryVO, commodityCategory);
            commodityCategory.setShopId(mchShop.getId());
            commodityCategoryService.save(commodityCategory,user.getId());
            return ResponseData.success();
        }catch (UserNotFoundException e){
            LOGGER.error(e.getMessage());
            return ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_INSERT_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_INSERT_FAIL.getValueStr());
        }

    }

    @RequestMapping("/cance")
    @ResponseBody
    public ResponseData cance(@RequestBody Map<String, Object> map) {
        LOGGER.info("cance:map{}",map);
        try {
            Integer userId = Integer.valueOf(map.get("userId").toString());
            User user = userService.selectById(userId);
            if(user == null){
                throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_FOUND.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND.getValueStr());
            }
            CommodityCategory commodityCategory = commodityCategoryService.selectById(Integer.valueOf(map.get("id").toString()));
            commodityCategory.setUpdateAt(DateUtil.getCurrDateTime());
            commodityCategory.setUpdateBy(userId);
            commodityCategory.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            commodityCategoryService.update(commodityCategory);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            LOGGER.error(e.getMessage());
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }

    }
}
