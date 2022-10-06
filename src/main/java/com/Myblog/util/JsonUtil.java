package com.Myblog.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtil {

    /*
    * 将Object对象转化为JSON格式
    * */
    public static void toJson(HttpServletResponse response,Object result){
        //   设置响应类型及编码格式
        response.setContentType("application/json;charset=UTF-8");
        //   得到字符输出流
        PrintWriter out= null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   通过fastjson的方法，将ResultInfo对象转化为JSON格式的字符串
        String json= JSON.toJSONString(result);
        //   通过输出流输出JSON格式的字符串
        out.write(json);
        //   关闭资源
        out.close();
    }
}
