package com.yuwenyun.demos.unit_test.basic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-05 19:17
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MyService.class)
public class PrivateMethodTest {

    @Test
    public void testPrivateMethod() throws InvocationTargetException, IllegalAccessException {
        // 通过PowerMock获取私有方法对象
        Method method = PowerMockito.method(MyService.class, "privateMethod", String.class);
        String result = (String)method.invoke(new MyService(), "Owen");
        String expected = "Owen OK";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testMockPrivateMethod() throws Exception {
        // spy被测试的类并定义需要被mock掉的私有方法的行为
        MyService testService = PowerMockito.spy(new MyService());
        PowerMockito.doReturn("Yes").when(testService, "privateMethod", Mockito.anyString());
        testService.callPrivateMethod();

        // 验证私有方法是否被调用
        PowerMockito.verifyPrivate(testService).invoke("privateMethod", Mockito.anyString());
    }
}