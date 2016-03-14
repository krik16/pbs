package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.Employee;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:05
 **/
public interface EmployeeService {

    void insert(Employee employee);

    void update(Employee employee);

    Employee selectById(Integer id);

    List<Employee> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
