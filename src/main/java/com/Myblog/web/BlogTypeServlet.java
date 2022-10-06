package com.Myblog.web;

import com.Myblog.po.BlogType;
import com.Myblog.po.User;
import com.Myblog.service.BlogTypeService;
import com.Myblog.util.JsonUtil;
import com.Myblog.vo.ResultInfo;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/type")
public class BlogTypeServlet extends HttpServlet {

    private BlogTypeService typeService=new BlogTypeService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置导航的高亮值
        request.setAttribute("menu_page","type");
        //得到用户行为
        String actionName=request.getParameter("actionName");
        //判断用户行为
        if("list".equals(actionName)){
            //查询类型列表
            typeList(request,response);
        }else if("delete".equals(actionName)){
            //删除该类型
            deleteType(request,response);
        }else if("addOrUpdate".equals(actionName)){
            //添加或修改类型
            addOrUpdate(request,response);
        }
    }

    /*
    *  添加或修改类型
    * */
    private void addOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        // 1.接收参数
        String typeName=request.getParameter("typeName");
        String typeId=request.getParameter("typeId");
        // 2.获取Session作用域中的User对象，得到用户ID
        User user= (User) request.getSession().getAttribute("user");
        // 3.调用Service层的更新方法，返回ResultInfo对象
        ResultInfo<Integer>resultInfo=typeService.addOrUpdate(typeName,user.getUserId(),typeId);
        // 4.将ResultInfo对象转化为JSON字符串，响应给ajax回调函数
        JsonUtil.toJson(response,resultInfo);
    }

    /*
    * 删除所选类型
    * */
    private void deleteType(HttpServletRequest request, HttpServletResponse response) {
        // 1.接收typeId,userId参数
        String typeId=request.getParameter("typeId");
        // 2.调用service层的更新操作，返回ResultInfo对象
        ResultInfo<BlogType>resultInfo=typeService.deleteType(typeId);
        // 3.将响应的ResultInfo对象转化为JSON格式的字符串，响应给ajax的回调函数
        JsonUtil.toJson(response,resultInfo);
    }

    /*
    * 查询用户列表
    * */
    private void typeList(HttpServletRequest request, HttpServletResponse response) {
        // 1.获取Session作用域中的user对象
        User user= (User) request.getSession().getAttribute("user");
        // 2.调用Service层的查询方法，查询当前登录用户的类型集合，返回集合
        List<BlogType>typeList=typeService.findTypeList(user.getUserId());
        // 3.将类型列表设置到request请求域中
        request.setAttribute("typeList",typeList);
        // 4.设置首页动态包含的页面值
        request.setAttribute("changePage","type/list.jsp");
        // 5.请求转发跳转到index.jsp页面
        try {
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
