package com.example.prosoft.taxiloy.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.gcm.QuickstartPreferences;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;

/**
 * Created by prosoft on 2/25/16.
 */
public class RatingScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_driver_screen);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Model.getInstance().statusOfPassenger = 1;

        getStar1().setOnClickListener(this);
        getStar2().setOnClickListener(this);
        getStar3().setOnClickListener(this);
        getStar4().setOnClickListener(this);
        getStar5().setOnClickListener(this);
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
    public void onClick(View view) {
        super.onClick(view);
        int rating = 0;
        switch (view.getId()) {
            case (R.id.imb_star_1):
                getStar1().setBackgroundResource(R.drawable.icon_star);
                rating = 1;
                break;
            case (R.id.imb_star_2):
                getStar1().setBackgroundResource(R.drawable.icon_star);
                getStar2().setBackgroundResource(R.drawable.icon_star);
                rating = 2;
                break;
            case (R.id.imb_star_3):
                getStar1().setBackgroundResource(R.drawable.icon_star);
                getStar3().setBackgroundResource(R.drawable.icon_star);
                getStar2().setBackgroundResource(R.drawable.icon_star);
                rating = 3;
                break;
            case (R.id.imb_star_4):
                getStar1().setBackgroundResource(R.drawable.icon_star);
                getStar3().setBackgroundResource(R.drawable.icon_star);
                getStar2().setBackgroundResource(R.drawable.icon_star);
                getStar4().setBackgroundResource(R.drawable.icon_star);
                rating = 4;
                break;
            case (R.id.imb_star_5):
                getStar1().setBackgroundResource(R.drawable.icon_star);
                getStar3().setBackgroundResource(R.drawable.icon_star);
                getStar2().setBackgroundResource(R.drawable.icon_star);
                getStar4().setBackgroundResource(R.drawable.icon_star);
                getStar5().setBackgroundResource(R.drawable.icon_star);
                rating = 5;
                break;
        }
        getBusyIndicator("Rating ...").show();
        Model.getInstance().ratingDriver(SystemUtils.getAccessToken(this), Model.getInstance().mDriver.idcar,
                SystemUtils.getIDPassenger(this), rating);

    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        getBusyIndicator("Rating ...").dismiss();
        if (evt == ModelEvent.RATING_DRIVER_SUCCESS) {
            finish();
        } else if (evt == ModelEvent.RATING_DRIVER_FAIL) {
            showErrorMessage(R.string.rating_fail, R.string.ok, new Runnable() {
                @Override
                public void run() {

                }
            }).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private TextView getTextViewPoint() {
        return (TextView) findViewById(R.id.tv_point);
    }

    private ImageButton getStar1() {
        return (ImageButton) findViewById(R.id.imb_star_1);
    }

    private ImageButton getStar2() {
        return (ImageButton) findViewById(R.id.imb_star_2);
    }

    private ImageButton getStar3() {
        return (ImageButton) findViewById(R.id.imb_star_3);
    }

    private ImageButton getStar4() {
        return (ImageButton) findViewById(R.id.imb_star_4);
    }

    private ImageButton getStar5() {
        return (ImageButton) findViewById(R.id.imb_star_5);
    }
}
