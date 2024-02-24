package com.example.prosoft.taxiloy.api.object_response_api;

/**
 * Created by prosoft on 1/12/16.
 */
public class LoginResponse {


    public Data data;

    public class Data{
        public int idpassenger;
        public String phone;
        public String smscode;
        public String check;
    }

}
