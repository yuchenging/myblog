package com.Myblog.service;

import cn.hutool.core.util.StrUtil;
import com.Myblog.dao.BlogDao;
import com.Myblog.dao.BlogTypeDao;
import com.Myblog.po.Blog;
import com.Myblog.util.Page;
import com.Myblog.vo.BlogVo;
import com.Myblog.vo.ResultInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogService {

    private BlogDao blogDao=new BlogDao();

    /*
    * 添加或修改博客
    * */
    public ResultInfo<Blog> addOrUpdate(String typeId, String title, String content,String noteId,int userId) {
        ResultInfo<Blog>resultInfo=new ResultInfo<>();
        // 参数的非空判断
        if(StrUtil.isBlank(typeId)){
            resultInfo.setCode(0);
            resultInfo.setMsg("请选择博客类型！");
            return resultInfo;
        }

        if(StrUtil.isBlank(title)){
            resultInfo.setCode(0);
            resultInfo.setMsg("博客标题不能为空！");
            return resultInfo;
        }

        if(StrUtil.isBlank(content)){
            resultInfo.setCode(0);
            resultInfo.setMsg("博客内容不能为空！");
            return resultInfo;
        }


        //设置Blog对象
        Blog blog=new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setTypeId(Integer.valueOf(typeId));
        blog.setUserId(userId);
        // 判断博客Id是否为空
        if(!StrUtil.isBlank(noteId)){
            blog.setNoteId(Integer.valueOf(noteId));
        }
        resultInfo.setResult(blog);

        // 调用Dao层，添加博客记录
        int row=blogDao.addOrUpdate(blog);
        if(row>0){
            resultInfo.setCode(1);
        }else {
            resultInfo.setCode(0);
            resultInfo.setResult(blog);
        }

        return resultInfo;
    }

    /*
    *  分页查询博客列表
    * */
    public Page<Blog> findBlogListByPage(String pageNumStr, String pageSizeStr, Integer userId,String title,String date,String typeId) {
        // 设置分页的默认参数
        Integer pageSize=5;//默认每页显示10条数据
        Integer pageNum=1;//默认总共只有一页
        // 参数的校验
        if(!StrUtil.isBlank(pageNumStr)){
            // 设置当前页
            pageNum=Integer.parseInt(pageNumStr);
        }
        if(!StrUtil.isBlank(pageSizeStr)){
            // 设置每页显示的数量
            pageSize=Integer.parseInt(pageSizeStr);
        }

        // 查询当前登录用户的博客数量
        long count=blogDao.findBlogCount(userId,title,date,typeId);
        if(count<1){
            return null;
        }
        // 如果总记录数大于0，调用Page类的带参构造，得到其他分页参数的值，返回Page对象
        Page<Blog>page=new Page<>(pageNum,pageSize,count);
        // 得到数据库中分页查询的开始下标
        Integer index=(pageNum-1)*pageSize;
        // 查询当前用户该页的数据列表，返回blog集合
        List<Blog>blogList=blogDao.findBlogListByPage(userId,index,pageSize,title,date,typeId);
        // 将blog集合设置到page对象中
        page.setDateList(blogList);
        return page;
    }

    /*
    *  通过日期分组查询当前用户的博客数量
    * */
    public List<BlogVo> findBlogCountByDate(Integer userId) {
        return blogDao.findBlogCountByDate(userId);
    }

    /*
     *  通过类型分组查询当前用户的博客数量
     * */
    public List<BlogVo> findBlogCountByType(Integer userId) {
        return blogDao.findBlogCountByType(userId);
    }

    /*
    * 查询博客详情
    * */
    public Blog findBlogById(String noteId) {
        // 参数的非空判断
        if(StrUtil.isBlank(noteId)){
            return null;
        }
        // 调用Dao层的查询方法
        Blog blog=blogDao.findBlogById(noteId);
        // 返回blog对象
        return blog;
    }

    /*
    * 删除博客
    * */
    public Integer deleteBlog(String noteId) {
        // 判断参数
        if(StrUtil.isBlank(noteId)){
            return 0;
        }
        // 调用Dao层的方法
        int row=blogDao.deleteNoteById(noteId);
        if(row>0){
            return 1;
        }
        return 0;
    }

    /*
    * 通过月份查询博客数量
    * */
    public ResultInfo<Map<String, Object>> queryBlogCountByMonth(Integer userId) {
        ResultInfo<Map<String, Object>> resultInfo=new ResultInfo<>();
        // 通过月份查询博客数量
        List<BlogVo>blogVos=blogDao.findBlogCountByDate(userId);

        //判断集合是否存在
        if(blogVos!=null&&blogVos.size()>0){
            // 得到月份
            List<String> monthList=new ArrayList<>();
            // 得到博客集合
            List<Integer>blogCountList=new ArrayList<>();
            // 遍历月份集合
            for (BlogVo blogVo : blogVos) {
                monthList.add(blogVo.getGroupName());
                blogCountList.add((int)blogVo.getNoteCount());
            }

            // 准备Map对象，封装月份和博客数量
            Map<String,Object>map=new HashMap<>();
            map.put("monthArray",monthList);
            map.put("dataArray",blogCountList);
            // 将map对象设置到ResultInfo对象中
            resultInfo.setCode(1);
            resultInfo.setResult(map);
        }
        return resultInfo;
    }
}
