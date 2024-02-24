package com.example.prosoft.taxiloy.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.adapter.GenderAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.fragment.FragmentProfileDriverCar;
import com.example.prosoft.taxiloy.ui.fragment.FragmentProfileDriverPersonal;
import com.example.prosoft.taxiloy.ui.fragment.FragmentRegisterCar;
import com.example.prosoft.taxiloy.ui.fragment.FragmentRegisterPersonal;
import com.example.prosoft.taxiloy.ui.objects.CurrentDriver;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.CircleImageView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by prosoft on 1/7/16.
 */
public class RegisterDriverActivity extends BaseActivity implements ObservableScrollViewCallbacks, View.OnClickListener {

    private int statusFragment;
    private CurrentDriver currentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        setLayoutRegisterPersonal();
        getScrollView().setScrollViewCallbacks(this);
        getTabPersonal().setOnClickListener(this);
        getTabCar().setOnClickListener(this);
        getFieldName().setText(Model.getInstance().getCurrentDriver().getName());

        if (Model.getInstance().getCurrentDriver().getActive() == 0) {
            getFieldStatus().setText(getString(R.string.status) + " Pending");
            // Toast.makeText(this, getString(R.string.please_contact), Toast.LENGTH_SHORT);
            showErrorMessage(R.string.please_contact, R.string.ok, new Runnable() {
                @Override
                public void run() {

                }
            }).show();
        } else {
            getFieldStatus().setText(getString(R.string.status) + " Approval");
        }

        //Avatar code
        if (SystemUtils.StringToBitMap(Model.getInstance().getCurrentDriver().getAvatar(), 50, 50) != null) {
            getImgViewAvatar().setImageBitmap(SystemUtils
                    .StringToBitMap(Model.getInstance().getCurrentDriver().getAvatar(), 50, 50));
            Log.i("string_bitmap", Model.getInstance().getCurrentDriver().getAvatar());
        }
    }

    public void setLayoutRegisterPersonal() {
        statusFragment = 0;
        FragmentManager childFragMan = getSupportFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();

        childFragTrans.replace(R.id.FRAGMENT_PLACEHOLDER, FragmentProfileDriverPersonal.getInstance());
        childFragTrans.commit();
        getTvTabPersonal().setTextColor(getResources().getColor(R.color.yellowDark));
        getTvTabCar().setTextColor(getResources().getColor(R.color.wallet_bright_foreground_disabled_holo_light));

    }

    public void setLayoutRegisterCar() {
        statusFragment = 1;
        FragmentManager childFragMan = getSupportFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();

        childFragTrans.replace(R.id.FRAGMENT_PLACEHOLDER, FragmentProfileDriverCar.getInstance());
        childFragTrans.commit();
        getTvTabCar().setTextColor(getResources().getColor(R.color.yellowDark));
        getTvTabPersonal().setTextColor(getResources().getColor(R.color.wallet_bright_foreground_disabled_holo_light));
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        ViewHelper.setTranslationY(getTopView(), scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {


    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        if (evt == ModelEvent.GET_DETAIL_DRIVER_SUCCESS) {
            getFieldName().setText(Model.getInstance().getCurrentDriver().getName());

            if (Model.getInstance().getCurrentDriver().getActive() == 0) {
                getFieldStatus().setText("Status: Pending");
            } else {
                getFieldStatus().setText("Status: Approval");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.tab_personal):
                if (statusFragment == 1) {
                    setLayoutRegisterPersonal();
                }
                break;
            case (R.id.tab_car):
                setLayoutRegisterCar();
                break;
        }
    }


    private RelativeLayout getTopView() {
        return (RelativeLayout) findViewById(R.id.rl_top_view);
    }

    private ObservableScrollView getScrollView() {
        return (ObservableScrollView) findViewById(R.id.scroll);
    }

    private RelativeLayout getTabPersonal() {
        return (RelativeLayout) findViewById(R.id.tab_personal);
    }

    private TextView getFieldName() {
        return (TextView) findViewById(R.id.tv_name);
    }

    private TextView getFieldStatus() {
        return (TextView) findViewById(R.id.tv_status);
    }

    private RelativeLayout getTabCar() {
        return (RelativeLayout) findViewById(R.id.tab_car);
    }

    private TextView getTvTabCar() {
        return (TextView) findViewById(R.id.tv_tab_car);
    }

    private TextView getTvTabPersonal() {
        return (TextView) findViewById(R.id.tv_tab_personal);
    }

    public CurrentDriver getCurrentDriver() {
        if (currentDriver == null)
            return new CurrentDriver();
        return currentDriver;
    }

    private CircleImageView getImgViewAvatar() {
        return (CircleImageView) findViewById(R.id.iv_avatar);
    }

}
