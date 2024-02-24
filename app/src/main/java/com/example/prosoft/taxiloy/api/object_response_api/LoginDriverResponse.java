package com.example.prosoft.taxiloy.api.object_response_api;

/**
 * Created by prosoft on 1/18/16.
 */
public class LoginDriverResponse {

    public Data data;

    public class Data {
        public long idcar;
        public String phonenumber;
        public int active;
    }
}
