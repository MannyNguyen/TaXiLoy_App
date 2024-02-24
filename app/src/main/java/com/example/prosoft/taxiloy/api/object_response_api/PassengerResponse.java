package com.example.prosoft.taxiloy.api.object_response_api;

/**
 * Created by prosoft on 1/13/16.
 */
public class PassengerResponse {

    public Data data;

    public class Data {
        public int idpassenger;
        public String name;
        public String email;
        public String avatar;
        public String phone;
        public String gender;
        public String birthday;
        public float addpoint;
        public float totalpoint;
        public String createddate;
    }
}
