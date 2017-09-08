package com.zzh.web;

import com.zzh.cons.CommonConstant;
import com.zzh.domain.User;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhao on 2017/8/22.
 */
public class BaseController {

    protected static final String ERROR_MSG_KEY = "errorMsg";

    /**
     * protected:只能被本包或者子类使用
     * 获取保存在Session中的用户对象
     */
    protected User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
    }

    /**
     * 保存用户对象到Session中
     */
    protected void setSessionUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
    }

    /**
     * 获取基于应用程序的url绝对路径
     */
    public final String getAppbaseUrl(HttpServletRequest request, String url) {
        Assert.hasLength(url,"url不能为空");
        Assert.isTrue(url.startsWith("/"),"必须以/打头");
        return request.getContextPath()+url;
    }

}