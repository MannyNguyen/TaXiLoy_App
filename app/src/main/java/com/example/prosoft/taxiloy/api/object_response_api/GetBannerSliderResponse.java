package com.example.prosoft.taxiloy.api.object_response_api;

import java.util.ArrayList;

/**
 * Created by prosoft on 3/8/16.
 */
public class GetBannerSliderResponse {

    public ArrayList<Banner> data;

    public class Banner {
        public int idimageslidecar;
        public String titleslide;
        public String subtitle;
        public String desc;
        public String imagesslide;
        public String createddate;
        public String accesstoken;
    }
}
