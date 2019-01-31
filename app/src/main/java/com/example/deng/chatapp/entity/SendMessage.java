package com.example.deng.chatapp.entity;

public class SendMessage {
    private int reqType;

    private Preception preception;

    private UserInfo userInfo;

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public Preception getPreception() {
        return preception;
    }

    public void setPreception(Preception preception) {
        this.preception = preception;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
