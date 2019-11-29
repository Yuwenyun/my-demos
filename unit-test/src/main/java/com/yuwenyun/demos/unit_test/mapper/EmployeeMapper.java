package com.yuwenyun.demos.unit_test.mapper;

import com.yuwenyun.demos.unit_test.basic.Employee;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-10 21:46
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 向数据库插入employee
     * @param employee 待插入数据
     * @return 成功则返回1，否则0
     */
    int insertEmployee(Employee employee);

    List<Employee> selectAll();
}
