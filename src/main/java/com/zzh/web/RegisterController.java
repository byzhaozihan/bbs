package com.zzh.web;

import com.zzh.domain.User;
import com.zzh.exception.UserExistException;
import com.zzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户注册
 */
@Controller
public class RegisterController extends BaseController{

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(HttpServletRequest request, User user) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/success");
        try {
            userService.register(user);
        } catch (UserExistException e) {
            view.addObject("errorMsg", "用户名已经存在，请选择其它的名字。");
            view.setViewName("forward:/register.jsp");
        }
        setSessionUser(request,user);
        return view;
    }
}
