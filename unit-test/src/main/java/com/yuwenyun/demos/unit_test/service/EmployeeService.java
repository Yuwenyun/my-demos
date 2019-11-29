package com.yuwenyun.demos.unit_test.service;

import com.yuwenyun.demos.unit_test.basic.Employee;
import com.yuwenyun.demos.unit_test.mapper.EmployeeMapper;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-11 15:58
 */
@Component
@Data
public class EmployeeService {

    @Autowired
    private EmployeeMapper mapper;

    public List<Employee> selectAll(){
        return this.mapper.selectAll();
    }
}
