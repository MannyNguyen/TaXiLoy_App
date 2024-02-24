package com.example.prosoft.taxiloy.api.object_response_api;

import com.example.prosoft.taxiloy.ui.objects.Driver;

import java.util.ArrayList;

/**
 * Created by prosoft on 1/26/16.
 */
public class GetDefaultListCarResponse {
    public int page;
    public int totalpage ;
    public ArrayList<Driver> data;

    public class Driver {
        public long idcar;
        public int idcartype;
        public String carname;
        public String carbrand;
        public String carseats;
        public String cartypename;
        public String phonenumber;
        public String phonecarrier;
        public String geox;
        public String geoy;
        public String avatar;
    }
}
