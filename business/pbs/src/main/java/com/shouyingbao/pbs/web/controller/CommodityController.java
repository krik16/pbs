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
import com.shouyingbao.pbs.vo.CommodityVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * kejun
 * 2016/3/17 11:05
 **/
@Controller
@RequestMapping("/commodity")
public class CommodityController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    CommodityCategoryService commodityCategoryService;

    @Autowired
    CommodityService commodityService;

    @Autowired
    MchShopService mchShopService;

    @Autowired
    UserService userService;

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(@RequestBody CommodityVO commodityVO){
        LOGGER.info("save:commodityVO={}", commodityVO);
        try {
            User user = userService.selectById(commodityVO.getUserId());
            if(user == null){
                throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_FOUND.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND.getValueStr());
            }
            MchShop mchShop = mchShopService.selectById(user.getShopId());
            if(mchShop == null){
                throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_FOUND_SHOP.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND_SHOP.getValueStr());
            }
            Commodity commodity = new Commodity();
            BeanUtils.copyProperties(commodityVO, commodity);
            CommodityCategory commodityCategory = commodityCategoryService.selectById(commodityVO.getCategoryId());
            if(commodityCategory == null){
                commodityCategory = new CommodityCategory();
                commodityCategory.setName(commodityVO.getCategoryName());
                commodityCategory.setDesc(commodityVO.getCategoryDesc());
                commodityCategory.setShopId(mchShop.getId());
                commodityCategoryService.save(commodityCategory, user.getId());
            }
            commodity.setCategoryId(commodityCategory.getId());
            commodityService.save(commodity,user.getId());
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
        LOGGER.info("cance:map={}",map);
        try {
            Integer userId = Integer.valueOf(map.get("userId").toString());
            User user = userService.selectById(userId);
            if(user == null){
                throw new UserNotFoundException(ConstantEnum.EXCEPTION_USER_NOT_FOUND.getCodeStr(),ConstantEnum.EXCEPTION_USER_NOT_FOUND.getValueStr());
            }
            Commodity commodity = commodityService.selectById(Integer.valueOf(map.get("id").toString()));
            commodity.setUpdateAt(DateUtil.getCurrDateTime());
            commodity.setUpdateBy(userId);
            commodity.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            commodityService.update(commodity);
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
