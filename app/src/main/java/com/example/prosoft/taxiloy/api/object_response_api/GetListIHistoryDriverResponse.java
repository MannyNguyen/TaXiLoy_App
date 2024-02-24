package com.example.prosoft.taxiloy.api.object_response_api;

import java.util.ArrayList;

/**
 * Created by prosoft on 2/25/16.
 */
public class GetListIHistoryDriverResponse {

    public int totalrecord;
    public int totalpage;
    public int currentpage;
    public int recordperpage;
    public ArrayList<History> data;

    public class History {
        public int idpassenger;
        public String passengername;
        public int idcar;
        public String carname;
        public String idmatchlog;
        public String matchlogcalltime;
        public String matchlogontime;
        public String matchlogofftime;

    }
}
