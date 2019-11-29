package com.yuwenyun.demos.unit_test.basic;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-10 21:37
 */
@Data
public class Employee {

    private int id;
    private String name;
    private int age;
    private LocalDateTime birth;
}
