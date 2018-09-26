package com.zhenjie.smartidiot.entity;

public class ChatListData {

    private int type;
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatListData{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}
