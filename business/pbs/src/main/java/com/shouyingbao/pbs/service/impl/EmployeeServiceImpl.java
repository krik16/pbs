package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Employee;
import com.shouyingbao.pbs.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:08
 **/
@Service
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.EmployeeMapper";


    @Override
    public void insert(Employee employee) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", employee);
    }

    @Override
    public void update(Employee employee) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", employee);
    }

    @Override
    public Employee selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByPrimaryKey", map);
    }

    @Override
    public List<Employee> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }
}
