package com.Myblog.web;

import cn.hutool.core.util.StrUtil;
import com.Myblog.po.Blog;
import com.Myblog.po.BlogType;
import com.Myblog.po.User;
import com.Myblog.service.BlogService;
import com.Myblog.service.BlogTypeService;
import com.Myblog.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    private BlogService blogService=new BlogService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置导航栏的高亮效果
        request.setAttribute("menu_page","blog");
        // 得到用户行为
        String actionName=request.getParameter("actionName");
        // 判断用户行为
        if("view".equals(actionName)){
            // 进入发布云记页面
            blogView(request,response);
        }else if("addOrUpdate".equals(actionName)){
            // 修改或添加博客
            addOrUpdate(request,response);
        }else if("detail".equals(actionName)){
            // 博客详情
            blogDetail(request,response);
        }else if("delete".equals(actionName)){
            // 删除博客
            blogDelete(request,response);
        }
    }

    /*
    * 删除博客
    * */
    private void blogDelete(HttpServletRequest request, HttpServletResponse response) {
        // 接收参数
        String noteId=request.getParameter("noteId");
        // 调用Service层的删除方法，返回状态码
        Integer code=blogService.deleteBlog(noteId);
        // 通过流将结果响应给ajax回调函数
        try {
            response.getWriter().write(code +"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 查询博客详情
    * */
    private void blogDetail(HttpServletRequest request, HttpServletResponse response) {
        // 接收参数
        String noteId=request.getParameter("noteId");
        // 调用Service层的查询方法，返回note对象
        Blog blog=blogService.findBlogById(noteId);
        // 将blog对象设置到request请求域中
        request.setAttribute("blog",blog);
        // 设置首页包含的页面值
        request.setAttribute("changePage","blog/detail.jsp");
        try {
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*添加或修改博客*/
    private void addOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        // 接收参数
        String typeId=request.getParameter("typeId");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        User user= (User) request.getSession().getAttribute("user");
        int userId=user.getUserId();
        // 如果是修改操作，需要接受noteId
        String noteId=request.getParameter("noteId");
        // 调用Service层的方法，返回resultInfo对象
        ResultInfo<Blog>resultInfo=blogService.addOrUpdate(typeId,title,content,noteId,userId);
        // 判断ResultInfo中的Code值
        if(resultInfo.getCode()==1){
            // 重定向跳转到首页
            try {
                response.sendRedirect("index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            // 将resultInfo对象设置到request作用域中
            request.setAttribute("resultInfo",resultInfo);
            String url="blog?actionName=view";
            // 如果是修改操作，需要传递noteId
            if(!StrUtil.isBlank(noteId)){
                url+="&noteId="+noteId;
            }
            // 请求跳转到原页面
            try {
                request.getRequestDispatcher(url).forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void blogView(HttpServletRequest request, HttpServletResponse response) {
        /*
        * 修改博客操作
        * */
        // 得到要修改的博客的Id
        String noteId=request.getParameter("noteId");
        // 通过noteId查询博客对象
        Blog blog=blogService.findBlogById(noteId);
        // 将blog对象设置到request请求域中
        request.setAttribute("blogInfo",blog);
        /*
        * 修改博客操作完结
        * */
        // 从Session对象中获取用户对象
        User user= (User) request.getSession().getAttribute("user");
        // 通过用户ID查询对应的类型列表
        List<BlogType>typeList=new BlogTypeService().findTypeList(user.getUserId());
        // 将类型设置到request请求域中
        request.setAttribute("typeList",typeList);
        // 设置首页动态包含的页面值
        request.setAttribute("changePage","blog/view.jsp");
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
