package com.example.prosoft.taxiloy.api.object_response_api;

import com.example.prosoft.taxiloy.ui.objects.CurrentDriver;

/**
 * Created by prosoft on 2/1/16.
 */
public class GetDetailDriverResponse {

    public Data data;

    public class Data {
        public long idcar;
        public int active;
        public String name;
        public String email;
        public String avatar;
        public String gender;
        public String birthday;
        public String phonenumber;
        public String phonecarrier;
        public int addpoint;
        public double addmoney;
        public double cutmoney;
        public double totalmoney;
        public double totalpoint;
        public double totalcutmoney;
        public String driverliscense;
        public String idcard;
        public String carno;
        public String cartag;
        public String carbrand;
        public String carmodel;
        public String carseats;
        public String idcartype;
        public String year;
        public String company;
        public String cheapcar;
        public String tour;
        public String specialevents;
        public String likes;
        public String createddate;
        public ListImageCar[] listimagecar;

        public class ListImageCar {
            public String idimagecar;
            public String idcar;
            public String idimagecartype;
            public String name;
            public String images;
        }
    }
}
