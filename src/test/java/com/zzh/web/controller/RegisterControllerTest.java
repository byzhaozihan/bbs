package com.zzh.web.controller;

import com.zzh.domain.User;
import com.zzh.web.RegisterController;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.testng.Assert.assertNotNull;

/**
 * Created by zhao on 2017/8/28.
 */
public class RegisterControllerTest extends BaseWebTest{

    @SpringBeanByType
    private RegisterController controller;

    @Test
    public void register() throws Exception {
        request.setRequestURI("/register.html");
        request.setMethod("POST");
        User user = new User();
        user.setUserName("test2");
        user.setPassword("1234");
        user.setLocked(0);

        ModelAndView mav = controller.register(request, user);
        assertNotNull(mav);
    }
}
