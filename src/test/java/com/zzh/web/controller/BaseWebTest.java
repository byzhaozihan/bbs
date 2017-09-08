package com.zzh.web.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.BeforeClass;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * WEB层的测试
 * TestNG+Unitils+Spring Mock组合
 * 运用Spring Mock模拟依赖容器的接口实例
 * 如HttpServletRequest
 * 完成Web层中控制器的独立性测试。
 */
@SpringApplicationContext({"applicationContext.xml","bbs-servlet.xml"})
public class BaseWebTest extends UnitilsTestNG {

    //声明模拟对象
    public MockHttpServletRequest request;
    public MockHttpServletResponse response;
    public MockHttpSession session;

    //执行测试前先初始模拟对象
    @BeforeClass
    public void before() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        request.setCharacterEncoding("UTF-8");
    }
}
