package com.example.prosoft.taxiloy.api;

import android.util.Log;

import com.example.prosoft.taxiloy.api.object_request_api.ReportPassengerRequest;
import com.example.prosoft.taxiloy.ui.enums.API_Method;
import com.example.prosoft.taxiloy.ui.utils.Config;

import java.util.Objects;

/**
 * Created by prosoft on 1/11/16.
 */
public class APIConnectionRequest {

    public static void API_Login_Passenger(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_LOGIN);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_LOGIN, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_Login_Driver(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_LOGIN_DIRVER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_LOGIN_DIRVER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_SMS_CODE(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_CHECK_SMS_CODE);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_CHECK_SMS_CODE, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_SMS_CODE_DRIVER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_CHECK_SMS_CODE_DRIVER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_CHECK_SMS_CODE_DRIVER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_DETAIL(CallBackAPI callBackDone, int idPassgenger, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_GET_DETAIL_PASSENGER, idPassgenger));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_GET_DETAIL_PASSENGER, idPassgenger), API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_DEFAULT_LIST_CAR(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_DEFAULT_LIST_CAR);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_DEFAULT_LIST_CAR, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_CAR_TYPE(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_CAR_TYPE);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_CAR_TYPE, API_Method.GET, jsonObject, callBackDone);
    }

    public static void API_GET_CAR_BRAND(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_CAR_BRAND);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_CAR_BRAND, API_Method.GET, jsonObject, callBackDone);
    }

    public static void API_GET_CAR_SEAT(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_CAR_SEAT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_CAR_SEAT, API_Method.GET, jsonObject, callBackDone);
    }

    public static void API_BOOK_CAR(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_BOOK_CAR);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_BOOK_CAR, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_DETAIL_DRIVER(CallBackAPI callBackDone, Object jsonObject, long idcar) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_GET_DETAIL_DRIVER, String.valueOf(idcar)));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_GET_DETAIL_DRIVER, String.valueOf(idcar)), API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_INSERT_LOCATION_DRIVER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_INSERT_LOCATION_DRIVER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_INSERT_LOCATION_DRIVER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_PICK_UP_PASSENGER(CallBackAPI callBackDone, Object jsonObject, int idMatchLog) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_INSERT_MATCHLOG, String.valueOf(idMatchLog)));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_INSERT_MATCHLOG, String.valueOf(idMatchLog)), API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_OFF_CAR(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_OFF_CAR);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_OFF_CAR, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_LIST_INBOX_PASSENGER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_ALL_INBOX_PASSENGER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_ALL_INBOX_PASSENGER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_LIST_INBOX_DRIVER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_ALL_INBOX_DRIVER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_ALL_INBOX_DRIVER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_LIST_HISTORY_DRIVER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_ALL_HISTORY_DRIVER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_ALL_HISTORY_DRIVER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_LIST_HISTORY_PASSENGER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_ALL_HISTORY_PASSENGER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_ALL_HISTORY_PASSENGER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_PUT_UPDATE_PASSENGER(CallBackAPI callBackDone, int idPassgenger, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_GET_DETAIL_PASSENGER, idPassgenger));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_GET_DETAIL_PASSENGER, idPassgenger), API_Method.PUT, jsonObject, callBackDone);
    }

    public static void API_PUT_UPDATE_DRIVER(CallBackAPI callBackDone, long idDriver, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_GET_DETAIL_DRIVER, idDriver));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_GET_DETAIL_DRIVER, idDriver), API_Method.PUT, jsonObject, callBackDone);
    }

    public static void API_GET_LIST_CAR(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_LIST_CAR);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_LIST_CAR, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_REPORT_PASSENGER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_PASSENGER_REPORT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_PASSENGER_REPORT, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_SUPPORT_PASSENGER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_PASSENGER_SUPPORT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_PASSENGER_SUPPORT, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_CANCEL_PASSENGER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_PASSENGER_CANCEL);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_PASSENGER_CANCEL, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_CHECK_VACANT(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_CHECK_VACANT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_CHECK_VACANT, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_RATING_DRIVER(CallBackAPI callBackDone, Object jsonObject) {
        Log.v("TestLog", Config.BASEURL + Config.API_RATING_DRIVER);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_RATING_DRIVER, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_PASSENGER_LOCATION(CallBackAPI callBackDone, Object jsonObject, int idMatchLog) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_GET_PASSENGER_LOCATION, String.valueOf(idMatchLog)));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_GET_PASSENGER_LOCATION, String.valueOf(idMatchLog)), API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_BANNER_LOCATION(CallBackAPI callBackDone, Object jsonObject ) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_BANNER_LOCATION);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_BANNER_LOCATION, API_Method.POST, jsonObject, callBackDone);
    }

    public static void API_GET_DETAIL_RATING(CallBackAPI callBackDone, Object jsonObject, long idCar ) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_DETAIL_RATING, idCar));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_DETAIL_RATING, idCar), API_Method.POST, jsonObject, callBackDone);
    }

}
