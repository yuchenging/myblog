package com.Myblog.web;

import cn.hutool.core.util.StrUtil;
import com.Myblog.dao.UserDao;
import com.Myblog.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/regist")
public class RegistServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 用户注册
        regist(request,response);
    }

    /*
    * 用户注册
    * */
    private ResultInfo<Object> regist(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<Object>resultInfo=new ResultInfo<>();
        // 接收参数
        String uname=request.getParameter("uname");
        String upwd=request.getParameter("upwd");
        String nick=request.getParameter("nick");
        String mood=request.getParameter("mood");
        // 必填参数的校验
        if(StrUtil.isBlank(uname)){
            resultInfo.setCode(0);
            resultInfo.setMsg("用户名称不能为空！");
            return resultInfo;
        }else if(StrUtil.isBlank(upwd)){
            resultInfo.setCode(0);
            resultInfo.setMsg("用户密码不能为空！");
            return resultInfo;
        }
        // 调用UserDao的更新方法
        UserDao userDao=new UserDao();
        int row=userDao.registUser(uname,upwd,nick,mood);
        if(row>0){
            resultInfo.setCode(1);
            resultInfo.setMsg("注册成功！");
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("注册失败！");
        }
        return resultInfo;
    }
}
