package com.yuwenyun.demos.unit_test.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 1. mock框架中的基本概念
 *   Unit: 测试单元，如被测试的方法（任意修饰符修饰）
 *   Collaborator: 测试单元中调用的第三方接口
 *   Mock: 虚假的Collaborator, 被mock的Collaborator所有的方法在测试运行时都不会真正调用，而是返回配置的指定值
 *   Spy: 虚假的Collaborator, 被spy的Collaborator所有的方法在测试运行时都会真正调用，除非配置了指定值
 *
 * 2. 验证的两种类型：验证行为和验证状态
 *
 * case
 * 3. 如何保证测试方法的执行顺序
 * 4. 想mock掉局部变量，对时间LocalDateTime.now()的处理
 * 5. 对exception的处理
 * 6. 对rpc调用的处理
 * 7. 对数据库测试的处理
 * 8. 对线程池测试的处理
 * 9. 好的单测还应该看case的数量，而不只是代码覆盖率
 *
 * @author lijing
 * @version 1.0
 * @date 2019-06-05 19:27
 */
@RunWith(MockitoJUnitRunner.class)
public class PublicMethodTest {

    @Mock
    private MySubService mockService; // collaborator
    @InjectMocks
    private MyService testService; // unit

    /**
     * test_void
     */
    @Test
    public void testCallSubServiceInMethod(){
        // 定义mock对象的某个行为时，在when()方法内部调用行为方法
        Mockito.when(mockService.getName()).thenReturn("Hello");

        // 执行测试对象的方法
        testService.callSubServiceInMethod();

        // 验证调用之后mock对象是否执行了某个行为，在verify()方法外调用行为方法
        Mockito.verify(mockService, Mockito.times(1)).getName();
    }

    /**
     * mock_void
     */
    @Test
    public void testCallSubServiceInMethodWithArgs(){
        // mock掉返回值为void的方法时，不定义具体返回值，行为方法在when()外面调用
        // 此处的when()与Mockito.when()为不同的方法，此处是陈述行为，Mockito.when()是定义行为
        Mockito.doNothing().when(mockService).setName(Mockito.anyString());
        testService.callSubServiceInMethodWithArgs();
        Mockito.verify(mockService).setName(Mockito.anyString());
    }

    /**
     * 同一mock对象多次调用返回不同结果
     */
    @Test
    public void testCallSubServiceMultiTimes(){
        Mockito.when(mockService.getName()).thenReturn("Hello").thenReturn("Word");
        testService.callSubServiceMultiTimes();
        Mockito.verify(mockService, Mockito.times(2)).getName();
    }

    @Test
    public void testCallSubServiceInMethod_PartlyMock(){
        // @Mock会mock掉所有方法，不需要显示创建实例
        // @Spy不会mock任何方法，需要显示创建实例
        MySubService subService = new MySubService();
        subService.setName("Hello");
        MySubService spyService = Mockito.spy(subService);
        testService.setService(spyService);

        // 定义spy行为
        Mockito.when(spyService.getName()).thenReturn("HELLO");

        // 执行测试对象的方法
        testService.callSubServiceInMethodWithArgs();

        Mockito.verify(spyService).getName();
    }
}
