package com.Myblog.service;

import cn.hutool.core.util.StrUtil;
import com.Myblog.dao.BlogTypeDao;
import com.Myblog.po.BlogType;
import com.Myblog.vo.ResultInfo;

import java.util.List;

public class BlogTypeService {

    private BlogTypeDao typeDao=new BlogTypeDao();
    /*
    * 查询类型列表
    * */
    public List<BlogType> findTypeList (Integer userId){
        List<BlogType> typeList=typeDao.findTypeListByUserId(userId);
        return typeList;
    }

    /*
    * 删除类型
    * */
    public ResultInfo<BlogType> deleteType(String typeId) {
        ResultInfo resultInfo=new ResultInfo();
        // 1.判断参数是否为空
        if(StrUtil.isBlank(typeId)){
            resultInfo.setMsg("系统异常，请重试！");
            resultInfo.setCode(0);
            return resultInfo;
        }

        // 2.调用Dao层，通过类型ID查询博客记录的数量
        long blogCount=typeDao.findBlogCountByTypeId(typeId);
        // 3.如果数量大于0，说明存在子记录，不可删除
        if(blogCount>0){
            resultInfo.setCode(0);
            resultInfo.setMsg("该类型中有数据，不可删除！");
            return resultInfo;
        }

        // 4.如果不存在子记录，调用Dao层的更新方法，通过类型ID删除指定的类型并返回受影响的行数
        int row=typeDao.deleteTypeById(typeId);
        if(row>0){
            resultInfo.setCode(1);
            resultInfo.setMsg("删除成功！");
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("删除失败！");
        }
        return resultInfo;
    }

    /*
    * 添加或修改类型
    * */
    public ResultInfo<Integer> addOrUpdate(String typeName, Integer userId, String typeId) {
        ResultInfo<Integer>resultInfo=new ResultInfo<>();
        // 1.判断参数是否为空
        if(StrUtil.isBlank(typeName)){
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称不能为空！");
            return resultInfo;
        }

        // 2.调用Dao层查询当前用户的类型名称是否唯一（1：可用；0：不可用）
        Integer code=typeDao.checkTypeName(typeName,userId,typeId);
        if(code==0){
            resultInfo.setCode(0);
            resultInfo.setMsg("类型名称已存在，请重新输入！");
            return resultInfo;
        }
        // 3.判断类型ID是否为空
        Integer key=null;
        if(StrUtil.isBlank(typeId)){
            //为空，调用Dao层的添加方法，返回主键
            key=typeDao.addType(typeName,userId);
        }else {
            //不为空，调用Dao层的修改方法，返回受影响的行数
            key=typeDao.updateType(typeName,typeId);
        }

        // 4.判断主键和受影响的行数是否大于0
        if(key>0){
            resultInfo.setCode(1);
            resultInfo.setResult(key);
            resultInfo.setMsg("更新成功！");
        }else {
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败！");
        }
        return resultInfo;
    }
}
