package com.yuwenyun.demos.unit_test.cases;

import com.yuwenyun.demos.unit_test.basic.Employee;
import com.yuwenyun.demos.unit_test.mapper.EmployeeMapper;
import com.yuwenyun.demos.unit_test.service.EmployeeService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 1. 添加依赖：
 *   com.h2database:h2:${version}, org.springframework.boot:spring-boot-starter-test:${version}
 * 2. 准备初始化建表语句和初始数据（data.sql/init-table.sql）
 * 3. 新建测试
 *
 * @author lijing
 * @version 1.0
 * @date 2019-06-10 21:31
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DbTest {

    /**
     * 演示测试db调用api本身的测试
     */

    @Autowired
    private EmployeeMapper mapper;

    @Test
    public void testSelectAll() {
        final List<Employee> employees = mapper.selectAll();
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals("alice", employees.get(0).getName());
    }

    @Test
    public void testInsertEmployee(){
        Employee employee = new Employee();
        employee.setName("vince");
        employee.setAge(19);
        employee.setBirth(LocalDateTime.of(2019, 10, 10, 10, 0));

        mapper.insertEmployee(employee);
        Assert.assertTrue(employee.getId() != 0);
    }


    /**
     * 演示测试db调用api被mock的测试
     */

    @Autowired
    private EmployeeService service;

    @Before
    public void setUp(){
        mapper = Mockito.mock(EmployeeMapper.class);
        service.setMapper(mapper);
    }

    @Test
    public void testServiceSelectAll(){
        // 准备数据并定义mock对象的行为
        Employee employee = new Employee();
        employee.setName("alice");
        Mockito.when(mapper.selectAll()).thenReturn(Arrays.asList(employee));

        // 执行调用
        List<Employee> result = service.selectAll();

        // 验证
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("alice", result.get(0).getName());
    }
}