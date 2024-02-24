package com.example.prosoft.taxiloy.ui.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.adapter.DefaultCarAdapter;
import com.example.prosoft.taxiloy.ui.adapter.GenderAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.fragment.FragmentGoogleMap;
import com.example.prosoft.taxiloy.ui.gcm.QuickstartPreferences;
import com.example.prosoft.taxiloy.ui.service.GPSTracker;
import com.example.prosoft.taxiloy.ui.utils.ShakeDetected;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.SlidingMenu;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

/**
 * Created by prosoft on 2/3/16.
 */
public class MapScreenDriverActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String phoneNumber;
    private SlidingMenu mDrawerList;
    private int idMatchLog;
    private int idPassenger;
    private GPSTracker gps;
    private BroadcastReceiver mReceiver;
    private boolean isDialogShowed = false;
    private Location myLocation;
    private FragmentGoogleMap fragmentGoogleMap = new FragmentGoogleMap();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_driver_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Window window = getWindow();

        fragmentGoogleMap.setIsDisableClickMarker(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        //NOT USE LEFT MENU WHEN RECEIVE BOOKING
//        mDrawerList = (SlidingMenu) findViewById(R.id.left_drawer);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList.setDrawerLayout(drawerLayout);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
//                drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
//            public void onDrawerClosed(View view) {
//
//                invalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//
//                invalidateOptionsMenu();
//            }
//
//        };
//
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        initViews();

    }

    private void initViews() {

        FragmentManager childFragMan = getSupportFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.replace(R.id.FRAGMENT_PLACEHOLDER, fragmentGoogleMap);
        childFragTrans.commit();

        getBtCall().setOnClickListener(this);
        getBtSendSMS().setOnClickListener(this);
        getBtPickup().setOnClickListener(this);
        getBtSendsms().setOnClickListener(this);
        getBtCall1().setOnClickListener(this);
        getBtFinish().setOnClickListener(this);

        idMatchLog = SystemUtils.getIDMatchLog(this);

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                showComfirmCanceledDialog();
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.bt_call):
                if (phoneNumber != null) {
                    //get phone number from SystemUtils
                    SystemUtils.actionCall(phoneNumber, this);
                    getViewCongrat().setVisibility(View.GONE);
                    getViewPickup().setVisibility(View.VISIBLE);
                    Model.getInstance().getMediaPlayer(this).pause();
                }
                break;
            case (R.id.bt_send_sms):
                if (phoneNumber != null) {
                    SystemUtils.sendSMS(phoneNumber, this);
                    getViewCongrat().setVisibility(View.GONE);
                    getViewPickup().setVisibility(View.VISIBLE);
                    Model.getInstance().getMediaPlayer(this).pause();
                }
                break;
            case (R.id.rl_pickup):
                if (idMatchLog != 0) {
                    showComfirmPickupDialog();
                }
                break;
            case (R.id.rl_sendsms):
                if (phoneNumber != null) {
                    SystemUtils.sendSMS(phoneNumber, this);
                }
                break;
            case (R.id.rl_call):
                if (phoneNumber != null) {
                    SystemUtils.actionCall(phoneNumber, this);
                }
                break;
            case (R.id.bt_finish):
                showDialogComfirmFinish();
                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Model.getInstance().setInApp(true);
        Intent intent = getIntent();
        switch (Model.getInstance().typePush) {
            case 1:
                gps = new GPSTracker(this);
                if (gps.canGetLocation()) {
                    if (gps.getLocation() != null) {
                        insertLocationDriver(gps.getLocation());
                    }
                } else if (!isDialogShowed) {
                    isDialogShowed = true;
                    gps.showSettingsAlert();
                }
                break;
            case 5:
                showComfirmCanceledDialog();
                break;
        }
        phoneNumber = intent.getExtras().getString(QuickstartPreferences.PHONE_NUMBER_OF_PASSENGER);
        idPassenger = intent.getExtras().getInt(QuickstartPreferences.ID_PASSENGER);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(QuickstartPreferences.THE_RIDE_CANCELED));
        //Not use left menu
//        getSlidingMenu().showInfo();
        Model.getInstance().getPassengerLocation(SystemUtils.getAccessToken(this), SystemUtils.getIDMatchLog(this));
    }

    @Override
    public void onPause() {
        super.onPause();
        Model.getInstance().setInApp(false);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
//        MenuItem item = menu.findItem(R.id.action_settings);
//        item.setIcon(R.color.yellowDark);
//        item.setEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        if (evt == ModelEvent.PICKUP_PASSENGER_SUCCESS) {
            getBusyIndicator("Pick up ...").dismiss();
            getViewFinish().setVisibility(View.VISIBLE);
            getViewPickup().setVisibility(View.GONE);
            getTvTitle().setText(getString(R.string.finish));
        } else if (evt == ModelEvent.FINISH_SUCCESS) {
            Intent i = new Intent(this, AddPointActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else if (evt == ModelEvent.GET_PASSENGER_DRIVER_SUCCESS) {
            if (fragmentGoogleMap.isReady) {
                fragmentGoogleMap.updatePassengerLocation(Model.getInstance().getPassengerLocation());
                if (gps.getLocation() != null) {
                    fragmentGoogleMap.updateDriver(gps.getLocation());
                }
            }


        }
    }

    private RelativeLayout getBtCall() {
        return (RelativeLayout) findViewById(R.id.bt_call);
    }

    private RelativeLayout getBtSendSMS() {
        return (RelativeLayout) findViewById(R.id.bt_send_sms);
    }

    private LinearLayout getViewCongrat() {
        return (LinearLayout) findViewById(R.id.ll_congra);
    }

    private LinearLayout getViewPickup() {
        return (LinearLayout) findViewById(R.id.ll_pickup);
    }

    private LinearLayout getViewFinish() {
        return (LinearLayout) findViewById(R.id.ll_finish);
    }

    private RelativeLayout getBtPickup() {
        return (RelativeLayout) findViewById(R.id.rl_pickup);
    }

    private TextView getTvTitle() {
        return (TextView) findViewById(R.id.tv_title);
    }

    private RelativeLayout getBtSendsms() {
        return (RelativeLayout) findViewById(R.id.rl_sendsms);
    }

    private RelativeLayout getBtCall1() {

        return (RelativeLayout) findViewById(R.id.rl_call);
    }

    private Button getBtFinish() {
        return (Button) findViewById(R.id.bt_finish);
    }

    private void showComfirmPickupDialog() {

        final Dialog dialog = new Dialog(MapScreenDriverActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_finally_confirm);

        Button bt_ok = (Button) dialog.findViewById(R.id.btn_yes);
        Button bt_cancel = (Button) dialog.findViewById(R.id.btn_no);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().pickUpPassenger(SystemUtils.getAccessToken(MapScreenDriverActivity.this), idMatchLog);
                getBusyIndicator("Pick up ...").show();
                dialog.dismiss();
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

    private void showComfirmCanceledDialog() {

        final Dialog dialog = new Dialog(MapScreenDriverActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_driver_confirm_cancel);

        Button bt_call = (Button) dialog.findViewById(R.id.btn_call_pass);
        Button bt_sms = (Button) dialog.findViewById(R.id.btn_sms);
        Button bt_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.actionCall(phoneNumber, MapScreenDriverActivity.this);
            }
        });

        bt_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.sendSMS(phoneNumber, MapScreenDriverActivity.this);
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });


        dialog.show();
    }

    private void showDialogComfirmFinish() {

        final Dialog dialog = new Dialog(MapScreenDriverActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_finish_pickup);

        Button bt_ok = (Button) dialog.findViewById(R.id.bt_ok);
        Button bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Model.getInstance().finishRide(SystemUtils.getAccessToken(MapScreenDriverActivity.this),
                        idMatchLog, SystemUtils.getIDCar(MapScreenDriverActivity.this), idPassenger);
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
    public void onBackPressed() {
        // prevent back
    }

    private void insertLocationDriver(Location location) {
        if (Model.getInstance().getCurrentDriver().getActive() != 0) {
            Model.getInstance().insertGPSDriver(SystemUtils.getAccessToken(this), location, false);
        }
    }

    private SlidingMenu getSlidingMenu() {
        return (SlidingMenu) findViewById(R.id.left_drawer);
    }
}
