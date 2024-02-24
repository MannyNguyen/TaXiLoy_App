package com.example.prosoft.taxiloy.api.object_response_api;

import com.example.prosoft.taxiloy.ui.objects.MessageInbox;

import java.util.ArrayList;

/**
 * Created by prosoft on 2/25/16.
 */
public class GetListInboxDriverResponse {
    public int totalrecord;
    public int totalpage;
    public int currentpage;
    public int recordperpage;
    public ArrayList<Inbox> data;

    public class Inbox {
        public int idcar;
        public String carname;
        public int idpushlogcar;
        public String message;
        public String pushlogcarcreateddate;
    }
}
