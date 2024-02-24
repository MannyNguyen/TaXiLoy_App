package com.example.prosoft.taxiloy.ui.objects;

/**
 * Created by Manh on 2/23/2016.
 */
public class PassengerCanceled extends BaseModelObject{
    private long idpassenger;
    private long idcar;
    private String passengergeox;
    private String passengergeoy;
    private String accesstoken;

    public long getIdpassenger() {
        return idpassenger;
    }

    public void setIdpassenger(long idpassenger) {
        this.idpassenger = idpassenger;
    }

    public long getIdcar() {
        return idcar;
    }

    public void setIdcar(long idcar) {
        this.idcar = idcar;
    }

    public String getPassengergeox() {
        return passengergeox;
    }

    public void setPassengergeox(String passengergeox) {
        this.passengergeox = passengergeox;
    }

    public String getPassengergeoy() {
        return passengergeoy;
    }

    public void setPassengergeoy(String passengergeoy) {
        this.passengergeoy = passengergeoy;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
