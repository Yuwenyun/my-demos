package com.yuwenyun.demos.unit_test.basic;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;

import org.junit.Assert;
import org.junit.Test;

/**
 * Assert: 断言，验证
 *   assertTrue(); assertEquals(); assertNull;
 * Matcher: 匹配器，匹配运行结果
 *   any(), is(), allOf(), anyOf(), equalTo(), sameInstance(), fail()
 *
 * @author lijing
 * @version 1.0
 * @date 2019-06-06 11:49
 */
public class JUnitTest {

    @Test
    public void testGetCounter(){
        MyService service = new MyService();
        int result = service.getCounter();
        Assert.assertTrue(result == 1);
        Assert.assertEquals(1, result);
        Assert.assertThat(result, is(1));
    }

    /**
     * 不使用Assert断言去验证/捕捉异常
     */
    @Test(expected = RuntimeException.class)
    public void testThrowException_1st(){
        MyService service = new MyService();
        service.throwException();
    }

    @Test
    public void testThrowException_2nd(){
        MyService service = new MyService();
        try {
            service.throwException();
            fail("expected exception");
        }catch (Exception e){
            // ignore
        }
    }
}
