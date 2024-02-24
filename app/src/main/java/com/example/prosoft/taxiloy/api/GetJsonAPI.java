package com.example.prosoft.taxiloy.api;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tung on 24/01/2015.
 */
public class GetJsonAPI {

     public static void getQueries(String url,String method, Object jsonObjectToCallAPI,CallBackAPI callBackDone)
     {
         List<Object> listParams = new ArrayList<Object>();
         listParams.add(url);
         listParams.add(method);
         listParams.add(jsonObjectToCallAPI);

         callBackDone.execute(listParams);
     }


}
