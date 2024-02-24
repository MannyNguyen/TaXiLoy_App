package com.example.prosoft.taxiloy.ui.enums;

/**
 * Created by prosoft on 1/12/16.
 */
public enum UserAccountTypeEnum {

    TYPE1("Passenger"),
    TYPE2("Driver");

    private String mType;

    UserAccountTypeEnum(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

}
