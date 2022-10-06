package com.Myblog;

import cn.hutool.json.JSONUtil;
import com.Myblog.dao.UserDao;
import com.Myblog.po.User;
import com.Myblog.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;

public class TestUser {

    //使用日志工厂，记录日志
    private Logger logger= LoggerFactory.getLogger(TestUser.class);
    @Test
    public void testQueryUserByName(){
        UserDao userDao=new UserDao();
        User user=userDao.queryUserByName("admin");
        //使用日志
        logger.info("该用户的密码为： "+user.getUpwd());
    }
}
