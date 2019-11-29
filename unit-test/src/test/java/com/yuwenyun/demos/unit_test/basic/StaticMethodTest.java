package com.yuwenyun.demos.unit_test.basic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * 测试静态方法：
 *   1. @PrepareForTest({StaticClass.class})
 *   2. PowerMockito.mockStatic(StaticClass.class)
 *
 * @author lijing
 * @version 1.0
 * @date 2019-06-05 19:29
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MySubService.class)
public class StaticMethodTest {

    @Before
    public void setUp(){
        MySubService.counter = 0;
    }

    @Test
    public void testGetCounter(){
        // 准备被mock的静态类，并定义被mock的方法
        PowerMockito.mockStatic(MySubService.class);
        PowerMockito.when(MySubService.getCounter()).thenReturn(10);

        MyService testService = new MyService();
        int result = testService.getCounter();

        Assert.assertEquals(10, result);
    }

    @Test
    public void testPrivateStaticMethod() throws InvocationTargetException, IllegalAccessException {
        // spy需要被mock掉私有方法的类，并获取私有方法对象
        PowerMockito.spy(MySubService.class);
        Method privateStaticMethod = PowerMockito.method(MySubService.class, "increaseCounter");

        privateStaticMethod.invoke(MySubService.class);

        Assert.assertEquals(1, MySubService.counter);
    }

    @Test
    public void testMockPrivateStaticMethod() throws Exception {
        PowerMockito.spy(MySubService.class);
        PowerMockito.doNothing().when(MySubService.class, "increaseCounter");
        int result = MySubService.getCounter();
        Assert.assertEquals(0, result);
    }
}
