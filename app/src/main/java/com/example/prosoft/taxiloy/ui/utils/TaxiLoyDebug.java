package com.example.prosoft.taxiloy.ui.utils;

/**
 * Created by prosoft on 12/24/15.
 */
public class TaxiLoyDebug {
    public static void d(String s) {
        if (Config.DEBUG) {
            System.err.println(s);
        }
    }
}
