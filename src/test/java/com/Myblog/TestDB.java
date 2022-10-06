package com.Myblog;

import com.Myblog.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDB {
    //使用日志工厂，记录日志
    private Logger logger= LoggerFactory.getLogger(TestDB.class);

    //单元测试方法：返回值一般为空，参数一般为空，每个方法都能独立运行，得用@test注解
    @Test
    public void testDB(){
        System.out.println(DBUtil.getconnection());
        //使用日志
        logger.info("获取数据库连接 "+DBUtil.getconnection());
    }


}
