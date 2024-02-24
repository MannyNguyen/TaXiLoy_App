package com.example.prosoft.taxiloy.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.gcm.RegistrationIntentService;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;

import java.util.Locale;

/**
 * Created by prosoft on 12/16/15.
 */
public class SplashScreenActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        SystemUtils.saveDeviceID(android_id, this);

        if (SystemUtils.checkPlayServices(this)) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashScreenActivity.this, LoginScreenActivity.class);
                startActivity(mainIntent);

                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
