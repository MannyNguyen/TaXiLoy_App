package com.example.prosoft.taxiloy.ui.utils;

/**
 * Created by prosoft on 12/24/15.
 */
public class Config {

    public static final String ANDROID = "Android";
    public static final boolean DEBUG = false;
    public static final String GOOGLE_SENDER_ID = "taxiloy-1155";
    public static final String GOOGLE_SENDER_NUMBER = "475219842129";
    public static final String DISPLAY_MESSAGE_ACTION = "com.androidexample.gcm.DISPLAY_MESSAGE";
    public static final String EXTRA_MESSAGE = "message";

    public static final String BASEURL = "http://apitaxiloy.ctyprosoft.com";
    public static final String API_LOGIN = "/passenger/app";
    public static final String API_CHECK_SMS_CODE = "/passenger/checksmscode";
    public static final String API_CHECK_SMS_CODE_DRIVER = "/car/checksmscode";
    public static final String API_GET_DETAIL_PASSENGER = "/passenger/%s";
    public static final String API_LIST_CAR = "/passenger/cargps";
    public static final String API_DEFAULT_LIST_CAR = "/passenger/filtercar";
    public static final String API_CAR_TYPE = "/cartype/all";
    public static final String API_CAR_BRAND = "/car/filtercarbrand";
    public static final String API_CAR_SEAT = "/car/filtercarseat";
    public static final String API_BOOK_CAR = "/passenger/callcar";
    public static final String API_PASSENGER_REPORT = "/passengerreport/";
    public static final String API_PASSENGER_SUPPORT = "/passengersupport/";
    public static final String API_PASSENGER_CANCEL = "/passenger/canceledcar";

    public static final String API_LOGIN_DIRVER = "/car/app";
    public static final String API_GET_DETAIL_DRIVER = "/car/%s";
    public static final String API_INSERT_LOCATION_DRIVER = "/cargps/";
    public static final String API_INSERT_MATCHLOG = "/car/%s/oncar";
    public static final String API_OFF_CAR = "/car/offcar";

    public static final String API_GET_ALL_INBOX_PASSENGER = "/pushnotificationforpassenger/filterall";
    public static final String API_GET_ALL_INBOX_DRIVER = "/pushnotificationforcar/filterall";
    public static final String API_GET_ALL_HISTORY_DRIVER = "/car/filterhistory";
    public static final String API_GET_ALL_HISTORY_PASSENGER = "/passenger/filterhistory";
    public static final String API_CHECK_VACANT = "/passenger/carvacant";
    public static final String API_RATING_DRIVER = "/rating";
    public static final String API_DETAIL_RATING = "/rating/%s";

    public static final String API_GET_PASSENGER_LOCATION = "/matchlog/%s";
    public static final String API_GET_BANNER_LOCATION = "/imageslidecar/all";



}
