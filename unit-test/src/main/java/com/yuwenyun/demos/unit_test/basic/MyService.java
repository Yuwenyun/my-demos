package com.yuwenyun.demos.unit_test.basic;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-05 19:21
 */
@Getter
@Setter
public class MyService {

    private MySubService service;

    public void callSubServiceInMethod(){
        System.out.println(service.getName());
    }

    public void callSubServiceMultiTimes(){
        System.out.println(service.getName());
        System.out.println(service.getName());
    }

    public void callSubServiceInMethodWithArgs(){
        service.setName("Hello");
        System.out.println(service.getName());
    }

    public void callPrivateMethod(){
        System.out.println(privateMethod("Owen"));
    }

    private String privateMethod(String name){
        return name + " OK";
    }

    private String privateMethod(){
        System.out.println("This is a private method test");
        return "OK";
    }

    public void throwException(){
        throw new RuntimeException("test throwing exception");
    }

    public int getCounter(){
        return MySubService.getCounter();
    }
}
