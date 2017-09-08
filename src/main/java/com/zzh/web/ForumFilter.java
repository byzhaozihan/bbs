package com.zzh.web;

import com.zzh.domain.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.zzh.cons.CommonConstant.*;

/**
 * 每个Controller都有可能涉及登陆验证处理逻辑
 * 如论坛中只有登陆用户才能发表新话题
 */
public class ForumFilter implements Filter {

    //过滤标识
    public static final String FILTERED_REQUEST = "@@session_context_filtered_request";

    //不需要登录即可访问的URL资源
    public static final String[] INHERENT_ESCAPE_URIS = {
            "/index.jsp","/index.html","/login.jsp","/login/doLogin.html",
            "/register.jsp","/register.html","/board/listBoardTopics-",
            "/board/listTopicPosts-"
    };

    protected User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(USER_CONTEXT);
    }

    /**
     * 当前URI是否需要登录才能访问
     */
    private boolean isURILogin(String requestURI, HttpServletRequest request) {

        //getContextPath()也就是: /bbs
        if (request.getContextPath().equalsIgnoreCase(requestURI)
                || (request.getContextPath() + "/").equalsIgnoreCase(requestURI)) {
            //进入根目录不需要登录
            return true;//不需要登录
        }
        for (String uri : INHERENT_ESCAPE_URIS) {
            //indexOf返回某个指定的字符串值在字符串中首次出现的位置
            //未找到则返回-1
            if (requestURI != null && requestURI.indexOf(uri) >= 0) {
                return true;//不需要登录
            }
        }
        return false;//需要登录
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //保证过滤器在一次请求中只被调用一次
        if (servletRequest != null && servletRequest.getAttribute(FILTERED_REQUEST) != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //设置过滤标识，防止一次请求多次过滤
            servletRequest.setAttribute(FILTERED_REQUEST, Boolean.TRUE);

            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            User userContext = getSessionUser(httpServletRequest);

            //用户未登录，且 当前URI需要登录才能访问
            //getRequestURI：/bbs/.....
            //getRequestURL：http://localhost:8080/bbs/.....
            if (userContext == null
                    && !isURILogin(httpServletRequest.getRequestURI(), httpServletRequest)) {
                //需要登录
                String toUrl = httpServletRequest.getRequestURL().toString();
                if (!StringUtils.isEmpty(httpServletRequest.getQueryString())) {
                    //getQueryString方法获取uri“？”后的语句
                    toUrl += "?" + httpServletRequest.getQueryString();
                }
                //将用户请求的URL保存在Session中，用于登录成功之后，跳到目标URL
                httpServletRequest.getSession().setAttribute(LOGIN_TO_URL,toUrl);

                //转发到登录页面
                servletRequest.getRequestDispatcher("/login.jsp").forward(servletRequest, servletResponse);
                return;
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
