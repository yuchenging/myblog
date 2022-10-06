package com.Myblog.web;

import com.Myblog.po.Blog;
import com.Myblog.po.User;
import com.Myblog.service.BlogService;
import com.Myblog.util.Page;
import com.Myblog.vo.BlogVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置首页导航高亮状态
        request.setAttribute("menu_page","index");
        // 得到用户行为
        String actionName=request.getParameter("actionName");
        // 将用户行为设置到request作用域中
        request.setAttribute("action",actionName);
        // 判断用户行为
        if("searchTitle".equals(actionName)){
            // 得到查询的条件：标题
            String title=request.getParameter("title");
            // 将查询条件设置到request请求域中,在主页设置回显
            request.setAttribute("title",title);
            // 标题搜索
            blogList(request,response,title,null,null);
        }else if("searchDate".equals(actionName)){
            // 日期查询
            String date=request.getParameter("date");
            // 将查询条件设置到request请求域中,在主页设置回显
            request.setAttribute("date",date);
            // 日期搜索
            blogList(request,response,null,date,null);
        }else if("searchType".equals(actionName)){
            // 类别查询
            String typeId=request.getParameter("typeId");
            // 将查询条件设置到request请求域中,在主页设置回显
            request.setAttribute("typeId",typeId);
            // 日期搜索
            blogList(request,response,null,null,typeId);
        }
        else {
            // 不做条件查询，分页查询博客列表
            blogList(request,response,null,null,null);
        }
        //设置首页动态包含的页面
        request.setAttribute("changePage","blog/list.jsp");
        //请求转发到index.jsp
        request.getRequestDispatcher("index.jsp").forward(request,response);
        //分页查询博客列表
        blogList(request,response,null,null,null);
    }

    /*
     * 分页查询博客列表
     * */
    private void blogList(HttpServletRequest request, HttpServletResponse response,String title,String date,String typeId) {
        // 接收参数
        String pageNum=request.getParameter("pageNum");
        String pageSize=request.getParameter("pageSize");
        // 获取Session作用域中的user对象
        User user= (User) request.getSession().getAttribute("user");
        // 调用Service层的方法，返回page对象
        Page<Blog>page=new BlogService().findBlogListByPage(pageNum,pageSize,user.getUserId(),title,date,typeId);
        // 将page对象设置到request作用域中
        request.setAttribute("page",page);

        // 通过日期分组查询当前用户的博客数量
        List<BlogVo>dateInfo=new BlogService().findBlogCountByDate(user.getUserId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("dateInfo",dateInfo);

        // 通过类型分组查询当前用户的博客数量
        List<BlogVo>typeInfo=new BlogService().findBlogCountByType(user.getUserId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("typeInfo",typeInfo);
    }
}
