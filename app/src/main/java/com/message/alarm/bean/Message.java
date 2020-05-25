package com.message.alarm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class Message {

    //短信id
    @Id
    private long messageId;


    //短信发件人号码
    @NotNull
    private String address;

    //短信内容
    @NotNull
    private String body;

    //收件时间
    @NotNull
    private String date;

    //0表示收 1表示发(本项目不需要展示发信内容)
    @NotNull
    private int type;

    //提醒内容
    private String noticeContent;

    //提醒的时间
    private String noticeTime;

    //是否已经提醒过
    private boolean noticed;

    //是否需要通知 默认不需要
    private boolean needNotice;

    @Generated(hash = 810885782)
    public Message(long messageId, @NotNull String address, @NotNull String body,
            @NotNull String date, int type, String noticeContent, String noticeTime,
            boolean noticed, boolean needNotice) {
        this.messageId = messageId;
        this.address = address;
        this.body = body;
        this.date = date;
        this.type = type;
        this.noticeContent = noticeContent;
        this.noticeTime = noticeTime;
        this.noticed = noticed;
        this.needNotice = needNotice;
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {this.address = address; }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNoticeContent() {
        return this.noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeTime() {
        return this.noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public boolean getNoticed() {
        return this.noticed;
    }

    public void setNoticed(boolean noticed) {
        this.noticed = noticed;
    }

    public boolean getNeedNotice() {
        return this.needNotice;
    }

    public void setNeedNotice(boolean needNotice) {
        this.needNotice = needNotice;
    }



}
