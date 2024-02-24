package com.example.prosoft.taxiloy.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.adapter.DefaultCarAdapter;
import com.example.prosoft.taxiloy.ui.adapter.SliderPagerAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.fragment.FragmentGoogleMap;
import com.example.prosoft.taxiloy.ui.gcm.QuickstartPreferences;
import com.example.prosoft.taxiloy.ui.objects.CurrentDriver;
import com.example.prosoft.taxiloy.ui.service.GPSTracker;
import com.example.prosoft.taxiloy.ui.utils.Config;
import com.example.prosoft.taxiloy.ui.utils.ImageLoader;
import com.example.prosoft.taxiloy.ui.utils.PollingTimer;
import com.example.prosoft.taxiloy.ui.utils.PollingTimerDelegate;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.CircleImageView;
import com.example.prosoft.taxiloy.ui.view.SlidingMenu;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by prosoft on 12/25/15.
 */
public class SliderScreenActivity extends BaseActivity implements PollingTimerDelegate, CompoundButton.OnCheckedChangeListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private SlidingMenu mDrawerList;
    private String balance = "Balance: %s USD";
    private String point = "Point(s): %s";
    private PollingTimer timer;
    private GPSTracker gps;
    private BroadcastReceiver mReceiver;
    private boolean isDialogShowed = false;
    private Location mLocation;
    private SliderPagerAdapter sliderAdapter;
    private int numStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_slider_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Window window = getWindow();
        mDrawerList = (SlidingMenu) findViewById(R.id.left_drawer);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (SlidingMenu) findViewById(R.id.left_drawer);
        mDrawerList.setDrawerLayout(drawerLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        sliderAdapter = new SliderPagerAdapter(this, Model.getInstance().getBanners());
        viewPager.setAdapter(sliderAdapter);

        initViews();
        getDetailDriver();
        getBannerSlider();

        //Run checkbox to vacant true or false.
        getChkBox().setOnCheckedChangeListener(this);
    }

    private void initViews() {
        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String phoneNumber = intent.getStringExtra(QuickstartPreferences.PHONE_NUMBER_OF_PASSENGER);
                int idPassenger = intent.getExtras().getInt(QuickstartPreferences.ID_PASSENGER);
                if (phoneNumber != null) {
                    Intent i = new Intent(SliderScreenActivity.this, MapScreenDriverActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(QuickstartPreferences.PHONE_NUMBER_OF_PASSENGER, phoneNumber);
                    i.putExtra(QuickstartPreferences.ID_PASSENGER, idPassenger);
                    startActivity(i);
                }

            }
        };
    }

    //button check of driver
    private void onCheckBoxClicked(boolean isChecked) {
        if (isChecked) {
            if (Model.getInstance().getCurrentDriver().getActive() != 0 && mLocation != null) {
                timer.start(false);
                Model.getInstance().insertGPSDriver(SystemUtils.getAccessToken(this), mLocation, true);
                Model.getInstance().setIsDriverVacant(true);
            }
        } else {
            if (Model.getInstance().getCurrentDriver().getActive() != 0 && mLocation != null) {
                timer.stop();
                Model.getInstance().insertGPSDriver(SystemUtils.getAccessToken(this), mLocation, false);
                Model.getInstance().setIsDriverVacant(false);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void getDetailDriver() {
        //Run getDetail 2
        Model.getInstance().getDetailDriver(SystemUtils.getAccessToken(this), SystemUtils.getIDCar(this));
        Model.getInstance().getDetailRating(SystemUtils.getAccessToken(this), SystemUtils.getIDCar(this));
    }

    private void getBannerSlider() {
        Model.getInstance().getBannerSlider(SystemUtils.getAccessToken(this));
    }

    private void insertLocationDriver(Location location) {
        if (Model.getInstance().getCurrentDriver().getActive() != 0) {
            Model.getInstance().insertGPSDriver(SystemUtils.getAccessToken(this), location, true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.agreeCheckbox) {
            onCheckBoxClicked(isChecked);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        if (evt == ModelEvent.GET_DETAIL_DRIVER_SUCCESS) {
            getSlidingMenu().showInfo();
            showInfo();
        } else if (evt == ModelEvent.GET_BANNER_SUCCESS) {
            sliderAdapter.notifyDataSetChanged();
        } else if (evt == ModelEvent.GET_RATING_DRIVER_SUCCESS) {
            showRating(Model.getInstance().getCurrentDriver().getRating());
        }
    }

    private void showInfo() {
        getTvName().setText(Model.getInstance().getCurrentDriver().getName());
        getTvBalance().setText(String.format(balance, Model.getInstance().getCurrentDriver().getTotalmoney()));
        getTvPoint().setText(String.format(point, Model.getInstance().getCurrentDriver().getTotalpoint()));

        if (SystemUtils.StringToBitMap(Model.getInstance().getCurrentDriver().getAvatar(), 50, 50) != null) {
            getAvatar().setImageBitmap(SystemUtils
                    .StringToBitMap(Model.getInstance().getCurrentDriver().getAvatar(), 50, 50));
        }
    }

    private void settingGPS() {
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            if (gps.getLocation() != null) {
                insertLocationDriver(gps.getLocation());
                //Need GPS for params, driver
                mLocation = gps.getLocation();
            }
        } else if (!isDialogShowed) {
            isDialogShowed = true;
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setIcon(R.color.yellowDark);
        item.setEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void timerDidFire(PollingTimer timer) {
        settingGPS();
    }

    @Override
    public void onResume() {
        super.onResume();
        Model.getInstance().setInApp(true);
        timer = new PollingTimer(this, 0);
        if (Model.getInstance().isDriverVacant()) {
            getChkBox().setChecked(true);
            timer.start(true);
        } else {
            getChkBox().setChecked(false);
            timer.stop();
        }
        getSlidingMenu().showInfo();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(QuickstartPreferences.A_NEW_RIDE_COMES));
    }

    @Override
    public void onPause() {
        super.onPause();
        Model.getInstance().setInApp(false);
        timer.stop();
        timer = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    private SlidingMenu getSlidingMenu() {
        return (SlidingMenu) findViewById(R.id.left_drawer);
    }

    private TextView getTvName() {
        return (TextView) findViewById(R.id.tv_name);
    }

    private TextView getTvBalance() {
        return (TextView) findViewById(R.id.tv_balance);
    }

    private TextView getTvPoint() {
        return (TextView) findViewById(R.id.tv_point);
    }

    private CheckBox getChkBox() {
        return (CheckBox) findViewById(R.id.agreeCheckbox);
    }

    private CircleImageView getAvatar() {
        return (CircleImageView) findViewById(R.id.iv_avatar);
    }

    private ImageButton getcheckstar1() {
        return (ImageButton) findViewById(R.id.imb_star_check_1);
    }

    private ImageButton getcheckstar2() {
        return (ImageButton) findViewById(R.id.imb_star_check_2);
    }

    private ImageButton getcheckstar3() {
        return (ImageButton) findViewById(R.id.imb_star_check_3);
    }

    private ImageButton getcheckstar4() {
        return (ImageButton) findViewById(R.id.imb_star_check_4);
    }

    private ImageButton getcheckstar5() {
        return (ImageButton) findViewById(R.id.imb_star_check_5);
    }

    private void showRating(int rating) {
        switch (rating) {
            case 1:
                getcheckstar1().setBackgroundResource(R.drawable.icon_star_white);
                break;
            case 2:
                getcheckstar1().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar2().setBackgroundResource(R.drawable.icon_star_white);
                break;
            case 3:
                getcheckstar1().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar2().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar3().setBackgroundResource(R.drawable.icon_star_white);
                break;
            case 4:
                getcheckstar1().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar2().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar3().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar4().setBackgroundResource(R.drawable.icon_star_white);
                break;
            case 5:
                getcheckstar1().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar2().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar3().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar4().setBackgroundResource(R.drawable.icon_star_white);
                getcheckstar5().setBackgroundResource(R.drawable.icon_star_white);
                break;
        }
    }
}