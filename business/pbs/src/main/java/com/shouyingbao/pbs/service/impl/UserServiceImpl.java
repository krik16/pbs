package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.entity.UserExtendInfo;
import com.shouyingbao.pbs.entity.UserRole;
import com.shouyingbao.pbs.service.UserExtendInfoService;
import com.shouyingbao.pbs.service.UserRoleService;
import com.shouyingbao.pbs.service.UserService;
import com.shouyingbao.pbs.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:47
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.UserMapper";

    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserExtendInfoService userExtendInfoService;


    @Override
    public void insert(User user) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective", user);
    }

    @Override
    public void update(User user) {
        this.getBaseDao().insertBySql(NAMESPACE+".updateByPrimaryKeySelective", user);
    }

    @Override
    public User selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<UserVO> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        if(currentPage != null && pageSize != null) {
            map.put("currentPage", (currentPage - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }

    @Override
    public User selectByUserAccount(String userAccount) {
        Map<String,Object> map = new HashMap<>();
        map.put("userAccount",userAccount);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByUserAccount", map);
    }

    @Override
    public User selectByUserAccountAndPwd(String userAccount, String userPwd) {
        Map<String,Object> map = new HashMap<>();
        map.put("userAccount",userAccount);
        map.put("userPwd",userPwd);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByUserAccountAndPwd", map);
    }

    @Override
    public boolean validateUserExist(String userAccount, Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("userAccount",userAccount);
        map.put("id",id);
        User user = this.getBaseDao().selectOneBySql(NAMESPACE + ".validateUserExist", map);
        return user != null;
    }

    @Override
    public void save(User user,UserVO userVO,Integer createId) {
        if (user.getId() == null) {
            user.setCreateAt(DateUtil.getCurrDateTime());
            user.setCreateBy(createId);
            user.setUserPwd(md5PasswordEncoder.encodePassword(ConstantEnum.DEFAULT_PASSWORD.getCodeStr(), null));
            user.setUserPhone(user.getUserAccount());
            insert(user);
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(userVO.getRoleId());
            userRoleService.insert(userRole);
            UserExtendInfo userExtendInfo = new UserExtendInfo();
            userExtendInfo.setCustName(userVO.getCustName());
            userExtendInfo.setCustPhone(userVO.getCustPhone());
            userExtendInfo.setUserId(user.getId());
            userExtendInfoService.insert(userExtendInfo);
        } else {
            user.setUserPhone(userVO.getUserAccount());
            user.setUpdateAt(DateUtil.getCurrDateTime());
            user.setUpdateBy(createId);
            update(user);
            UserRole userRole = userRoleService.selectByUserId(user.getId());
            userRole.setRoleId(userVO.getRoleId());
            userRoleService.update(userRole);
            UserExtendInfo userExtendInfo = userExtendInfoService.selectByUserId(user.getId());
            if(userExtendInfo == null){
                userExtendInfo = new UserExtendInfo();
                userExtendInfo.setUserId(user.getId());
            }
            userExtendInfo.setCustName(userVO.getCustName());
            userExtendInfo.setCustPhone(userVO.getCustPhone());
            if(userExtendInfo.getId() != null) {
                userExtendInfoService.update(userExtendInfo);
            }else{
                userExtendInfoService.insert(userExtendInfo);
            }
        }
    }

    @Override
    public UserVO selectExtendInfoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectExtendInfoById",map);
    }
}
