package com.yuwenyun.demos.unit_test.controller;

import com.yuwenyun.demos.unit_test.basic.Employee;
import com.yuwenyun.demos.unit_test.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-11 16:15
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/test")
    public List<Employee> getAllEmployees(){
        return this.service.selectAll();
    }
}
