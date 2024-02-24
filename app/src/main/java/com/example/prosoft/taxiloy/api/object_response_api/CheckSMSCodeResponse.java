package com.example.prosoft.taxiloy.api.object_response_api;

/**
 * Created by prosoft on 1/12/16.
 */
public class CheckSMSCodeResponse {

    public Data data;

    public class Data{
        public int idpassenger;
        public String accesstoken;
        public String phone;
    }
}
