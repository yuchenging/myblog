package com.Myblog.dao;

import cn.hutool.core.util.StrUtil;
import com.Myblog.po.BlogType;
import com.Myblog.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlogTypeDao {
    /*
    *  通过用户ID查询类型集合
    * */
    public List<BlogType>findTypeListByUserId(Integer userId){
        //定义sql语句
        String sql="select distinct t.typeId,typeName from blog_type t,blog b " +
                "where t.typeId=b.typeId and userId=? order by t.typeId";
        //设置参数列表
        List<Object>params=new ArrayList<>();
        params.add(userId);
        //调用BaseDao的查询方法，返回集合
        List<BlogType> list=BaseDao.queryRows(sql,params,BlogType.class);

        return list;
    }

    public int deleteTypeById(String typeId) {
        //1.定义sql语句
        String sql="delete from blog_type where typeId=?";
        //2.设置参数集合
        List<Object>params=new ArrayList<>();
        params.add(typeId);
        //3.调用BaseDao层的方法
        int row=BaseDao.executeUpdate(sql,params);
        return row;
    }

    /*
    * 通过typeId查询博客数量
    * */
    public long findBlogCountByTypeId(String typeId) {
        //1.定义sql语句
        String sql="select count(*) from blog where typeId=?";
        //2.设置参数集合
        List<Object>params=new ArrayList<>();
        params.add(typeId);
        //3.调用BaseDao层的方法
        long count= (long) BaseDao.findSingleValue(sql,params);
        return count;
    }

    /*
    * 查询当前用户的类型是否唯一
    * */
    public Integer checkTypeName(String typeName, Integer userId, String typeId) {
        // 定义SQL语句
        String sql="select * from blog_type t,blog b " +
                "where t.typeId=b.typeId and userId=? and typeName=?";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(userId);
        params.add(typeName);
        // 执行查询操作
        BlogType blogType= (BlogType) BaseDao.queryRow(sql,params,BlogType.class);
        // 如果对象为空，表示可用
        if(blogType==null){
            return 1;
        }else {
            // 如果是修改操作，则需判断当前记录本身
            if(typeId.equals(blogType.getTypeId().toString())){
                return 1;
            }
        }
        return 0;
    }

    /*
    * 添加方法
    * */
    public Integer addType(String typeName, Integer userId) {
        Integer key=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try {
            // 得到数据库连接
            connection=DBUtil.getconnection();
            // 定义sql语句
            String sql="insert into blog_type(typeName) values(?)";
            // 预编译
            preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // 数值参数
            preparedStatement.setString(1,typeName);
            // 执行更新，返回受影响的行数
            int row=preparedStatement.executeUpdate();
            if(row>0){
                // 获取返回主键的结果集
                resultSet=preparedStatement.getGeneratedKeys();
                // 得到主键的值
                if(resultSet.next()){
                    key=resultSet.getInt(1);
                }
            }
            /*
            * 进行blog表的更新
            * */
            // 查询刚插入的类型Id
            String sql_queryNewTypeId="select max(typeId) from blog_type";
            int typeId= (int) BaseDao.findSingleValue(sql_queryNewTypeId,null);
            // 定义sql语句
            String sql_blog="insert into blog(typeId,userId) values(?,?)";
            // 设置参数
            List<Object>params=new ArrayList<>();
            params.add(typeId);
            params.add(userId);
            // 调用BaseDao的方法：
            int row_blog=BaseDao.executeUpdate(sql_blog,params);
            if(row_blog>0){
                return key;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return key;
    }

    /*
    * 修改方法
    * */
    public Integer updateType(String typeName, String typeId) {
        // 定义SQL语句
        String sql="update blog_type set typeName=? where typeId= ?";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(typeName);
        params.add(typeId);
        // 调用BaseDao的更新方法
        int row=BaseDao.executeUpdate(sql,params);
        return row;
    }
}
