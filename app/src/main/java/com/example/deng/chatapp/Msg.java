package com.example.deng.chatapp;

public class Msg {
    public static final int TYEP_RECEIVED = 0;

    public static final int TYEP_SENT = 1;

    private String content;

    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
