package com.Myblog.web;

import com.Myblog.po.User;
import com.Myblog.service.BlogService;
import com.Myblog.util.JsonUtil;
import com.Myblog.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private BlogService blogService=new BlogService();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置导航栏的高亮值
        request.setAttribute("menu_page","report");
        // 得到用户行为
        String actionName=request.getParameter("actionName");
        // 判断用户行为
        if("info".equals(actionName)){
            reportInfo(request,response);
        }else if("month".equals(actionName)){
            // 通过月份查询博客数量
            queryBlogCountByMonth(request,response);
        }
    }

    /*
    * 通过月份查询博客数量
    * */
    private void queryBlogCountByMonth(HttpServletRequest request, HttpServletResponse response) {
        // 从Session作用域中获取用户对象
        User user= (User) request.getSession().getAttribute("user");
        // 调用service层的方法，返回resultInfo对象
        ResultInfo<Map<String,Object>>resultInfo=blogService.queryBlogCountByMonth(user.getUserId());
        // 将ResultInfo对象转化成JSON格式的字符串，响应给ajax请求
        JsonUtil.toJson(response,resultInfo);
    }

    /*
    * 进入数据报表页面
    * */
    private void reportInfo(HttpServletRequest request, HttpServletResponse response) {
        // 设置首页动态包含的页面值
        request.setAttribute("changePage","report/info.jsp");
        // 请求转发跳转到index.jsp
        try {
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
