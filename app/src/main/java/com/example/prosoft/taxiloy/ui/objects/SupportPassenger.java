package com.example.prosoft.taxiloy.ui.objects;

/**
 * Created by Manh on 2/14/2016.
 */
public class SupportPassenger extends BaseModelObject {

    private long idpassenger;
    private String title;
    private String content;
    private String accesstoken;

    public SupportPassenger(String title, String content){
        this.title = title;
        this.content = content;
    }

    public long getIdpassenger() {
        return idpassenger;
    }

    public void setIdpassenger(long idpassenger) {
        this.idpassenger = idpassenger;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
