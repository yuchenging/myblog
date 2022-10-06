package com.Myblog.dao;

import cn.hutool.crypto.digest.DigestUtil;
import com.Myblog.po.User;
import com.Myblog.util.DBUtil;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    /*
    * 通过用户名查询用户对象
    * */
    public  User queryUserByName(String userName){
        User user=null;
        //定义sql语句
        String sql="select * from user where uname=?";
        //设置参数集合
        List<Object> params= new ArrayList<>();
        params.add(userName);
        //调用BaseDao的查询方法
        user= (User) BaseDao.queryRow(sql,params,User.class);
        return user;
    }
    public  User queryUserByName1(String userName){
        User user=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try {
            //获取数据库连接
            connection=DBUtil.getconnection();
            //定义sql查询
            String sql="select * from user where uname=?";
            //预编译
            preparedStatement=connection.prepareStatement(sql);
            //设置参数
            preparedStatement.setString(1,userName);
            //执行查询
            resultSet=preparedStatement.executeQuery();
            //判断及分析结果
            if(resultSet.next()){
                user=new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(userName);
                user.setHead(resultSet.getString("head"));
                user.setMood(resultSet.getString("mood"));
                user.setNick(resultSet.getString("nick"));
                user.setUpwd(resultSet.getString("upwd"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return user;
    }

    /*
    * 通过用户ID和昵称查询用户对象
    * */
    public User queryUserByNickAndUserId(String nick, Integer userId) {
        // 1.定义SQL语句
        String sql="select * from user where nick=?and userId !=?";
        // 2.设置参数集合
        List<Object>params=new ArrayList<>();
        params.add(nick);
        params.add(userId);
        // 3.调用BaseDao的查询方法
        User user= (User) BaseDao.queryRow(sql,params,User.class);
        return user;
    }

    /*
    *  更新用户信息
    * */
    public int updateUser(User user) {
        // 1.定义SQL语句
        String sql="update user set nick=?,mood=?,head=? where userId=?";
        // 2.设置参数集合
        List<Object>params=new ArrayList<>();
        params.add(user.getNick());
        params.add(user.getMood());
        params.add(user.getHead());
        params.add(user.getUserId());
        // 3.调用BaseDao层方法，返回受影响的行数
        int row=BaseDao.executeUpdate(sql,params);
        return row;
    }

    /*
    * 用户注册
    * */
    public int registUser(String uname, String upwd, String nick, String mood) {
        // 对用户密码进行md5加密
        String pwd= DigestUtil.md5Hex(upwd);
        // 定义sql语句
        String sql="insert into user(uname,upwd,nick,mood) values(?,?,?,?)";
        // 设置参数集合
        List<Object>params=new ArrayList<>();
        params.add(uname);
        params.add(pwd);
        params.add(nick);
        params.add(mood);
        // 调用BaseDao层的更新方法
        int row=BaseDao.executeUpdate(sql,params);
        return row;
    }
}
