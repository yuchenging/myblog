package com.Myblog.dao;

import com.Myblog.util.DBUtil;
import com.Myblog.vo.ResultInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
* 基础的JDBC操作类：
* 更新操作：（修改、添加、删除操作）
* 查询操作：
* */
public class BaseDao {

    //更新操作
    public  static int executeUpdate(String sql, List<Object> params){
        int row=0;//更新的数据库的行数
        Connection connection=null;
        PreparedStatement preparedStatement=null;

        try {
            //得到数据库连接
            connection=DBUtil.getconnection();
            //预编译
            preparedStatement=connection.prepareStatement(sql);
            //如果有参数，则设置参数
            if(params!=null &&params.size()>0){
                //循环设置参数
                for (int i=0;i<params.size();i++)
                {
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行更新操作
            row=preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(null,preparedStatement,connection);
        }

        return row;
    }

    //该查询只会返回一条记录且只有一个字段，如查询总数量
    public static Object findSingleValue(String sql,List<Object>params){
        Object object=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try {

            //得到数据库连接
            connection=DBUtil.getconnection();
            //预编译
            preparedStatement=connection.prepareStatement(sql);
            //如果有参数，则设置参数
            if(params!=null &&params.size()>0){
                //循环设置参数
                for (int i=0;i<params.size();i++)
                {
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行查询操作，返回结果集
            resultSet=preparedStatement.executeQuery();
            //判断并分析结果集
            if(resultSet.next()){
                object=resultSet.getObject(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }

        return object;
    }

    //查询一个数据集合
    public static  List queryRows(String sql,List<Object>params,Class cls){
        List list=new ArrayList();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            //得到数据库连接
            connection=DBUtil.getconnection();
            //预编译
            preparedStatement=connection.prepareStatement(sql);
            //如果有参数，则设置参数
            if(params!=null &&params.size()>0){
                //循环设置参数
                for(int i=0;i<params.size();i++)
                {
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行查询操作，返回结果集
            resultSet=preparedStatement.executeQuery();
            //得到结果集的元数据对象（包括哪些字段，以及字段的数量）
            ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
            //查询得到的字段数量
            int fieldNum=resultSetMetaData.getColumnCount();

            //判断并分析结果集
            while (resultSet.next()){
                //实例化对象
                Object object=cls.newInstance();
                //遍历查询的字段数量，得到每一个列名
                for (int i=1;i<=fieldNum;i++){
                    String columnName=resultSetMetaData.getColumnName(i);
                    //通过反射，使用列名得到对应的field对象
                    Field field=cls.getDeclaredField(columnName);
                    //拼接set方法，得到字符串
                    String setMethod="set" + columnName.substring(0,1).toUpperCase()+columnName.substring(1);
                    //通过反射，将set方法字符串反射成类中的set方法
                    Method method=cls.getDeclaredMethod(setMethod,field.getType());
                    //得到查询到的每一个字段的值
                    Object value=resultSet.getObject(columnName);
                    //通过invoke()方法调用set()方法
                    method.invoke(object,value);
                }
                //将javabeen设置到集合中
                list.add(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return list;
    }

    //查询一个对象
    public static Object queryRow(String sql,List<Object>params,Class cls){
        List list=queryRows(sql,params,cls);
        Object object =null;
        //如果集合不为空，则获取查询的第一条数据
        if(list!=null&&list.size()>0){
            object=list.get(0);
        }
        return object;
    }
}
