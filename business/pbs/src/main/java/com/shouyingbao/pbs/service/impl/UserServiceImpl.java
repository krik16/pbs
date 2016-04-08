package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.UserService;
import com.shouyingbao.pbs.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
