package com.example.prosoft.taxiloy.api.object_response_api;

/**
 * Created by prosoft on 1/25/16.
 */
public class CheckSMSCodeDriverResponse {

    public Data data;

    public class Data{
        public int idcar;
        public String accesstoken;
        public String phonenumber;
        public int active;
    }
}
