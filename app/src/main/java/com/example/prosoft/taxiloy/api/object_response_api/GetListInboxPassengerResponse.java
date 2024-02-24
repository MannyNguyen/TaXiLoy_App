package com.example.prosoft.taxiloy.api.object_response_api;

import java.util.ArrayList;

/**
 * Created by prosoft on 2/25/16.
 */
public class GetListInboxPassengerResponse {

    public int totalrecord;
    public int totalpage;
    public int currentpage;
    public int recordperpage;
    public ArrayList<Inbox> data;

    public class Inbox {
        public int idpassenger;
        public String passengername;
        public int idpushlogpassenger;
        public String message;
        public String pushlogpassengercreateddate;
    }


}
