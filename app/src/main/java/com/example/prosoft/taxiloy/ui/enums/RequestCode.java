package com.example.prosoft.taxiloy.ui.enums;

/**
 * Created by prosoft on 1/12/16.
 */
public enum RequestCode {

    REQUEST_CODE_1(1),
    REQUEST_CODE_2(2);

    private int mCode;

    RequestCode(int code) {
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}
