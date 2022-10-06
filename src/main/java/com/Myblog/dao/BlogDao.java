package com.Myblog.dao;

import cn.hutool.core.util.StrUtil;
import com.Myblog.po.Blog;
import com.Myblog.vo.BlogVo;

import java.util.ArrayList;
import java.util.List;

public class BlogDao {
    public int addOrUpdate(Blog blog) {
        // 定义sql语句
        String sql="";
        // 设置参数
        List<Object> params=new ArrayList<>();
        params.add(blog.getTypeId());
        params.add(blog.getTitle());
        params.add(blog.getContent());
        params.add(blog.getUserId());

        // 判断noteId是否为空，如果为空，则为添加操作；如果不为空，则为修改操作
        if(blog.getNoteId()==null){
            sql="insert into blog(typeId,title,content,userId,pubTime)values(?,?,?,?,now())";
        }else {
            sql=" update blog set typeId=?,title=?,content=? where noteId=?";
            params.add(blog.getNoteId());
        }

        //调用BaseDao的更新方法
        int row=BaseDao.executeUpdate(sql,params);
        return row;
    }

    public long findBlogCount(Integer userId,String title,String date,String typeId) {
        // 定义sql语句
        String sql="SELECT COUNT(1) FROM blog,blog_type WHERE blog_type.typeId=blog.typeId and userId=? ";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(userId);
        // 判断条件查询的参数是否为空
        if(!StrUtil.isBlank(title)){/*标题查询*/
            // 拼接sql语句
            sql+=" and title like concat('%',?,'%') ";
            // 设置sql语句所需要的参数
            params.add(title);
        }else if(!StrUtil.isBlank(date)){/*日期查询*/
            // 拼接sql语句
            sql+=" and date_format(pubTime,'%Y年%m月') =? ";
            // 设置sql语句所需要的参数
            params.add(date);
        }else if(!StrUtil.isBlank(typeId)){/*类型查询*/
            // 拼接sql语句
            sql+=" and blog.typeId =? ";
            // 设置sql语句所需要的参数
            params.add(typeId);
        }
        // 调用BaseDao层的方法
        long count= (long) BaseDao.findSingleValue(sql,params);
        return count;
    }

    /*
    *  分页查询博客列表
    * */
    public List<Blog> findBlogListByPage(Integer userId, Integer index, Integer pageSize,String title,String date,String typeId) {
        // 定义sql语句
        String sql="SELECT blog.noteId,blog.title,blog.pubTime " +
                "FROM blog,blog_type " +
                "WHERE blog_type.typeId=blog.typeId and userId=? ";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(userId);
        // 判断条件查询参数是否为空
        if(!StrUtil.isBlank(title)){
            sql+=" and title like concat('%',?,'%') ";
            params.add(title);
        }else if(!StrUtil.isBlank(date)){/*日期查询*/
            // 拼接sql语句
            sql+=" and date_format(pubTime,'%Y年%m月') =? ";
            // 设置sql语句所需要的参数
            params.add(date);
        }else if(!StrUtil.isBlank(typeId)){/*类型查询*/
            // 拼接sql语句
            sql+=" and blog.typeId =? ";
            // 设置sql语句所需要的参数
            params.add(typeId);
        }
        // 拼接分页sql语句(limit语句必须写在sql的最后面）
        sql+=" order by pubTime desc limit ?,?";
        params.add(index);
        params.add(pageSize);
        // 调用BaseDao的查询方法
        List<Blog>blogList=BaseDao.queryRows(sql,params,Blog.class);
        return blogList;
    }

    /*
    * 通过日期分组查询当前用户的博客数量
    * */
    public List<BlogVo> findBlogCountByDate(Integer userId) {
        String sql="SELECT COUNT(1) noteCount,DATE_FORMAT(pubTime,'%Y年%m月')groupName FROM blog b " +
                " WHERE userId=? " +
                " GROUP BY DATE_FORMAT(pubTime,'%Y年%m月') " +
                " ORDER BY DATE_FORMAT(pubTime,'%Y年%m月') DESC";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(userId);

        // 调用Dao层的方法
        List<BlogVo>list=BaseDao.queryRows(sql,params,BlogVo.class);
        return list;
    }

    /*
    * 通过博客类别查询博客记录
    * */
    public List<BlogVo> findBlogCountByType(Integer userId) {
        // 定义sql语句
        String sql="SELECT COUNT(noteId)noteCount,t.typeId,typeName FROM blog n" +
                " RIGHT JOIN blog_type t on n.typeId=t.typeId WHERE userId=? " +
                " GROUP BY t.typeId ORDER BY COUNT(noteId) DESC";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(userId);
        // 调用Dao层的方法
        List<BlogVo>list=BaseDao.queryRows(sql,params,BlogVo.class);
        return list;
    }

    /*
    *  通过Id查询
    * */
    public Blog findBlogById(String noteId) {
        // 定义sql
        String sql="select noteId,title,content,pubTime,typeName,b.typeId from blog b" +
                " inner join blog_type t on b.typeId=t.typeId where noteId=?";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(noteId);
        // 调用BaseDao层的查询方法
        Blog blog= (Blog) BaseDao.queryRow(sql,params,Blog.class);
        return blog;
    }

    /*
    * 删除博客
    * */
    public int deleteNoteById(String noteId) {
        // 定义sql语句
        String sql="delete from blog where noteId=?";
        // 设置参数
        List<Object>params=new ArrayList<>();
        params.add(noteId);
        // 调用Dao层
        int row=BaseDao.executeUpdate(sql,params);
        return row;
    }
}
