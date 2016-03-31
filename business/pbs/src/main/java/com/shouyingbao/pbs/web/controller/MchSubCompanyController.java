package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.UserNotFoundException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.entity.MchSubCompany;
import com.shouyingbao.pbs.service.MchCompanyService;
import com.shouyingbao.pbs.service.MchSubCompanyService;
import com.shouyingbao.pbs.vo.MchCompanyVO;
import com.shouyingbao.pbs.vo.MchSubCompanyVO;
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
@RequestMapping("/mchSubCompany")
public class MchSubCompanyController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(MchSubCompanyController.class);

    @Autowired
    MchSubCompanyService mchSubCompanyService;

    @Autowired
    MchCompanyService mchCompanyService;

    @RequestMapping(value = "/search")
    public String search() {
        return "/mchSubCompany/mchSubCompany";
    }

    @RequestMapping("/list")
    public String list(ModelMap model,@RequestBody Map<String,Object> map){
        LOGGER.info("list:map={}", map);
        try {
            //数据权限
            if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else  if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("areaId", getUser().getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                map.put("agentId", getUser().getAgentId());
            } else if (ConstantEnum.AUTHORITY_MCH_COMPANY.getCodeStr().equals(getAuthority())) {
                map.put("companyId", getUser().getCompanyId());
            }  else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "mchSubCompany/list";
            }
            Integer currpage = Integer.valueOf(map.get("currpage").toString());
            List<MchSubCompanyVO> mchSubCompanyList = mchSubCompanyService.selectListByPage(map, currpage, ConstantEnum.LIST_PAGE_SIZE.getCodeInt());
            Integer totalCount = mchSubCompanyService.selectListCount(map);
            model.addAttribute("rowCount", getRowCount(totalCount));
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("currpage", currpage);
            model.addAttribute("list", mchSubCompanyList);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return "mchSubCompany/list";
    }

    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, Integer id) {
        LOGGER.info("edit:id={}", id);
        try {
            Map<String,Object> companyMap = new HashMap<>();
            //数据权限
            if(ConstantEnum.AUTHORITY_COMPANY_SHAREHOLDER.getCodeStr().equals(getAuthority())){
                LOGGER.info("permission is admin");
            }else  if (ConstantEnum.AUTHORITY_AREA_AGENT.getCodeStr().equals(getAuthority())) {
                companyMap.put("areaId", getUser().getAreaId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                companyMap.put("agentId", getUser().getAgentId());
            } else if (ConstantEnum.AUTHORITY_DISTRIBUTION_AGENT.getCodeStr().equals(getAuthority())) {
                companyMap.put("id", getUser().getCompanyId());
            }  else {
                LOGGER.info(ConstantEnum.EXCEPTION_NO_DATA_PERMISSION.getValueStr());
                return "mchSubCompany/edit";
            }
            MchSubCompanyVO mchSubCompanyVO = new MchSubCompanyVO();
            if (id != null && id > 0) {
                MchSubCompany mchSubCompany = mchSubCompanyService.selectById(id);
                BeanUtils.copyProperties(mchSubCompany, mchSubCompanyVO);
            }
            List<MchCompanyVO> mchCompanyList = mchCompanyService.selectListByPage(companyMap, null, null);
            mchSubCompanyVO.setMchCompanyList(mchCompanyList);
            modelMap.addAttribute("entity", mchSubCompanyVO);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return "mchSubCompany/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseData save(MchSubCompany mchSubCompany){
        LOGGER.info("save:mchSubCompany={}", mchSubCompany);
        try {

            if (mchSubCompany.getId() == null) {
                mchSubCompany.setCreateAt(DateUtil.getCurrDateTime());
                mchSubCompany.setCreateBy(getUser().getId());
                mchSubCompanyService.insert(mchSubCompany);
            } else {
                mchSubCompany.setUpdateAt(DateUtil.getCurrDateTime());
                mchSubCompany.setUpdateBy(getUser().getId());
                mchSubCompanyService.update(mchSubCompany);
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
            MchSubCompany mchSubCompany = mchSubCompanyService.selectById(id);
            mchSubCompany.setUpdateAt(DateUtil.getCurrDateTime());
            mchSubCompany.setUpdateBy(getUser().getId());
            mchSubCompany.setIsDelete(ConstantEnum.IS_DELETE_1.getCodeByte());
            mchSubCompanyService.update(mchSubCompany);
            return ResponseData.success();
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_CANCE_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_CANCE_FAIL.getValueStr());
        }

    }

    @RequestMapping("/getByCompanyId")
    @ResponseBody
    public ResponseData getByCompanyId(Integer parentId) {
        LOGGER.info("getByCompanyId:parentId={}", parentId);
        try {
            if(parentId == 0){
                return ResponseData.success();
            }
            List<MchSubCompany> mchSubCompanyList = mchSubCompanyService.selectByCompanyId(parentId);
            return ResponseData.success(mchSubCompanyList);
        } catch (UserNotFoundException e) {
            return ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_OPERATION_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_OPERATION_FAIL.getValueStr());
        }

    }

}
