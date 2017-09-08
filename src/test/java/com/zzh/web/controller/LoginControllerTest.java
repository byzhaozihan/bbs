package com.zzh.web.controller;

import com.zzh.cons.CommonConstant;
import com.zzh.domain.User;
import com.zzh.web.LoginController;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by zhao on 2017/8/27.
 */
public class LoginControllerTest extends BaseWebTest {

    @SpringBeanByType
    private LoginController controller;

    @Test
    public void loginCheckByMock() throws Exception {
        request.setRequestURI("/login/doLogin.html");
        User user = new User();
        user.setUserName("test");
        user.setPassword("1234");

        ModelAndView mav = controller.login(request, user);
        User userBack = (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);

        assertNotNull(mav);
        assertNotNull(userBack);
        assertEquals(userBack.getUserName(), "test");
        assertEquals(userBack.getCredit() > 5, true);

    }

}
