package com.example.prosoft.taxiloy.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.fragment.FragmentGoogleMap;
import com.example.prosoft.taxiloy.ui.gcm.QuickstartPreferences;
import com.example.prosoft.taxiloy.ui.objects.PassengerCanceled;
import com.example.prosoft.taxiloy.ui.service.GPSTracker;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;

/**
 * Created by Manh on 2/22/2016.
 */
public class DialogBookSuccess extends BaseActivity {

    private GPSTracker gps;
    private long idDriver;
    private String phoneDriver;
    private BroadcastReceiver mReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notification_book_driver);
        this.setFinishOnTouchOutside(false);

        getBtnCancel().setOnClickListener(this);
        getBtnCall().setOnClickListener(this);
        getBtnSMS().setOnClickListener(this);
        Intent intent = getIntent();
        idDriver = intent.getExtras().getLong(QuickstartPreferences.ID_DRIVER);
        phoneDriver = intent.getExtras().getString(QuickstartPreferences.PHONE_DRIVER);

        //statusOfPassenger = 2, passenger in status after book
        Model.getInstance().statusOfPassenger = 2;


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_cancel_ride):
                //Need location geox, geoy and idcar
                gps = new GPSTracker(this);
                if (gps.canGetLocation()) {
                    //Request gps location to function Cancel car
                    showDialogComfirmFinish();
                }
                break;

            case (R.id.notify_call):
                //Call phone number from SystemUtls
                SystemUtils.actionCall(phoneDriver, this);
                break;

            case (R.id.notify_sms):
                //Call SMS from SystemUtils
                SystemUtils.sendSMS(phoneDriver, this);
                break;

        }
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

    }

    private void showDialogComfirmFinish() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_passenger_confirm_cancel);

        Button bt_ok = (Button) dialog.findViewById(R.id.bt_ok);
        Button bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Model.getInstance().callApiPassengerCancel(SystemUtils.getAccessToken(DialogBookSuccess.this),
                        SystemUtils.getIDPassenger(DialogBookSuccess.this), gps.getLocation(), idDriver);
                Model.getInstance().statusOfPassenger = 1;
                finish();

            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Model.getInstance().setInApp(false);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        Model.getInstance().setInApp(true);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(QuickstartPreferences.THE_RIDE_CANCELED));
    }

    @Override
    public void onBackPressed() {
        // prevent back
    }

    private Button getBtnCancel() {
        return (Button) findViewById(R.id.btn_cancel_ride);
    }

    private Button getBtnCall() {
        return (Button) findViewById(R.id.notify_call);
    }

    private Button getBtnSMS() {
        return (Button) findViewById(R.id.notify_sms);
    }

}
