package com.example.prosoft.taxiloy.ui;

import android.content.Context;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Message;
import android.util.Log;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.api.APIConnectionRequest;
import com.example.prosoft.taxiloy.api.CallBackAPI;
import com.example.prosoft.taxiloy.api.object_request_api.BookCarRequest;
import com.example.prosoft.taxiloy.api.object_request_api.CheckSMSCodeDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.CheckSMSCodeRequest;
import com.example.prosoft.taxiloy.api.object_request_api.CheckVacantRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetBannerSliderRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetDefaultListCarRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetDetailDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetDetailPassengerRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetFilterListCarByBrandRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetFilterListCarBySeatsRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetFilterListCarByTypeRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetListCarRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetListHistoryDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetListHistoryPassengerRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetListInboxDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetListInboxPassengerRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetPassengerLocationRequest;
import com.example.prosoft.taxiloy.api.object_request_api.GetRatingDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.InsertLocationDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.InsertRatingDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.LoginDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.LoginRequest;
import com.example.prosoft.taxiloy.api.object_request_api.OffCarRequest;
import com.example.prosoft.taxiloy.api.object_request_api.OnCarRequest;
import com.example.prosoft.taxiloy.api.object_request_api.PassengerCancelRequest;
import com.example.prosoft.taxiloy.api.object_request_api.ReportPassengerRequest;
import com.example.prosoft.taxiloy.api.object_request_api.SupportPassengerRequest;
import com.example.prosoft.taxiloy.api.object_request_api.UpdateDriverRequest;
import com.example.prosoft.taxiloy.api.object_request_api.UpdatePassengerRequest;
import com.example.prosoft.taxiloy.api.object_response_api.CheckSMSCodeDriverResponse;
import com.example.prosoft.taxiloy.api.object_response_api.CheckSMSCodeResponse;
import com.example.prosoft.taxiloy.api.object_response_api.CheckVacantDriverResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetBannerSliderResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetDefaultListCarResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetDetailDriverResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetDriverPassengerResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetListCarResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetListHistoryPassengerResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetListInboxDriverResponse;
import com.example.prosoft.taxiloy.api.object_response_api.GetListInboxPassengerResponse;
import com.example.prosoft.taxiloy.api.object_response_api.InsertRatingDriverResponse;
import com.example.prosoft.taxiloy.api.object_response_api.LoginDriverResponse;
import com.example.prosoft.taxiloy.api.object_response_api.LoginResponse;
import com.example.prosoft.taxiloy.api.object_response_api.PassengerResponse;
import com.example.prosoft.taxiloy.api.object_response_api.PickUpPassengerReponse;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.objects.CarType;
import com.example.prosoft.taxiloy.ui.objects.CurrentDriver;
import com.example.prosoft.taxiloy.ui.objects.CurrentPassenger;
import com.example.prosoft.taxiloy.ui.objects.DetailRating;
import com.example.prosoft.taxiloy.ui.objects.Driver;
import com.example.prosoft.taxiloy.ui.objects.HistoryList;
import com.example.prosoft.taxiloy.ui.objects.MessageInbox;
import com.example.prosoft.taxiloy.ui.objects.ModelError;
import com.example.prosoft.taxiloy.ui.objects.ModelObject;
import com.example.prosoft.taxiloy.ui.objects.PassengerCanceled;
import com.example.prosoft.taxiloy.ui.objects.ReportPassenger;
import com.example.prosoft.taxiloy.ui.objects.SupportPassenger;
import com.example.prosoft.taxiloy.ui.utils.Config;
import com.example.prosoft.taxiloy.ui.utils.TaxiLoyDebug;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

/**
 * Created by prosoft on 1/11/16.
 */

public class Model extends Observable implements Observer {

    private static Model _instance;
    private String smsCode = null;
    private String accessToken = null;
    public static Driver mDriver;
    private static Location passengerLocation;
    private static CurrentPassenger currentPassenger;
    private static CurrentDriver currentDriver;
    private static ArrayList<MessageInbox> messageInboxArrayList;
    private static ArrayList<HistoryList> messageHistoryArrayList;
    private static ReportPassenger reportPassenger;
    private static ArrayList<Driver> driverArrayList;
    private static ArrayList<Driver> driverDefaultList;
    private static ArrayList<Driver> driverFilterList;
    private static ArrayList<CarType> carTypes;
    private static ArrayList<String> carBrands;
    private static ArrayList<String> carSeats;
    private static ArrayList<GetBannerSliderResponse.Banner> banners;
    private static boolean inApp = false;
    public static boolean isDriverVacant = true;
    public static int typePush;
    public static int totalItem;
    public static MediaPlayer mediaPlayer;
    public int statusOfPassenger = 1;
    private static DetailRating detailRating;

    //Tao truong hop/doi tuong "Model" duy nhat dung cho toan bo app
    public static Model getInstance() {
        if (_instance == null) {
            _instance = new Model();
        }
        return _instance;
    }

    public static boolean isDriverVacant() {
        return isDriverVacant;
    }

    public static void setIsDriverVacant(boolean isDriverVacant) {
        Model.isDriverVacant = isDriverVacant;
    }

    //Tao truong hop/doi tuong "CurrentPassenger" duy nhat dung cho toan bo app
    public CurrentPassenger getCurrentPassenger() {
        if (currentPassenger == null) {
            currentPassenger = new CurrentPassenger();
        }
        return currentPassenger;
    }

    public CurrentDriver getCurrentDriver() {
        if (currentDriver == null) {
            currentDriver = new CurrentDriver();
        }
        return currentDriver;
    }

//    public DetailRating getDetailRating(){
//        if (detailRating == null){
//            detailRating = new DetailRating();
//        }
//        return detailRating;
//    }

    //use to shake phone and book car
    public ArrayList<Driver> getDefaultCars() {

        if (driverDefaultList == null) {
            driverDefaultList = new ArrayList<Driver>();
        }
        return driverDefaultList;
    }

    public ArrayList<MessageInbox> getMessageInbox() {
        if (messageInboxArrayList == null) {
            messageInboxArrayList = new ArrayList<MessageInbox>();
        }
        return messageInboxArrayList;
    }

    public ArrayList<HistoryList> getMessageHistory() {
        if (messageHistoryArrayList == null) {
            messageHistoryArrayList = new ArrayList<HistoryList>();
        }
        return messageHistoryArrayList;
    }

    public ArrayList<Driver> getDriverArrayList() {

        if (driverArrayList == null) {
            driverArrayList = new ArrayList<Driver>();
        }
        return driverArrayList;
    }

    public ArrayList<Driver> getDriverFilterList() {

        if (driverFilterList == null) {
            driverFilterList = new ArrayList<Driver>();
        }
        return driverFilterList;
    }

    public ArrayList<CarType> getCarTypes() {

        if (carTypes == null) {
            carTypes = new ArrayList<CarType>();
        }
        return carTypes;
    }

    public ArrayList<String> getCarBrands() {

        if (carBrands == null) {
            carBrands = new ArrayList<String>();
        }
        return carBrands;
    }

    public ArrayList<String> getCarSeats() {

        if (carSeats == null) {
            carSeats = new ArrayList<String>();
        }
        return carSeats;
    }

    public ArrayList<GetBannerSliderResponse.Banner> getBanners() {

        if (banners == null) {
            banners = new ArrayList<GetBannerSliderResponse.Banner>();
        }
        return banners;
    }

    //Load media, play horntone when passenger book a ride.
    public MediaPlayer getMediaPlayer(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.air_horntone);
        }
        return mediaPlayer;
    }

    public Location getPassengerLocation() {
        if (passengerLocation == null) {
            passengerLocation = new Location("");
        }
        return passengerLocation;
    }


    //use to show notification, check user in app or not, this isInApp for return inApp
    public boolean isInApp() {
        return inApp;
    }

    //use to show notification, this setInApp to set data for inApp
    public void setInApp(boolean inApp) {
        this.inApp = inApp;
    }

    //Goi API passenger, tao 1 truong hop
    public void callAPILoginPasserger(CurrentPassenger passenger, String deviceID, String deviceToken) {

        //Response
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    LoginResponse response = gson.fromJson(result.toString(), LoginResponse.class);
                    //get idpassenger from json to setIdPassenger.
                    getCurrentPassenger().setIdPassenger(response.data.idpassenger);
                    //get phone from json to setPhone
                    getCurrentPassenger().setPhone(response.data.phone);
                    if (result.has("check")) {
                        notifyObserversOfEvent(ModelEvent.GETSMS_ALREADY);
                    } else {
                        notifyObserversOfEvent(ModelEvent.GETSMS_SUCCESS);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.avatar = "";
        loginRequest.name = passenger.getName();
        loginRequest.email = "";
        loginRequest.phone = passenger.getPhone();
        loginRequest.gender = passenger.getGender();
        loginRequest.system = Config.ANDROID;
        loginRequest.deviceid = deviceID;
        loginRequest.devicetoken = deviceToken;

        //Run callAPILoginPassenger
        APIConnectionRequest.API_Login_Passenger(callBackDone, loginRequest);

    }

    public void callAPILoginDriver(CurrentPassenger passenger, String deviceID, String deviceToken) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    LoginDriverResponse response = gson.fromJson(result.toString(), LoginDriverResponse.class);
                    getCurrentDriver().setPhonenumber(response.data.phonenumber);
                    getCurrentDriver().setIdcar(response.data.idcar);
                    getCurrentDriver().setActive(response.data.active);
                    if (response.data.active == 0) {
                        notifyObserversOfEvent(ModelEvent.GETSMS_SUCCESS);
                    } else {
                        notifyObserversOfEvent(ModelEvent.GETSMS_ALREADY);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        LoginDriverRequest loginRequest = new LoginDriverRequest();
        loginRequest.idcar = "1";
        loginRequest.avatar = "";
        loginRequest.name = passenger.getName();
        loginRequest.email = "";
        loginRequest.phonenumber = passenger.getPhone();
        loginRequest.gender = passenger.getGender();
        loginRequest.system = Config.ANDROID;
        loginRequest.deviceid = deviceID;
        loginRequest.devicetoken = deviceToken;

        APIConnectionRequest.API_Login_Driver(callBackDone, loginRequest);

    }


    public void checkSMSCode(String smsCode) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        JSONObject data = result.getJSONObject("data");
                        if (data.has("result")) {
                            notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_FAILED);
                            return;
                        }
                    } catch (Exception ex) {

                    }

                    Gson gson = new Gson();
                    CheckSMSCodeResponse response = gson.fromJson(result.toString(), CheckSMSCodeResponse.class);
                    //accesstoken to access data
                    setAccessToken(response.data.accesstoken);
                    notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        CheckSMSCodeRequest checkSMSCodeRequest = new CheckSMSCodeRequest();
        checkSMSCodeRequest.idpassenger = getCurrentPassenger().getIdPassenger();
        checkSMSCodeRequest.smscode = smsCode;
        checkSMSCodeRequest.phone = getCurrentPassenger().getPhone();


        APIConnectionRequest.API_SMS_CODE(callBackDone, checkSMSCodeRequest);

    }

    public void checkSMSCodeDriver(String smsCode) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    if (result.has("result")) {
                        notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_FAILED);
                        return;
                    }

                    Gson gson = new Gson();
                    CheckSMSCodeDriverResponse response = gson.fromJson(result.toString(), CheckSMSCodeDriverResponse.class);
                    setAccessToken(response.data.accesstoken);
                    notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        CheckSMSCodeDriverRequest checkSMSCodeRequest = new CheckSMSCodeDriverRequest();
        checkSMSCodeRequest.idcar = getCurrentDriver().getIdcar();
        checkSMSCodeRequest.smscode = smsCode;
        checkSMSCodeRequest.phonenumber = getCurrentDriver().getPhonenumber();

        APIConnectionRequest.API_SMS_CODE_DRIVER(callBackDone, checkSMSCodeRequest);
    }

    public void getDetailPassenger(int idPassenger, String accessToken) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {

                    Gson gson = new Gson();
                    PassengerResponse response = gson.fromJson(result.toString(), PassengerResponse.class);

                    getCurrentPassenger().setIdPassenger(response.data.idpassenger);
                    getCurrentPassenger().setPhone(response.data.phone);
                    getCurrentPassenger().setName(response.data.name);
                    getCurrentPassenger().setGender(response.data.gender);
                    getCurrentPassenger().setCreatedDate(response.data.createddate);
                    getCurrentPassenger().setAvatar(response.data.avatar);
                    getCurrentPassenger().setBirthday(response.data.birthday);
                    getCurrentPassenger().setEmail(response.data.email);
                    getCurrentPassenger().setPoint(response.data.totalpoint);
                    notifyObserversOfEvent(ModelEvent.GET_DETAIL_PASSENGER_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        GetDetailPassengerRequest getDetailPassengerRequest = new GetDetailPassengerRequest();
        getDetailPassengerRequest.accesstoken = accessToken;
        getDetailPassengerRequest.idpassenger = idPassenger;

        APIConnectionRequest.API_GET_DETAIL(callBackDone, idPassenger, getDetailPassengerRequest);

    }

    public void getDefaultListCar(final int page, String accessToken, Location location) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {

                    Gson gson = new Gson();
                    GetDefaultListCarResponse response = gson.fromJson(result.toString(), GetDefaultListCarResponse.class);
                    if (page == 1) {
                        getDefaultCars().clear();
                    }
                    for (GetDefaultListCarResponse.Driver driver : response.data) {
                        Driver temp = new Driver();
                        temp.idcar = driver.idcar;
                        temp.name = driver.carname;
                        temp.carbrand = driver.carbrand;
                        temp.carseats = driver.carseats;
                        temp.cartypename = driver.cartypename;
                        temp.phonecarrier = driver.phonecarrier;
                        temp.phonenumber = driver.phonenumber;
                        temp.geox = driver.geox;
                        temp.geoy = driver.geoy;
                        temp.avatar = driver.avatar;
                        getDefaultCars().add(temp);
                    }
                    notifyObserversOfEvent(ModelEvent.GET_DEFAULT_LIST_CAR_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        GetDefaultListCarRequest getDetailRequest = new GetDefaultListCarRequest();
        getDetailRequest.accesstoken = accessToken;
        getDetailRequest.page = page;
        getDetailRequest.geox = String.valueOf(location.getLatitude());
        getDetailRequest.geoy = String.valueOf(location.getLongitude());

        APIConnectionRequest.API_GET_DEFAULT_LIST_CAR(callBackDone, getDetailRequest);

    }


    public void getAllCarType() {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        JSONArray data = result.getJSONArray("data");
                        getCarTypes().clear();
                        for (short i = 0; i < data.length(); i++) {
                            JSONObject temp = data.getJSONObject(i);
                            getCarTypes().add(new CarType(temp.getInt("idcartype"),
                                    temp.getString("cartypename")));
                        }
                        notifyObserversOfEvent(ModelEvent.GET_CARTYPES_SUCCESS);
                    } catch (JSONException ex) {
                        Log.e("JSONException", ex + "");
                    }

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        APIConnectionRequest.API_GET_CAR_TYPE(callBackDone, null);
    }

    public void getAllCarBrand() {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {

                    try {
                        JSONObject data = result.getJSONObject("data");
                        JSONArray listCarBrand = data.getJSONArray("listcarbrand");
                        if (listCarBrand != null) {
                            getCarBrands().clear();
                            for (int i = 0; i < listCarBrand.length(); i++) {
                                getCarBrands().add(listCarBrand.getString(i));
                            }
                        }
                        notifyObserversOfEvent(ModelEvent.GET_CARBRAND_SUCCESS);
                    } catch (JSONException ex) {
                        Log.e("JSONException", ex + "");
                    }

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        APIConnectionRequest.API_GET_CAR_BRAND(callBackDone, null);
    }

    public void getAllCarSeats() {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {

                    try {
                        JSONObject data = result.getJSONObject("data");
                        JSONArray listCarSeats = data.getJSONArray("listcarseat");
                        if (listCarSeats != null) {
                            getCarSeats().clear();
                            for (int i = 0; i < listCarSeats.length(); i++) {
                                getCarSeats().add(listCarSeats.getString(i));
                            }
                            notifyObserversOfEvent(ModelEvent.GET_CARSEATS_SUCCESS);
                        }
                    } catch (JSONException ex) {
                        Log.e("JSONException", ex + "");
                    }

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        APIConnectionRequest.API_GET_CAR_SEAT(callBackDone, null);
    }

    public void getListFilterCar(int tab, final int page, Location location, String accessToken, int pos) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    GetDefaultListCarResponse response = gson.fromJson(result.toString(), GetDefaultListCarResponse.class);
                    if (page == 1) {
                        getDefaultCars().clear();
                    }
                    for (GetDefaultListCarResponse.Driver driver : response.data) {
                        Driver temp = new Driver();
                        temp.idcar = driver.idcar;
                        temp.name = driver.carname;
                        temp.carbrand = driver.carbrand;
                        temp.carseats = driver.carseats;
                        temp.cartypename = driver.cartypename;
                        temp.phonecarrier = driver.phonecarrier;
                        temp.phonenumber = driver.phonenumber;
                        temp.geox = driver.geox;
                        temp.geoy = driver.geoy;
                        getDefaultCars().add(temp);
                    }
                    notifyObserversOfEvent(ModelEvent.GET_FILTER_LIST_CAR_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        switch (tab) {
            case 1:
                GetFilterListCarByTypeRequest carByTypeRequest = new GetFilterListCarByTypeRequest();
                carByTypeRequest.accesstoken = accessToken;
                carByTypeRequest.idcartype = getCarTypes().get(pos).idcartype;
                carByTypeRequest.geox = String.valueOf(location.getLatitude());
                carByTypeRequest.geoy = String.valueOf(location.getLongitude());
                carByTypeRequest.page = page;
                APIConnectionRequest.API_GET_DEFAULT_LIST_CAR(callBackDone, carByTypeRequest);
                break;
            case 2:
                GetFilterListCarByBrandRequest carByBrandRequest = new GetFilterListCarByBrandRequest();
                carByBrandRequest.accesstoken = accessToken;
                carByBrandRequest.carbrand = getCarBrands().get(pos);
                carByBrandRequest.geox = String.valueOf(location.getLatitude());
                carByBrandRequest.geoy = String.valueOf(location.getLongitude());
                carByBrandRequest.page = page;
                APIConnectionRequest.API_GET_DEFAULT_LIST_CAR(callBackDone, carByBrandRequest);
                break;
            case 3:
                GetFilterListCarBySeatsRequest carBySeatsRequest = new GetFilterListCarBySeatsRequest();
                carBySeatsRequest.accesstoken = accessToken;
                carBySeatsRequest.carseats = getCarSeats().get(pos);
                carBySeatsRequest.geox = String.valueOf(location.getLatitude());
                carBySeatsRequest.geoy = String.valueOf(location.getLongitude());
                carBySeatsRequest.page = page;
                APIConnectionRequest.API_GET_DEFAULT_LIST_CAR(callBackDone, carBySeatsRequest);
                break;
        }

    }

    // no need
    public void checkVacantDriver() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {

                    getCurrentPassenger().setIdPassenger(getCurrentPassenger().getIdPassenger());
                    getCurrentPassenger().setPhone(currentPassenger.getPhone());
                    getCurrentPassenger().setName(currentPassenger.getName());
                    getCurrentPassenger().setGender(currentPassenger.getGender());
                    if (currentPassenger.getAvatar() != null) {
                        getCurrentPassenger().setAvatar(currentPassenger.getAvatar());
                    }
                    getCurrentPassenger().setBirthday(currentPassenger.getBirthday());
                    getCurrentPassenger().setEmail(currentPassenger.getEmail());
                    notifyObserversOfEvent(ModelEvent.PUT_DETAIL_PASSENGER_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        UpdatePassengerRequest updatePassengerRequest = new UpdatePassengerRequest();

        APIConnectionRequest.API_PUT_UPDATE_PASSENGER(callBackDone, getCurrentPassenger().getIdPassenger(), updatePassengerRequest);
    }

    public void putUpdateDriver(String accessToken, final CurrentDriver currentDriver, String deviceID) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {

                    getCurrentPassenger().setIdPassenger(getCurrentPassenger().getIdPassenger());
                    getCurrentPassenger().setPhone(currentPassenger.getPhone());
                    getCurrentPassenger().setName(currentPassenger.getName());
                    getCurrentPassenger().setGender(currentPassenger.getGender());
                    if (currentPassenger.getAvatar() != null) {
                        getCurrentPassenger().setAvatar(currentPassenger.getAvatar());
                    }
                    getCurrentPassenger().setBirthday(currentPassenger.getBirthday());
                    getCurrentPassenger().setEmail(currentPassenger.getEmail());
                    notifyObserversOfEvent(ModelEvent.PUT_DETAIL_PASSENGER_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        UpdateDriverRequest updatePassengerRequest = new UpdateDriverRequest();
        updatePassengerRequest.idcar = getCurrentDriver().getIdcar();
        updatePassengerRequest.accesstoken = accessToken;
        updatePassengerRequest.name = currentDriver.getName();
        updatePassengerRequest.email = currentDriver.getEmail();
        updatePassengerRequest.avatar = currentDriver.getAvatar();
        updatePassengerRequest.phonenumber = currentDriver.getPhonenumber();
        updatePassengerRequest.gender = currentDriver.getGender();

        APIConnectionRequest.API_PUT_UPDATE_DRIVER(callBackDone, getCurrentDriver().getIdcar(), updatePassengerRequest);

    }

    public void putUpdatePassenger(String accessToken, final CurrentPassenger currentPassenger, String deviceID) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {

                    getCurrentPassenger().setIdPassenger(getCurrentPassenger().getIdPassenger());
                    getCurrentPassenger().setPhone(currentPassenger.getPhone());
                    getCurrentPassenger().setName(currentPassenger.getName());
                    getCurrentPassenger().setGender(currentPassenger.getGender());
                    if (currentPassenger.getAvatar() != null) {
                        getCurrentPassenger().setAvatar(currentPassenger.getAvatar());
                    }
                    getCurrentPassenger().setBirthday(currentPassenger.getBirthday());
                    getCurrentPassenger().setEmail(currentPassenger.getEmail());
                    notifyObserversOfEvent(ModelEvent.PUT_DETAIL_PASSENGER_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        UpdatePassengerRequest updatePassengerRequest = new UpdatePassengerRequest();
        updatePassengerRequest.idpassenger = getCurrentPassenger().getIdPassenger();
        updatePassengerRequest.accesstoken = accessToken;
        updatePassengerRequest.name = currentPassenger.getName();
        updatePassengerRequest.email = currentPassenger.getEmail();
        updatePassengerRequest.avatar = currentPassenger.getAvatar();
        updatePassengerRequest.phone = currentPassenger.getPhone();
        updatePassengerRequest.gender = currentPassenger.getGender();
        updatePassengerRequest.system = Config.ANDROID;
        updatePassengerRequest.deviceid = deviceID;
        updatePassengerRequest.devicetoken = "";

        APIConnectionRequest.API_PUT_UPDATE_PASSENGER(callBackDone, getCurrentPassenger().getIdPassenger(), updatePassengerRequest);

    }


    public void getListCar(String accessToken, Location location) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    GetListCarResponse response = gson.fromJson(result.toString(), GetListCarResponse.class);
                    getDriverArrayList().clear();
                    for (Driver driver : response.data) {
                        getDriverArrayList().add(driver);
                    }
                    notifyObserversOfEvent(ModelEvent.GET_CAR_LIST_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        GetListCarRequest updatePassengerRequest = new GetListCarRequest();
        updatePassengerRequest.geox = String.valueOf(location.getLatitude());
        updatePassengerRequest.accesstoken = accessToken;
        updatePassengerRequest.geoy = String.valueOf(location.getLongitude());

        APIConnectionRequest.API_GET_LIST_CAR(callBackDone, updatePassengerRequest);

    }

    public void bookTheRide(String accessToken, Location location, long idCar, int idPassenger) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    notifyObserversOfEvent(ModelEvent.BOOK_CAR_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        BookCarRequest bookCarRequest = new BookCarRequest();
        bookCarRequest.idcar = idCar;
        bookCarRequest.accesstoken = accessToken;
        bookCarRequest.idpassenger = idPassenger;
        bookCarRequest.passengergeox = String.valueOf(location.getLatitude());
        bookCarRequest.passengergeoy = String.valueOf(location.getLongitude());
        APIConnectionRequest.API_BOOK_CAR(callBackDone, bookCarRequest);

    }

    public void getDetailDriver(String accessToken, long idCar) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();

                    GetDetailDriverResponse response = gson.fromJson(result.toString(), GetDetailDriverResponse.class);
                    getCurrentDriver().setIdcar(response.data.idcar);
                    getCurrentDriver().setName(response.data.name);
                    getCurrentDriver().setAvatar(response.data.avatar);
                    getCurrentDriver().setPhonenumber(response.data.phonenumber);
                    getCurrentDriver().setEmail(response.data.email);
                    getCurrentDriver().setGender(response.data.gender);
                    getCurrentDriver().setBirthday(response.data.birthday);
                    getCurrentDriver().setDriverliscense(response.data.driverliscense);
                    getCurrentDriver().setIdcard(response.data.idcard);
                    getCurrentDriver().setActive(response.data.active);
                    getCurrentDriver().setCarbrand(response.data.carbrand);
                    getCurrentDriver().setCarmodel(response.data.carmodel);
                    getCurrentDriver().setCarno(response.data.carno);
                    getCurrentDriver().setCarseats(response.data.carseats);
                    getCurrentDriver().setTotalmoney(response.data.totalmoney);
                    getCurrentDriver().setTotalpoint(response.data.totalpoint);
                    getCurrentDriver().setAddpoint(response.data.addpoint);

                    notifyObserversOfEvent(ModelEvent.GET_DETAIL_DRIVER_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //create request
        GetDetailDriverRequest request = new GetDetailDriverRequest();
        request.idcar = idCar;
        request.accesstoken = accessToken;

        APIConnectionRequest.API_GET_DETAIL_DRIVER(callBackDone, request, idCar);

    }

    public void insertGPSDriver(String accessToken, Location location, boolean vacant) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    PickUpPassengerReponse response = gson.fromJson(result.toString(), PickUpPassengerReponse.class);
                    if ("Successful Update Action!!!".equalsIgnoreCase(response.data.result)) {

                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        TimeZone tz = TimeZone.getDefault();
        Calendar c = Calendar.getInstance(tz);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        String cDateTime = dateFormat.format(c.getInstance().getTime());

        InsertLocationDriverRequest request = new InsertLocationDriverRequest();
        request.idcard = getCurrentDriver().getIdcard();
        request.type = "1";
        request.geox = String.valueOf(location.getLatitude());
        request.geoy = String.valueOf(location.getLongitude());
        request.time = cDateTime;
        request.vacant = vacant;
        request.direction = "0";
        request.imei = "0";
        request.status = "1";
        request.accesstoken = accessToken;

        if (request.idcard != null) {
            APIConnectionRequest.API_INSERT_LOCATION_DRIVER(callBackDone, request);
        }

    }

    public void pickUpPassenger(String accessToken, int idMatchLog) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        JSONObject data = result.getJSONObject("data");
                        if (data.has("result")) {
                            notifyObserversOfEvent(ModelEvent.PICKUP_PASSENGER_SUCCESS);
                            return;
                        }
                    } catch (Exception ex) {

                    }

//                    notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });


        OnCarRequest request = new OnCarRequest();
        request.idmatchlog = idMatchLog;
        request.accesstoken = accessToken;

        APIConnectionRequest.API_PICK_UP_PASSENGER(callBackDone, request, idMatchLog);

    }

    public void finishRide(String accessToken, int idMatchLog, long idCar, int idPassenger) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        JSONObject data = result.getJSONObject("data");
                        if (data.has("result")) {
                            notifyObserversOfEvent(ModelEvent.FINISH_SUCCESS);
                            setIsDriverVacant(true);
                            return;
                        }
                    } catch (Exception ex) {

                    }

                    //notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });


        OffCarRequest request = new OffCarRequest();

        request.idmatchlog = idMatchLog;
        request.accesstoken = accessToken;
        request.idcar = idCar;
        request.idpassenger = idPassenger;

        APIConnectionRequest.API_OFF_CAR(callBackDone, request);

    }

    public void getListInboxPassenger(String accessToken, long idPassenger, final int page, String time) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        if (!result.has("totalrecord")) {
                            JSONObject data = result.getJSONObject("data");
                            String mResult = data.optString("result");
                            if (mResult.equalsIgnoreCase("No list of the object found!!!")) {
                                notifyObserversOfEvent(ModelEvent.GET_INBOX_NO_LIST_PASSENGER);
                            }
                        } else {
                            Gson gson = new Gson();
                            GetListInboxPassengerResponse response = gson.fromJson(result.toString(), GetListInboxPassengerResponse.class);
                            if (page == 1) {
                                getMessageInbox().clear();
                            }
                            for (GetListInboxPassengerResponse.Inbox inbox : response.data) {
                                MessageInbox temp = new MessageInbox();
                                temp.setMessage(inbox.message);
                                temp.setTime(inbox.pushlogpassengercreateddate);
                                temp.setTitle(inbox.passengername);

                                getMessageInbox().add(temp);
                            }
                            notifyObserversOfEvent(ModelEvent.GET_INBOX_LIST_PASSENGER_SUCCESS);
                        }
                    } catch (Exception ex) {

                    }

                    //notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        GetListInboxPassengerRequest request = new GetListInboxPassengerRequest();

        request.idpassenger = idPassenger;
        request.accesstoken = accessToken;
        request.date = time;
        request.page = page;
        request.sortdirection = 0;

        APIConnectionRequest.API_GET_LIST_INBOX_PASSENGER(callBackDone, request);

    }

    public void getListInboxDriver(String accessToken, long idCar, final int page, String time) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        if (!result.has("totalrecord")) {
                            JSONObject data = result.getJSONObject("data");
                            String mResult = data.optString("result");
                            if (mResult.equalsIgnoreCase("No list of the object found!!!")) {
                                notifyObserversOfEvent(ModelEvent.GET_INBOX_NO_LIST_PASSENGER);
                            }
                        } else {
                            Gson gson = new Gson();
                            GetListInboxDriverResponse response = gson.fromJson(result.toString(), GetListInboxDriverResponse.class);
                            if (page == 1) {
                                getMessageInbox().clear();
                            }
                            for (GetListInboxDriverResponse.Inbox inbox : response.data) {
                                MessageInbox temp = new MessageInbox();
                                temp.setMessage(inbox.message);
                                temp.setTime(inbox.pushlogcarcreateddate);
                                temp.setTitle(inbox.carname);

                                getMessageInbox().add(temp);
                            }
                            notifyObserversOfEvent(ModelEvent.GET_INBOX_LIST_PASSENGER_SUCCESS);
                        }
                    } catch (Exception ex) {

                    }

                    //notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });


        GetListInboxDriverRequest request = new GetListInboxDriverRequest();

        request.idcar = idCar;
        request.accesstoken = accessToken;
        request.date = time;
        request.page = page;
        request.sortdirection = 0;

        APIConnectionRequest.API_GET_LIST_INBOX_DRIVER(callBackDone, request);

    }

    public void getHistoryDriver(String accessToken, long idCar, final int page, String name, String date) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        if (!result.has("totalrecord")) {
                            JSONObject data = result.getJSONObject("data");
                            String mResult = data.optString("result");
                            if (mResult.equalsIgnoreCase("No list of the object found!!!")) {
                                notifyObserversOfEvent(ModelEvent.GET_HISTORY_NO_LIST_PASSENGER);
                            }
                        } else {
                            totalItem = result.optInt("totalrecord", 0);
                            Gson gson = new Gson();
                            GetListHistoryPassengerResponse response = gson.fromJson(result.toString(), GetListHistoryPassengerResponse.class);
                            if (page == 1) {
                                getMessageHistory().clear();
                            }
                            for (GetListHistoryPassengerResponse.History history : response.data) {
                                HistoryList temp = new HistoryList();
                                temp.setHistoryDate(history.matchlogontime);
                                temp.setHistoryId(history.idmatchlog);
                                temp.setHistoryName(history.carname);

                                getMessageHistory().add(temp);
                            }
                            notifyObserversOfEvent(ModelEvent.GET_HISTORYLIST_PASSENGER_SUCCESS);
                        }
                    } catch (Exception ex) {

                    }

                    //notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        GetListHistoryDriverRequest request = new GetListHistoryDriverRequest();

        request.idcar = idCar;
        request.accesstoken = accessToken;
        request.date = date;
        request.page = page;
        request.name = name;

        APIConnectionRequest.API_GET_LIST_HISTORY_DRIVER(callBackDone, request);

    }

    public void getHistoryPassenger(String accessToken, long idPassenger, final int page, String name, String date) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        if (!result.has("totalrecord")) {
                            JSONObject data = result.getJSONObject("data");
                            String mResult = data.optString("result");
                            if (mResult.equalsIgnoreCase("No list of the object found!!!")) {
                                notifyObserversOfEvent(ModelEvent.GET_HISTORY_NO_LIST_PASSENGER);
                            }
                        } else {
                            totalItem = result.optInt("totalrecord", 0);
                            Gson gson = new Gson();
                            GetListHistoryPassengerResponse response = gson.fromJson(result.toString(), GetListHistoryPassengerResponse.class);
                            if (page == 1) {
                                getMessageHistory().clear();
                            }
                            for (GetListHistoryPassengerResponse.History history : response.data) {
                                HistoryList temp = new HistoryList();
                                temp.setHistoryDate(history.matchlogontime);
                                temp.setHistoryId(history.idmatchlog);
                                temp.setHistoryName(history.carname);

                                getMessageHistory().add(temp);
                            }
                            notifyObserversOfEvent(ModelEvent.GET_HISTORYLIST_PASSENGER_SUCCESS);
                        }
                    } catch (Exception ex) {

                    }

                    //notifyObserversOfEvent(ModelEvent.CHECK_SMS_CODE_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        GetListHistoryPassengerRequest request = new GetListHistoryPassengerRequest();

        request.idpassenger = idPassenger;
        request.accesstoken = accessToken;
        request.date = date;
        request.page = page;
        request.name = name;


        APIConnectionRequest.API_GET_LIST_HISTORY_PASSENGER(callBackDone, request);

    }

    @Override
    public void update(Observable observable, Object o) {

    }

    public void notifyObserversOfEvent(ModelEvent mEvent) {
        setChanged();
        TaxiLoyDebug.d("Model notifying observers: " + mEvent);
        notifyObservers(mEvent);
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getSMSCode() {
        if (smsCode != null) {
            return smsCode;
        }
        return null;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        if (accessToken != null) {
            return accessToken;
        }
        return null;
    }

    public void callApiReportPassenger(String accessToken, final ReportPassenger reportPassenger, long idpassenger) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        ReportPassengerRequest request = new ReportPassengerRequest();
        request.accesstoken = accessToken;
        request.idpassenger = idpassenger;
        request.name = reportPassenger.getName();
        request.phonenumber = reportPassenger.getPhonenumber();
        request.ridecode = reportPassenger.getRidecode();
        request.content = reportPassenger.getContent();


        APIConnectionRequest.API_REPORT_PASSENGER(callBackDone, request);
    }

    public void getBannerSlider(String accessToken) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    GetBannerSliderResponse response = gson.fromJson(result.toString(), GetBannerSliderResponse.class);
                    for (GetBannerSliderResponse.Banner banner : response.data) {
                        getBanners().add(banner);
                    }

                    notifyObserversOfEvent(ModelEvent.GET_BANNER_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });
        GetBannerSliderRequest request = new GetBannerSliderRequest();
        request.accesstoken = accessToken;

        APIConnectionRequest.API_GET_BANNER_LOCATION(callBackDone, request);
    }

    public void callApiSupportPassenger(String accessToken, final SupportPassenger supportPassenger, long idpassenger) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        SupportPassengerRequest request = new SupportPassengerRequest();
        request.accesstoken = accessToken;
        request.idpassenger = idpassenger;
        request.title = supportPassenger.getTitle();
        request.content = supportPassenger.getContent();

        APIConnectionRequest.API_SUPPORT_PASSENGER(callBackDone, request);
    }

    public void callApiPassengerCancel(String accessToken, long idpassenger, Location location, long idDriver) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {

            }

            @Override
            public void onComplete() {

            }
        });

        PassengerCancelRequest request = new PassengerCancelRequest();
        request.accesstoken = accessToken;
        request.idpassenger = idpassenger;
        request.idcar = idDriver;
        request.passengergeox = location.getLatitude();
        request.passengergeoy = location.getLongitude();

        APIConnectionRequest.API_CANCEL_PASSENGER(callBackDone, request);
    }

    public void callApiCheckVacant(final String accessToken, final long idcar, final Location location, final int idPassenger) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    CheckVacantDriverResponse response = gson.fromJson(result.toString(), CheckVacantDriverResponse.class);
                    if ("True".equalsIgnoreCase(response.data.result)) {
                        bookTheRide(accessToken, location, idcar, idPassenger);
                    } else {
                        notifyObserversOfEvent(ModelEvent.CHECK_VACANT_DRIVER_FAILED);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {

            }

            @Override
            public void onComplete() {

            }
        });

        CheckVacantRequest request = new CheckVacantRequest();
        request.accesstoken = accessToken;
        request.idcar = idcar;

        APIConnectionRequest.API_CHECK_VACANT(callBackDone, request);
    }

    public void ratingDriver(final String accessToken, final long idcar, final int idPassenger, int rating) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    InsertRatingDriverResponse response = gson.fromJson(result.toString(), InsertRatingDriverResponse.class);
                    if (response.data != null) {
                        notifyObserversOfEvent(ModelEvent.RATING_DRIVER_SUCCESS);
                    } else {
                        notifyObserversOfEvent(ModelEvent.RATING_DRIVER_FAIL);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {

            }

            @Override
            public void onComplete() {

            }
        });

        InsertRatingDriverRequest request = new InsertRatingDriverRequest();
        request.accesstoken = accessToken;
        request.idcar = idcar;
        request.idpassenger = idPassenger;
        request.rating = rating;

        APIConnectionRequest.API_RATING_DRIVER(callBackDone, request);
    }

    public void getPassengerLocation(final String accessToken, int idMatchLog) {
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    GetDriverPassengerResponse response = gson.fromJson(result.toString(), GetDriverPassengerResponse.class);
                    if (response.data != null) {
                        getPassengerLocation().setLatitude(Float.parseFloat(response.data.passengergeox));
                        getPassengerLocation().setLongitude(Float.parseFloat(response.data.passengergeoy));

                        notifyObserversOfEvent(ModelEvent.GET_PASSENGER_DRIVER_SUCCESS);
                    } else {
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {

            }

            @Override
            public void onComplete() {

            }
        });

        GetPassengerLocationRequest request = new GetPassengerLocationRequest();
        request.accesstoken = accessToken;
        request.idmatchlog = idMatchLog;

        APIConnectionRequest.API_GET_PASSENGER_LOCATION(callBackDone, request, idMatchLog);
    }

    public void getDetailRating(final String accessToken, long idcar){
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try{
                    JSONObject obj = result.getJSONObject("data");
                        int rating = obj.getInt("rating");
                        Log.i("TestRating", rating+"");
                        getCurrentDriver().setRating(rating);
                        notifyObserversOfEvent(ModelEvent.GET_RATING_DRIVER_SUCCESS);
                    }catch (Exception ex){
                        Log.e("Exception:", "" + ex);
                    }
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(ModelObject modelObject) {
            }

            @Override
            public void onFail(ModelError error) {
            }

            @Override
            public void onComplete() {
            }
        });

        //send request
        GetRatingDriverRequest request = new GetRatingDriverRequest();
        request.accesstoken = accessToken;
        request.idcar = idcar;

        APIConnectionRequest.API_GET_DETAIL_RATING(callBackDone, request, idcar);
    }
}
