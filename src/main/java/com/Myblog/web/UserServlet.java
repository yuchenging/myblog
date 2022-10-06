package com.Myblog.web;

import cn.hutool.core.io.FileUtil;
import com.Myblog.po.User;
import com.Myblog.service.UserService;
import com.Myblog.vo.ResultInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/user")
@MultipartConfig
public class UserServlet extends HttpServlet {

    private UserService userService=new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置首页导航高亮状态
        request.setAttribute("menu_page","user");
        //接收用户行为
        String actionName=request.getParameter("actionName");
        //判断用户行为
        if("login".equals(actionName)){
            //用户登录
            userLogin(request,response);
        }else if ("logout".equals(actionName)){
            //用户退出
            userLogOut(request,response);
        }else if ("userCenter".equals(actionName)){
            //进入个人中心
            userCenter(request,response);
        }else if("userHead".equals(actionName)){
            //加载头像
            userHead(request,response);
        }else if("checkNick".equals(actionName)){
            //验证昵称的唯一性
            checkNick(request,response);
        }else if("updateUser".equals(actionName)){
            //修改用户信息
            updateUser(request,response);
        }

    }

    /*
    *  修改用户信息
    * */
    private void updateUser(HttpServletRequest request, HttpServletResponse response) {
        // 1.调用Service层的方法，传递request对象作为参数，返回resultInfo对象
        ResultInfo<User>resultInfo=userService.updateUser(request);
        // 2.将resultInfo对象存到request作用域中
        request.setAttribute("resultInfo",resultInfo);
        // 3.请求转发跳转到个人中心页面
        try {
            request.getRequestDispatcher("user?actionName=userCenter").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 验证用户昵称的唯一性
    * */
    private void checkNick(HttpServletRequest request, HttpServletResponse response) {
        // 1.获取昵称
        String nick=request.getParameter("nick");
        // 2.从session作用域中获取用户对象，得到用户id
        User user=(User)request.getSession().getAttribute("user");
        // 3.调用Service层方法，得到返回结果
        Integer code=userService.checkNick(nick,user.getUserId());
        // 4.通过字节输出流将结果响应给前端的Ajax请求
        try {
            response.getWriter().write(code+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 5.关闭资源
        try {
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 加载用户头像
    * */
    private void userHead(HttpServletRequest request, HttpServletResponse response) {
        Logger logger= LoggerFactory.getLogger(UserServlet.class);
        // 1.获取参数
        String head=request.getParameter("imageName");
        // 2.得到图片存放的路径
        String realPath="D:\\java IDEA专业版\\java项目\\博客管理系统1\\src\\main\\webapp\\WEB-INF\\upload";
        logger.info("真实路径："+realPath);
        // 3.通过图片的完整路径得到file对象
        File file=new File(realPath+"/"+head);
        // 4.通过截取，得到图片后缀
        String pic=head.substring(head.lastIndexOf(".")+1);
        // 5.通过不同的图片后缀，设置不同的响应类型
        if("PNG".equalsIgnoreCase(pic)){
            response.setContentType("image/png");
        }else if("JPG".equalsIgnoreCase(pic)||"JPEG".equalsIgnoreCase(pic)){
            response.setContentType("image/jpeg");
        }else if("GIF".equalsIgnoreCase(pic)){
            response.setContentType("image/gif");
        }

        //利用FileUtils的copyFile()方法，将图片拷贝给浏览器
        try {
            FileUtils.copyFile(file,response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 个人中心
    * */
    private void userCenter(HttpServletRequest request, HttpServletResponse response) {
        //设置首页动态包含的页面值
        request.setAttribute("changePage","user/info.jsp");
        //请求转发跳转到index
        try {
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 用户退出
    * */
    private void userLogOut(HttpServletRequest request, HttpServletResponse response) {
        // 1.删除Session对象
        request.getSession().invalidate();
        // 2.删除Cookie对象
        Cookie cookie=new Cookie("user",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        // 3.重定向跳转到登陆页面
        try {
            response.sendRedirect("login.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 用户登录
    * */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) {
        // 1.获取用户名和密码
        String userName=request.getParameter("userName");
        String userPwd=request.getParameter("userPwd");
        // 2.调用Service层的方法，返回ResultInfo对象
        ResultInfo<User> resultInfo= userService.userlogin(userName,userPwd);
        // 3.判断是否登陆成功
        if(resultInfo.getCode()==1){
            //将用户信息设置到session作用域中
            request.getSession().setAttribute("user",resultInfo.getResult());
            //判断用户是否选择记住密码
            String rem=request.getParameter("rem");
            if("1".equals(rem)){
                //得到Cookie对象
                Cookie cookie=new Cookie("user",userName+"-"+userPwd);
                //设置失效时间
                cookie.setMaxAge(3*24*60*60);
                //响应给客户端
                response.addCookie(cookie);
            }else {
                //如果不记住密码，清空所有的cookie对象
                Cookie cookie=new Cookie("user",null);
                //删除cookie，设置maxage为0
                cookie.setMaxAge(0);
                //响应给客户端
                response.addCookie(cookie);
            }

            //登录成功，跳转到首页
            try {
                response.sendRedirect("index");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            //将resultInfo对象设置到request作用域中
            request.setAttribute("resultInfo",resultInfo);
            //跳转到登录页面
            try {
                request.getRequestDispatcher("login.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
