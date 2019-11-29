package com.yuwenyun.demos.unit_test.principles;

/**
 * 1. AIR原则
 *   A(automatic): 单测自动化，不需要人为检查验证，禁止使用system.out.println()
 *   I(independent): 测试用例之间保证独立，不能相互调用
 *   R(repeatable): 可重复执行，尽量不对外界有依赖
 *
 * 2. 单测粒度尽量小，尽量方法级别，对调用链较长的方法使用mock
 *
 * 3. 好的单测不止看覆盖率，还要看测试用例是否全面：
 *   B(border): 边界值用例，特殊值，特殊顺序等
 *   C(correct): 正确的测试用例，正常输入并得到预期值
 *   D(design): 结合设计文档编写单测
 *   E(error): 错误的输入是否能得到预期的处理，如抛异常，返回默认值
 *
 * 4. 测试用例方法命名：test + 方法名 + 用例简要说明
 *   public void testInsertEmployee_emptyEmployee()
 *
 * 5.
 *
 * @author lijing
 * @version 1.0
 * @date 2019-06-11 14:25
 */
public class Principles {
}
