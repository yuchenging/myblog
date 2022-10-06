package com.Myblog.filter;

import com.Myblog.po.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
*  非法访问拦截：
*  无需拦截的资源：
*    1.指定页面（用户无需登录就可访问的页面，如登陆页面）
*    2.静态资源（如存放在statics目录下的js、css、images等资源）
*    3.特定行为（用户无需登录便可执行的操作，如登陆操作）
*    4.登陆状态（判断session作用域中是否存在user对象，如果存在，所有资源均可访问，否则跳转到登陆页面）
*  免登陆功能：
*    通过Cookie和Session对象实现
*    当用户处于未登录状态且请求需要只有登录才能访问的页面时，调用自动登录功能
*    实现：
*    1.通过request.getCookies()获取Cookie数组
*    2.遍历Cookie数组，获取指定的Cookie对象
*    3.得到对应Cookie对象的value值（姓名和密码）
*    4.通过split()方法分割value字符串，得到用户姓名和密码
*    5.请求到登录页面，进行登陆操作
* */
@WebFilter("/*")
public class LoginAccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //基于HTTP
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        //得到访问的路径
        String path=request.getRequestURI();//格式：项目路径/资源路径

        // 1.指定页面（用户无需登录就可访问的页面，如登陆页面）
        if(path.contains("/login.jsp")){
            filterChain.doFilter(request,response);
            return;
        }

        // 2.静态资源（如存放在statics目录下的js、css、images等资源）
        if(path.contains("/statics")){
            filterChain.doFilter(request,response);
            return;
        }

        // 3.特定行为（用户无需登录便可执行的操作，如登陆操作）
        if(path.contains("/user")){
            //得到用户行为
            String actionName=request.getParameter("actionName");
            //判断是否是登陆操作
            if("login".equals(actionName)){
                filterChain.doFilter(request,response);
                return;
            }
        }
        // 4.登陆状态（判断session作用域中是否存在user对象，如果存在，所有资源均可访问，否则跳转到登陆页面）
        //获取Session作用域中的user对象
        User user=(User)request.getSession().getAttribute("user");
        //判断user是否为空
        if(user!=null){
            filterChain.doFilter(request,response);
            return;
        }

        //免登陆操作
            // 1.通过request.getCookies()获取Cookie数组
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0){
            // 2.遍历Cookie数组，获取指定的Cookie对象
            for (Cookie cookie : cookies) {
                if("user".equals(cookie.getName())){
                    // 3.得到对应Cookie对象的value值（姓名和密码）
                    String value=cookie.getValue();
                    // 4.通过split()方法分割value字符串，得到用户姓名和密码
                    String [] val=value.split("-");
                    String userName=val[0];
                    String userPwd=val[1];
                    // 5.请求到登录页面，进行登陆操作
                    String url="user?actionName=login&rem=1&userName="+userName+"&userPwd="+userPwd;
                    request.getRequestDispatcher(url).forward(request,response);
                    return;
                }
            }



        }
        
        //拦截请求，重定向跳转到登陆页面
        response.sendRedirect("login.jsp");
    }

    @Override
    public void destroy() {

    }
}
