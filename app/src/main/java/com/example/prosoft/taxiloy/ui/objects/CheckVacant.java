package com.example.prosoft.taxiloy.ui.objects;

/**
 * Created by Manh on 3/1/2016.
 */
public class CheckVacant extends BaseModelObject{
    private long idcar;
    private String accesstoken;

    public long getIdcar() {
        return idcar;
    }

    public void setIdcar(long idcar) {
        this.idcar = idcar;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
