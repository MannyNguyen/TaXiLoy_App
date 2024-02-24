package com.example.prosoft.taxiloy.ui.objects;

import android.text.method.DateTimeKeyListener;

import com.example.prosoft.taxiloy.ui.activity.BaseActivity;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Manh on 1/18/2016.
 */
public class MessageInbox {

    private String title;
    private String message;
    private String time;

    public MessageInbox() {

    }

    public MessageInbox(String title, String message, String time) {
        this.title = title;
        this.message = message;
        this.time = time;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
