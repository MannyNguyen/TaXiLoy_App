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
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.adapter.DefaultCarAdapter;
import com.example.prosoft.taxiloy.ui.adapter.GenderAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.fragment.FragmentGoogleMap;
import com.example.prosoft.taxiloy.ui.gcm.QuickstartPreferences;
import com.example.prosoft.taxiloy.ui.objects.CarType;
import com.example.prosoft.taxiloy.ui.objects.Driver;
import com.example.prosoft.taxiloy.ui.service.GPSTracker;
import com.example.prosoft.taxiloy.ui.utils.ShakeDetected;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.CustomListView;
import com.example.prosoft.taxiloy.ui.view.SlidingMenu;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.example.prosoft.taxiloy.ui.activity.LoginScreenActivity;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.net.URL;
import java.util.ArrayList;

public class MainScreenActivity extends BaseActivity implements View.OnClickListener, CustomListView.EndlessListener, SwipeRefreshLayout.OnRefreshListener, ListView.OnItemClickListener, ShakeDetected.OnShakeListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DefaultCarAdapter carAdapter;
    private DrawerLayout drawerLayout;
    private SlidingMenu mDrawerList;
    private MenuDrawer mMenuDrawer;
    private GPSTracker gps;
    private GenderAdapter adapter;
    private int page = 1;
    private int tabChange = 10;
    private ArrayList<String> optionArrayStairsToBuilding = new ArrayList<>();
    private Location myLocation;
    private int posSpinner;
    private boolean listCarDefault = true;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetected mShakeDetector;
    private int posCarShake;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Tracker mTracker;
    private FragmentGoogleMap fragment = new FragmentGoogleMap();
    private boolean isShowNoDriverToShake = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Window window = getWindow();
        mDrawerList = (SlidingMenu) findViewById(R.id.left_drawer);

        //Regulations android version
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList.setDrawerLayout(drawerLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                mMenuDrawer.setEnabled(true);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                mMenuDrawer.setEnabled(false);
                invalidateOptionsMenu();
            }

        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY,
                Position.RIGHT, MenuDrawer.MENU_DRAG_WINDOW);

        mMenuDrawer.setMenuView(R.layout.slidingmenu_right_list);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        mMenuDrawer.setMenuSize((width >= 640) ? 600 : 400);

        //mMenuDrawer.peekDrawer();


        getDetailPassenger();
        initViews();

    }

    private void getDetailPassenger() {
        Model.getInstance().getDetailPassenger(SystemUtils.getIDPassenger(this),
                SystemUtils.getAccessToken(this));
    }

    private void initViews() {

        FragmentManager childFragMan = getSupportFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.replace(R.id.FRAGMENT_PLACEHOLDER, fragment);
        childFragTrans.commit();

        adapter = new GenderAdapter(this, R.layout.cell_spinner_gender_1,
                optionArrayStairsToBuilding, getResources());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinner().setAdapter(adapter);
        getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initializedAdapter != parent.getAdapter()) {
                    initializedAdapter = parent.getAdapter();
                    return;
                }
                if (position > 0) {
                    page = 1;
                    posSpinner = position - 1;
                    Model.getInstance().getListFilterCar(tabChange, page, myLocation
                            , SystemUtils.getAccessToken(MainScreenActivity.this), posSpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetected();
        mShakeDetector.setOnShakeListener(this);

        //set to enable action click
        getBtnShake().setOnClickListener(this);
        getBtnLocation().setOnClickListener(this);
        getTabType().setOnClickListener(this);
        getTabBrand().setOnClickListener(this);
        getTabSeats().setOnClickListener(this);
        getRlShare().setOnClickListener(this);
        getBtnRate().setOnClickListener(this);
        getSwipeLayout().setOnRefreshListener(this);
        getSwipeLayout().setColorSchemeColors
                (getResources().getColor(R.color.colorPrimaryDark));

        carAdapter = new DefaultCarAdapter(this, Model.getInstance().getDefaultCars());
        getDefaultListCar().setAdapter(carAdapter);
        getDefaultListCar().setListener(this);
        getDefaultListCar().setOnItemClickListener(this);
        getDefaultListCar().setLoadingView(R.layout.loading_layout);

        getDefaultListCar().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Model.getInstance().statusOfPassenger == 1) {
                    Driver driver = (Driver) getDefaultListCar().getItemAtPosition(position);
                    getBusyIndicator("Book car ...").show();
                    Model.getInstance().mDriver = driver;
                    Model.getInstance().callApiCheckVacant(SystemUtils.getAccessToken(MainScreenActivity.this), driver.idcar,
                            myLocation, SystemUtils.getIDPassenger(MainScreenActivity.this));
                }
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Intent i = new Intent(MainScreenActivity.this, RatingScreenActivity.class);
                startActivity(i);
            }
        };
    }

    @Override
    public void onClick(View view) {
        drawerLayout.closeDrawers();
        if(!SystemUtils.getStatusLogin(this)){
            finish();
            Intent intent = new Intent(this, LoginScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return;
        }
        switch (view.getId()) {
            case (R.id.iv_location):
                settingGPS();
                mTracker.setScreenName("Image~" + this.getClass());
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Share")
                        .build());
                break;
            case (R.id.iv_shake_phone):
                shakeBookCar();
                break;
            case (R.id.tab_type):
                setSpinnerCar(1);
                setLayoutTab(1);
                break;
            case (R.id.tab_brand):
                setSpinnerCar(2);
                setLayoutTab(2);
                break;
            case (R.id.tab_seats):
                setSpinnerCar(3);
                setLayoutTab(3);
                break;
            case (R.id.rl_share):
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "http://taxiloy.com/?p=731");
                startActivity(Intent.createChooser(share, "Please chose the app to share"));
                break;
            case(R.id.btn_rate):
                //access to this app in google play store
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Driver driver = Model.getInstance().getDefaultCars().get(position);
        Model.getInstance().bookTheRide(SystemUtils.getAccessToken(this),
                myLocation, driver.idcar, SystemUtils.getIDPassenger(this));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
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
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setIcon(R.drawable.icon_list);
        return super.onCreateOptionsMenu(menu);
    }

    //update info
    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.GET_DETAIL_PASSENGER_SUCCESS) {
            getSlidingMenu().showInfo();
        } else if (evt == ModelEvent.GET_CAR_LIST_SUCCESS) {
            setCarListLocation();
            posCarShake = 0;
        } else if (evt == ModelEvent.GET_DEFAULT_LIST_CAR_SUCCESS) {
            getSwipeLayout().setRefreshing(false);
            carAdapter.notifyDataSetChanged();
            getDefaultListCar().addNewData();
        } else if (evt == ModelEvent.GET_FILTER_LIST_CAR_SUCCESS) {
            getSwipeLayout().setRefreshing(false);
            carAdapter.notifyDataSetChanged();
            getDefaultListCar().addNewData();
        } else if (evt == ModelEvent.GET_CARTYPES_SUCCESS) {
            setSpinnerCar(1);
            setLayoutTab(1);
        } else if (evt == ModelEvent.GET_CARBRAND_SUCCESS) {
            //setSpinnerCar(2);
        } else if (evt == ModelEvent.GET_CARSEATS_SUCCESS) {
            //setSpinnerCar(3);
        } else if (evt == ModelEvent.BOOK_CAR_SUCCESS) {
            mShakeDetector.isEnable(true);
            getBusyIndicator("Book car ...").dismiss();
            if (Model.getInstance().mDriver != null) {
                Intent intent = new Intent(this, DialogBookSuccess.class);
                intent.putExtra(QuickstartPreferences.ID_DRIVER, Model.getInstance().mDriver.idcar);
                intent.putExtra(QuickstartPreferences.PHONE_DRIVER, Model.getInstance().mDriver.phonenumber);
                startActivity(intent);
            }
        } else if (evt == ModelEvent.CHECK_VACANT_DRIVER_SUCCESS) {

        } else if (evt == ModelEvent.CHECK_VACANT_DRIVER_FAILED) {
            mShakeDetector.isEnable(true);
            getBusyIndicator("Book car ...").dismiss();
            showErrorMessage(R.string.book_fail, R.string.book_fail_detail, new Runnable() {
                @Override
                public void run() {

                }
            }).show();
        }
    }

    @Override
    public void loadData() {
        page = page++;
        if (!listCarDefault) {
            Model.getInstance().getListFilterCar(tabChange, page, myLocation,
                    SystemUtils.getAccessToken(this), posSpinner);
        } else {
            Model.getInstance().getDefaultListCar(page, SystemUtils.getAccessToken(this), gps.getLocation());
        }
    }

    @Override
    public void onShake() {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        shakeBookCar();
    }

    @Override
    public void onRefresh() {
        getSwipeLayout().setRefreshing(true);
        refreshDefaultListCar();
    }

    private void setLayoutTab(int pos) {
        if (pos == tabChange) {
            return;
        }
        tabChange = pos;
        getTextType().setTextColor(getResources().getColor(R.color.yellowDark));
        getTabType().setBackgroundColor(getResources().getColor(R.color.white));
        getTextBrand().setTextColor(getResources().getColor(R.color.yellowDark));
        getTabBrand().setBackgroundColor(getResources().getColor(R.color.white));
        getTextSeats().setTextColor(getResources().getColor(R.color.yellowDark));
        getTabSeats().setBackgroundColor(getResources().getColor(R.color.white));
        switch (tabChange) {
            case 1:
                getTextType().setTextColor(getResources().getColor(R.color.white_dark));
                getTabType().setBackgroundColor(getResources().getColor(R.color.yellowDark));
                break;
            case 2:
                getTextBrand().setTextColor(getResources().getColor(R.color.white_dark));
                getTabBrand().setBackgroundColor(getResources().getColor(R.color.yellowDark));
                break;
            case 3:
                getTextSeats().setTextColor(getResources().getColor(R.color.white_dark));
                getTabSeats().setBackgroundColor(getResources().getColor(R.color.yellowDark));
                break;
        }
        listCarDefault = true;
    }

    //for passenger
    private void setCarListLocation() {
        fragment.setDriverArrayList(Model.getInstance().getDriverArrayList());
        fragment.setCarsLocation();
    }

    private void refreshDefaultListCar() {
        page = 1;
        Model.getInstance().getDefaultListCar(page, SystemUtils.getAccessToken(this), gps.getLocation());
        Model.getInstance().getAllCarBrand();
        Model.getInstance().getAllCarType();
        Model.getInstance().getAllCarSeats();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Model & getInstance create only one setInApp into the app
        Model.getInstance().setInApp(false);
        mSensorManager.unregisterListener(mShakeDetector);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SystemUtils.getStatusLogin(this)) {
            getTvTitle().setText(getString(R.string.search_for_driver));
        }
        //flag 1, statusOfPassenger == 2, status of passenger after driver pick up
        if (Model.getInstance().statusOfPassenger == 2) {
            Model.getInstance().statusOfPassenger = 3;
            getRlShare().setVisibility(View.VISIBLE);
            getBtnLocation().setVisibility(View.GONE);
            getBtnShake().setVisibility(View.GONE);
            mShakeDetector.isEnable(false);
        } else if (Model.getInstance().statusOfPassenger == 1) {
            //status of passenger after click Cancel
            getRlShare().setVisibility(View.GONE);
            getBtnLocation().setVisibility(View.VISIBLE);
            getBtnShake().setVisibility(View.VISIBLE);
            mShakeDetector.isEnable(true);
        } else if(Model.getInstance().statusOfPassenger == 3){
            getRlShare().setVisibility(View.VISIBLE);
            getBtnLocation().setVisibility(View.GONE);
            getBtnShake().setVisibility(View.GONE);
            mShakeDetector.isEnable(false);
        }
        getSlidingMenu().showInfo();
        settingGPS();
        Model.getInstance().setInApp(true);
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.THE_RIDE_CANCELED));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mMenuDrawer.toggleMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //shake to book car
    private void shakeBookCar() {
        mShakeDetector.isEnable(false);
        if (!SystemUtils.getStatusLogin(this)) {
            finish();
            Intent intent = new Intent(this, LoginScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mShakeDetector.isEnable(true);
            return;
        }
        if (Model.getInstance().getDefaultCars().size() <= posCarShake) {
            if (!isShowNoDriverToShake) {
                showComfirmNoDriverDialog();
            }
            mShakeDetector.isEnable(true);
            return;
        }
        getBusyIndicator("Book car ...").show();
        Model.getInstance().mDriver = Model.getInstance().getDefaultCars().get(posCarShake);
        Model.getInstance().callApiCheckVacant(SystemUtils.getAccessToken(this), Model.getInstance().mDriver.idcar,
                myLocation, SystemUtils.getIDPassenger(this));
        posCarShake++;

    }

    private void settingGPS() {
        gps = new GPSTracker(this);
        //Check data of GPS null or not, if it's null, request gps location
        if (gps.canGetLocation()) {
            if (gps.getLocation() != null) {
                if (fragment.isReady) {
                    fragment.updateLocation(gps.getLocation());
                }
                refreshDefaultListCar();
                myLocation = gps.getLocation();
                Model.getInstance().getListCar(SystemUtils.getAccessToken(this), gps.getLocation());

            }
        } else {
            gps.showSettingsAlert();
        }
    }

    private void setSpinnerCar(int pos) {
        optionArrayStairsToBuilding.clear();
        optionArrayStairsToBuilding.add("--Select one--");
        switch (pos) {
            case 1:
                for (CarType carType : Model.getInstance().getCarTypes()) {
                    optionArrayStairsToBuilding.add(carType.cartypename);
                }
                break;
            case 2:
                for (String carBrand : Model.getInstance().getCarBrands()) {
                    optionArrayStairsToBuilding.add(carBrand);
                }
                break;
            case 3:
                for (String carBrand : Model.getInstance().getCarSeats()) {
                    optionArrayStairsToBuilding.add(carBrand);
                }
                break;
        }
        adapter.notifyDataSetChanged();
        getSpinner().setSelection(0);

    }

    private ImageView getBtnShake() {
        return (ImageView) findViewById(R.id.iv_shake_phone);
    }

    private ImageView getBtnLocation() {
        return (ImageView) findViewById(R.id.iv_location);
    }

    private SwipeRefreshLayout getSwipeLayout() {
        return (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }

    private CustomListView getDefaultListCar() {
        return (CustomListView) findViewById(R.id.list_inbox);
    }

    private Spinner getSpinner() {
        return (Spinner) findViewById(R.id.spin_filter_taxi);
    }

    private RelativeLayout getTabType() {
        return (RelativeLayout) findViewById(R.id.tab_type);
    }

    private TextView getTextType() {
        return (TextView) findViewById(R.id.tv_tab_type);
    }

    private RelativeLayout getTabBrand() {
        return (RelativeLayout) findViewById(R.id.tab_brand);
    }

    private TextView getTextBrand() {
        return (TextView) findViewById(R.id.tv_tab_brand);
    }

    private TextView getTextSeats() {
        return (TextView) findViewById(R.id.tv_tab_seats);
    }

    private RelativeLayout getTabSeats() {
        return (RelativeLayout) findViewById(R.id.tab_seats);
    }

    private SlidingMenu getSlidingMenu() {
        return (SlidingMenu) findViewById(R.id.left_drawer);
    }

    private RelativeLayout getRlShare() {
        return (RelativeLayout) findViewById(R.id.rl_share);
    }

    private TextView getTvTitle() {
        return (TextView) findViewById(R.id.tv_title);
    }

    private Button getBtnRate(){
        return (Button) findViewById(R.id.btn_rate);
    }

    private void showComfirmNoDriverDialog() {
        isShowNoDriverToShake = true;
        final Dialog dialog = new Dialog(MainScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_no_driver_shake);

        Button bt_ok = (Button) dialog.findViewById(R.id.btn_got);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isShowNoDriverToShake = false;
            }
        });


        dialog.show();
    }
}

