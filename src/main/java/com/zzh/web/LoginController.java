package com.zzh.web;

import com.zzh.cons.CommonConstant;
import com.zzh.domain.User;
import com.zzh.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户登录与注销
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     */
    @RequestMapping("/doLogin")
    public ModelAndView login(HttpServletRequest request, User user) {
        //dbUser:数据库中名字匹配的User
        User dbUser = userService.getUserByUserName(user.getUserName());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("forward:/login.jsp");
        if (dbUser == null) {
            mav.addObject("errorMsg", "用户名不存在");
        } else if (!dbUser.getPassword().equals(user.getPassword())) {
            mav.addObject("errorMsg", "密码不正确");
        } else if (dbUser.getLocked() == User.USER_LOCK) {
            mav.addObject("errorMsg", "用户已被锁定，不能登陆");
        } else {
            //成功登录
            dbUser.setLastIp(request.getRemoteAddr());
            dbUser.setLastVisit(new Date());
            //添加当前用户积分并保存登录日志
            userService.loginSuccess(dbUser);
            //保存用户对象到session中USER_CONTEXT
            setSessionUser(request,dbUser);
            //LOGIN_TO_URL:用户请求的URL
            String toUrl = (String) request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
            //如果当前会话中没有保存登录之前的请求URL，则直接跳转到主页
            if (StringUtils.isEmpty(toUrl)) {
                toUrl = "/index.html";
            }
            mav.setViewName("redirect:"+toUrl);
        }
        return mav;
    }

    /**
     * 登录注销
     */
    @RequestMapping("/doLogout")
    public String logout(HttpSession session) {
        session.removeAttribute(CommonConstant.USER_CONTEXT);
        return "forward:/index.jsp";
    }
}
