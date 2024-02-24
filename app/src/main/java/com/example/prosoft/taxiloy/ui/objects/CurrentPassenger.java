package com.example.prosoft.taxiloy.ui.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prosoft on 1/11/16.
 */
public class CurrentPassenger extends BaseModelObject {

    private int idPassenger;
    private String name;
    private String email;
    private String avatar;
    private String phone;
    private String gender;
    private String birthday;
    private float point;
    private String createdDate ;

    public int getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(int idPassenger) {
        this.idPassenger = idPassenger;
        update();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        update();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        update();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        update();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        update();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        update();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        update();
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
        update();
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        update();
    }

    private void update() {

    }

    @Override
    public String getKeyPath() {
        return "user";
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
            setIdPassenger(jsObj.optInt("idpassenger", 0));
            setName(jsObj.optString("name", null));
            setEmail(jsObj.optString("email", null));
            setAvatar(jsObj.optString("avatar", null));
            setPhone(jsObj.optString("phone", null));
            setGender(jsObj.optString("gender", null));
            setBirthday(jsObj.optString("birthday", null));
            setPoint(jsObj.optLong("point"));
            setCreatedDate(jsObj.optString("createddate", null));
        }

        setClean(true);

    }

    @Override
    public JSONObject getJSONRepresentation() {
        try {
            JSONObject jsObj = new JSONObject();

            jsObj.put("idpassenger", getIdPassenger());
            jsObj.put("name", getName());
            jsObj.put("email", getEmail());
            jsObj.put("avatar", getAvatar());
            jsObj.put("phone", getPhone());
            jsObj.put("gender", getGender());
            jsObj.put("birthday", getBirthday());
            jsObj.put("point", getPoint());
            jsObj.put("createddate", getCreatedDate());


            return jsObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString() {
        return "Passenger user with ID: " + this.idPassenger + " email: " + this.phone ;
    }
}
