package com.Myblog.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
*  分页工具类
* */
@Getter
@Setter
public class Page<T> {
    private Integer pageNum;//当前页
    private Integer pageSize;//每页显示的数量
    private long totalCount;//总记录数

    private Integer totalPages;//总页数
    private Integer prePage;//上一页
    private Integer nextPage;//下一页

    private Integer startNavPage;//导航开始页
    private Integer endNavPage;//导航结束页

    private List<T> dateList;//当前页的数据集合

    /*
    * 通过指定参数的值，得到其他分页参数的值
    * */
    public Page(Integer pageNum, Integer pageSize, long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

        // 总页数
        this.totalPages=(int)Math.ceil(totalCount/(pageSize*1.0));
        // 上一页
        this.prePage=pageNum-1<1?1:pageNum-1;
        // 下一页
        this.nextPage=pageNum+1>totalPages?totalPages:prePage+1;
        //导航开始页
        this.startNavPage=pageNum-5;
        this.endNavPage=pageNum+4;
        if(this.startNavPage<1){
            // 如果当前页-5小于1，则导航开始页为1
            this.startNavPage=1;
            // 此时导航结束页为导航开始数+9
            this.endNavPage=this.startNavPage+9>totalPages?totalPages:this.startNavPage+9;
        }
        // 导航结束页
        if(this.endNavPage>totalPages){
            // 如果当前页+4大于总页数，则导航结束页为总页数
            this.endNavPage=totalPages;
            // 此时导航开始页为导航页-9；如果导航页-9小于1，则导航开始页为1
            this.startNavPage=this.endNavPage-9 < 1 ? 1:this.endNavPage-9;
        }
    }
}
