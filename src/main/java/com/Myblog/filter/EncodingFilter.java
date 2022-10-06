package com.Myblog.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 解决发送请求时出现的乱码情况
* 乱码原因：服务器默认的解析编码为ISO-8859-1，不支持中文
* */
@WebFilter("/*")//过滤所有资源
public  class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //基于HTTP
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        //处理post请求,get请求Tomcat7以上不会出现乱码
        request.setCharacterEncoding("UTF-8");

        filterChain.doFilter(request,response);
    }
}
