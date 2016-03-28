package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:46
 **/
public interface UserService {

    void insert(User user);

    void update(User user);

    User selectById(Integer id);

    List<UserVO> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);

    User selectByUserAccount(String userName);

    User selectByUserAccountAndPwd(String userName,String userPwd);

}
