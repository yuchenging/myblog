package com.Myblog.vo;

//封装返回结果的类：
public class ResultInfo<T> {

    private Integer code; //状态码  成功=1，失败=0.
    private String  msg; //提示信息
    private T result;    //返回对象

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getResult() {
        return result;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
