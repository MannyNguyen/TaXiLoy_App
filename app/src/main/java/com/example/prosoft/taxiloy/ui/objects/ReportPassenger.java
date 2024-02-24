package com.example.prosoft.taxiloy.ui.objects;

/**
 * Created by Manh on 2/2/2016.
 */
public class ReportPassenger extends BaseModelObject {

    private long idpassenger;
    private String name;
    private String phonenumber;
    private String ridecode;
    private String content;
    private String accesstoken;

    public ReportPassenger(String name, String phonenumber, String ridecode, String content){
        this.name = name;
        this.phonenumber = phonenumber;
        this.ridecode = ridecode;
        this.content = content;
    }

    public long getIdpassenger() {
        return idpassenger;
    }

    public void setIdpassenger(long idpassenger) {
        this.idpassenger = idpassenger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRidecode() {
        return ridecode;
    }

    public void setRidecode(String ridecode) {
        this.ridecode = ridecode;
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
