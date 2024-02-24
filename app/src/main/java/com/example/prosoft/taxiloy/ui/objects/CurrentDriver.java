package com.example.prosoft.taxiloy.ui.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prosoft on 1/19/16.
 */
public class CurrentDriver extends BaseModelObject {

    private long idcar;
    private int active;
    private String name;
    private String email;
    private String avatar;
    private String gender;
    private String birthday;
    private String phonenumber;
    private String phonecarrier;
    private int addpoint;
    private double addmoney;
    private double cutmoney;
    private double totalmoney;
    private double totalpoint;
    private double totalcutmoney;
    private String driverliscense;
    private String idcard;
    private String carno;
    private String cartag;
    private String carbrand;
    private String carmodel;
    private String carseats;
    private String idcartype;
    private String year;
    private String company;
    private String cheapcar;
    private String tour;
    private String specialevents;
    private String likes;
    private String createddate;
    private ListImageCar[] listimagecar;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public class ListImageCar {
        public String idimagecar;
        public String idcar;
        public String idimagecartype;
        public String name;
        public String images;
    }

    public ListImageCar[] getListimagecar() {
        return listimagecar;
    }

    public void setListimagecar(ListImageCar[] listimagecar) {
        this.listimagecar = listimagecar;
    }

    public long getIdcar() {
        return idcar;
    }

    public void setIdcar(long idcar) {
        this.idcar = idcar;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonecarrier() {
        return phonecarrier;
    }

    public void setPhonecarrier(String phonecarrier) {
        this.phonecarrier = phonecarrier;
    }

    public int getAddpoint() {
        return addpoint;
    }

    public void setAddpoint(int addpoint) {
        this.addpoint = addpoint;
    }

    public double getAddmoney() {
        return addmoney;
    }

    public void setAddmoney(double addmoney) {
        this.addmoney = addmoney;
    }

    public double getCutmoney() {
        return cutmoney;
    }

    public void setCutmoney(double cutmoney) {
        this.cutmoney = cutmoney;
    }

    public double getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(double totalmoney) {
        this.totalmoney = totalmoney;
    }

    public double getTotalpoint() {
        return totalpoint;
    }

    public void setTotalpoint(double totalpoint) {
        this.totalpoint = totalpoint;
    }

    public double getTotalcutmoney() {
        return totalcutmoney;
    }

    public void setTotalcutmoney(double totalcutmoney) {
        this.totalcutmoney = totalcutmoney;
    }

    public String getDriverliscense() {
        return driverliscense;
    }

    public void setDriverliscense(String driverliscense) {
        this.driverliscense = driverliscense;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getCartag() {
        return cartag;
    }

    public void setCartag(String cartag) {
        this.cartag = cartag;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getCarseats() {
        return carseats;
    }

    public void setCarseats(String carseats) {
        this.carseats = carseats;
    }

    public String getIdcartype() {
        return idcartype;
    }

    public void setIdcartype(String idcartype) {
        this.idcartype = idcartype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCheapcar() {
        return cheapcar;
    }

    public void setCheapcar(String cheapcar) {
        this.cheapcar = cheapcar;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public String getSpecialevents() {
        return specialevents;
    }

    public void setSpecialevents(String specialevents) {
        this.specialevents = specialevents;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    private void update() {

    }

    @Override
    public String getKeyPath() {
        return "passenger";
    }

    @Override
    public void setValuesWithJSON(Object jsonObject) {
        JSONObject jsObj = null;

        try {
            jsObj = (JSONObject) jsonObject;
        } catch (ClassCastException e) {
            JSONArray jsonArray = (JSONArray) jsonObject;
            jsObj = jsonArray.optJSONObject(0);
        }

        if (jsObj != null) {

            setIdcar(jsObj.optInt("idcar", 0));
            setName(jsObj.optString("name", null));
            setEmail(jsObj.optString("email", null));
            setAvatar(jsObj.optString("avatar", null));
            setPhonenumber(jsObj.optString("phonenumber", null));
            setGender(jsObj.optString("gender", null));
            setBirthday(jsObj.optString("birthday", null));
            setPhonecarrier(jsObj.optString("phonecarrier", null));
            setAddpoint(jsObj.optInt("addpoint"));
            setAddmoney(jsObj.optDouble("addmoney"));
            setCutmoney(jsObj.optDouble("cutmoney"));
            setTotalmoney(jsObj.optDouble("totalmoney"));
            setTotalpoint(jsObj.optDouble("totalpoint"));
            setTotalcutmoney(jsObj.optDouble("totalcutmoney"));
            setDriverliscense(jsObj.optString("driverliscense", null));
            setIdcard(jsObj.optString("idcard", null));
            setCarno(jsObj.optString("carno", null));
            setCartag(jsObj.optString("cartag", null));
            setCarbrand(jsObj.optString("carbrand", null));
            setCarmodel(jsObj.optString("carmodel", null));
            setCarseats(jsObj.optString("carseats", null));
            setIdcartype(jsObj.optString("idcartype", null));
            setYear(jsObj.optString("year", null));
            setCompany(jsObj.optString("company", null));
            setCheapcar(jsObj.optString("cheapcar", null));
            setTour(jsObj.optString("tour", null));
            setSpecialevents(jsObj.optString("specialevents", null));
            setLikes(jsObj.optString("likes", null));
            setCreateddate(jsObj.optString("createddate", null));

        }

        setClean(true);

    }

    @Override
    public JSONObject getJSONRepresentation() {

        return null;
    }

    @Override
    public String toString() {
        return "Passenger user with ID: " + this.idcar + " email: " + this.email;
    }
}
