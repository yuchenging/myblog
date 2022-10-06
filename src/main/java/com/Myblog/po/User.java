package com.Myblog.po;

public class User {

    private  Integer userId; //用户ID
    private  String uname;   //用户姓名
    private  String upwd;    //用户密码
    private  String nick;    //用户昵称
    private  String head;    //用户头像
    private  String mood;    //个性签名

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUname() {
        return uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public String getNick() {
        return nick;
    }

    public String getHead() {
        return head;
    }

    public String getMood() {
        return mood;
    }

}
