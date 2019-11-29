package com.yuwenyun.demos.unit_test.basic;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-05 19:21
 */
public class MySubService {

    private String name;
    public static int counter;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public static int getCounter(){
        increaseCounter();
        return counter;
    }

    private static void increaseCounter(){
        counter++;
    }
}