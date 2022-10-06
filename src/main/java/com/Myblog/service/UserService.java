package com.Myblog.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.Myblog.dao.UserDao;
import com.Myblog.po.User;
import com.Myblog.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class UserService {

    private UserDao userDao=new UserDao();

    /*
    * 用户登录
    * */
    public ResultInfo<User> userlogin(String userName,String userPwd){
        ResultInfo<User> resultInfo=new ResultInfo<>();

        //数据回显：当实现登录时，将登陆信息返回给页面显示
        User u=new User();
        u.setUname(userName);
        u.setUpwd(userPwd);
        resultInfo.setResult(u);
        // 1.判断参数是否为空
        if(StrUtil.isBlank(userName)||StrUtil.isBlank(userPwd)){
            //如果为空，返回状态码和提示信息
            resultInfo.setCode(0);
            resultInfo.setMsg("用户名或密码不能为空！");
            return resultInfo;
        }

        // 2.如果不为空，查询用户对象是否存在
        User user=userDao.queryUserByName(userName);
        // 3.判断用户对象是否为空
        if(user==null){
            //如果为空，设置ResultInfo对象的状态码和信息
            resultInfo.setCode(0);
            resultInfo.setMsg("该用户不存在！");
            return resultInfo;
        }

        // 4.如果用户存在，判断用户密码是否正确（密码是经过MD5加密的）
        userPwd= DigestUtil.md5Hex(userPwd);
        if(!userPwd.equals(user.getUpwd())){
            //如果用户密码不正确
            resultInfo.setCode(0);
            resultInfo.setMsg("用户密码错误！");
            return resultInfo;
        }

        //如果用户名和密码都正确
        resultInfo.setCode(1);
        resultInfo.setResult(user);
        return resultInfo;
    }

    /*
    * 验证用户昵称的唯一性
    * */
    public Integer checkNick(String nick, Integer userId) {
        // 1.判断昵称是否为空
        if(StrUtil.isBlank(nick)){
            return 0;
        }

        // 2.调用Dao层，通过用户ID和昵称查询用户对象
        User user=userDao.queryUserByNickAndUserId(nick,userId);

        //判断用户对象是否存在
        if(user!=null){
            return 0;
        }
        return 1;
    }

    /*
    *  修改用户信息
    * */
    public ResultInfo<User> updateUser(HttpServletRequest request) {
        ResultInfo<User> resultInfo=new ResultInfo<>();
        // 1.获取参数信息
        String nick=request.getParameter("nick");
        String mood=request.getParameter("mood");

        // 2.必填参数nick()的校验
        if(StrUtil.isBlank(nick)){
            resultInfo.setCode(0);
            resultInfo.setMsg("用户昵称不能为空！");
            return resultInfo;
        }

        // 3.从session作用域中获取用户对象（获取用户信息中的默认用户头像）
        User user= (User) request.getSession().getAttribute("user");
        //设置修改的昵称和头像
        user.setNick(nick);
        user.setMood(mood);

        // 4.实现文件上传
        try{
            // 1.获取part对象 request.getPart("name")
            Part part=request.getPart("img");
            // 2.通过part对象获取上传的文件名（从头部信息中获取文件名）
            String header=part.getHeader("Content-Disposition");
            //   获取具体的请求头对应的值
            String str=header.substring(header.lastIndexOf("=")+2);
            // 3.获取上传的文件名
            String fileName=str.substring(0,str.length()-1);
            if(!StrUtil.isBlank(fileName)){
                //如果用户上传了头像，则更新用户头像
                user.setHead(fileName);
                //获取文件存放的路径
                String filePath="D:\\java IDEA专业版\\java项目\\博客管理系统1\\src\\main\\webapp\\WEB-INF\\upload";
                //上传文件到指定目录
                part.write(filePath+"/"+fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // 4.调用Dao层的更新方法，返回受影响的行数
        int row=userDao.updateUser(user);
        if(row>0){
            resultInfo.setCode(1);
            //更新session中的对象
            request.getSession().setAttribute("user",user);
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败！");
        }
        return resultInfo;
    }
}
