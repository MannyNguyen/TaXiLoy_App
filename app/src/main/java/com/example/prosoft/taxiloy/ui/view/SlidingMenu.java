package com.example.prosoft.taxiloy.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.activity.BaseActivity;
import com.example.prosoft.taxiloy.ui.activity.InboxActivity;
import com.example.prosoft.taxiloy.ui.activity.LoginScreenActivity;
import com.example.prosoft.taxiloy.ui.activity.MainScreenActivity;
import com.example.prosoft.taxiloy.ui.activity.PassengerProfileActivity;
import com.example.prosoft.taxiloy.ui.activity.RegisterDriverActivity;
import com.example.prosoft.taxiloy.ui.activity.SupportScreenActivity;
import com.example.prosoft.taxiloy.ui.enums.UserAccountTypeEnum;
import com.example.prosoft.taxiloy.ui.objects.CurrentDriver;
import com.example.prosoft.taxiloy.ui.objects.CurrentPassenger;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.activity.ActivityHistoryList;
import com.example.prosoft.taxiloy.ui.activity.PassengerProfileActivity;
import com.example.prosoft.taxiloy.ui.activity.ReportScreenActivity;

import java.util.Locale;

/**
 * Created by prosoft on 12/24/15.
 */
public class SlidingMenu extends FrameLayout implements View.OnClickListener {

    RelativeLayout rl_account_field;
    RelativeLayout rl_inbox_field;
    RelativeLayout rl_history_field;
    RelativeLayout rl_suggest_field;
    RelativeLayout rl_report_field;
    RelativeLayout rl_support_field;
    RelativeLayout rl_share_field;
    RelativeLayout rl_language_field;
    TextView tv_name;
    CircleImageView iv_avatar;
    BaseActivity context;
    ImageView iv_nation_cam, iv_nation_us;
    DrawerLayout drawerLayout;

    public SlidingMenu(Context context) {
        super(context);
        this.context = (BaseActivity) context;
        loadViews();
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = (BaseActivity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.slidingmenu_left_list, this);

        loadViews();
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    private void loadViews() {
        rl_account_field = (RelativeLayout) findViewById(R.id.rl_account_field);
        rl_inbox_field = (RelativeLayout) findViewById(R.id.rl_inbox_field);
        rl_history_field = (RelativeLayout) findViewById(R.id.rl_history_field);
        rl_suggest_field = (RelativeLayout) findViewById(R.id.rl_suggest_field);
        rl_report_field = (RelativeLayout) findViewById(R.id.rl_report_field);
        rl_support_field = (RelativeLayout) findViewById(R.id.rl_support_field);
        rl_share_field = (RelativeLayout) findViewById(R.id.rl_share_field);
        rl_language_field = (RelativeLayout) findViewById(R.id.rl_language_field);
        iv_nation_cam = (ImageView) findViewById(R.id.flag_nation);
        iv_nation_us = (ImageView) findViewById(R.id.flag_nation_us);

        tv_name = (TextView) findViewById(R.id.tv_name);
        iv_avatar = (CircleImageView) findViewById(R.id.iv_avatar);

        rl_account_field.setOnClickListener(this);
        rl_inbox_field.setOnClickListener(this);
        rl_history_field.setOnClickListener(this);
        rl_report_field.setOnClickListener(this);
        rl_support_field.setOnClickListener(this);
        rl_share_field.setOnClickListener(this);
        rl_language_field.setOnClickListener(this);
        rl_suggest_field.setOnClickListener(this);

        //ADD 2 FLAG: CAMBODIA AND AMERICA
        if (SystemUtils.getLanguage(context) == 1) {
            iv_nation_cam.setVisibility(View.VISIBLE);
            iv_nation_us.setVisibility(View.INVISIBLE);
        } else {
            iv_nation_us.setVisibility(View.VISIBLE);
            iv_nation_cam.setVisibility(View.INVISIBLE);
        }
    }

    public void showInfo() {
        if (UserAccountTypeEnum.values()[0].getType()
                .equalsIgnoreCase(SystemUtils.getUserType(context))) {
            CurrentPassenger currentPassenger = Model.getInstance().getCurrentPassenger();
            tv_name.setText(currentPassenger.getName());
            if (SystemUtils.StringToBitMap(currentPassenger.getAvatar(), 50, 50) != null) {
                iv_avatar.setImageBitmap(SystemUtils
                        .StringToBitMap(currentPassenger.getAvatar(), 50, 50));
            }
        } else {
            CurrentDriver currentDriver = Model.getInstance().getCurrentDriver();
            tv_name.setText(currentDriver.getName());
            if (SystemUtils.StringToBitMap(currentDriver.getAvatar(), 50, 50) != null) {
                iv_avatar.setImageBitmap(SystemUtils
                        .StringToBitMap(currentDriver.getAvatar(), 50, 50));
            }
        }
    }

    @Override
    public void onClick(View view) {
        drawerLayout.closeDrawer(this);
        if(!SystemUtils.getStatusLogin(context)){
            context.finish();
            Intent intent = new Intent(context, LoginScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return;
        }
        switch (view.getId()) {
            case (R.id.rl_account_field):
                if (UserAccountTypeEnum.values()[0].getType()
                        .equalsIgnoreCase(SystemUtils.getUserType(context))) {
                    Intent intent = new Intent(context, PassengerProfileActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, RegisterDriverActivity.class);
                    context.startActivity(intent);
                }
                break;
            case (R.id.rl_inbox_field):
                Intent intent1 = new Intent(context, InboxActivity.class);
                context.startActivity(intent1);
                break;
            case (R.id.rl_history_field):
                Intent intent2 = new Intent(context, ActivityHistoryList.class);
                context.startActivity(intent2);
                break;
            case (R.id.rl_suggest_field):
                Toast.makeText(context, context.getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;
            case (R.id.rl_report_field):
                Intent intent3 = new Intent(context, ReportScreenActivity.class);
                context.startActivity(intent3);
                break;
            case (R.id.rl_support_field):
                Intent intent4 = new Intent(context, SupportScreenActivity.class);
                context.startActivity(intent4);

                break;
            case (R.id.rl_share_field):
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "http://taxiloy.com/?p=731");
                context.startActivity(Intent.createChooser(share, "Please chose the app to share"));
                break;
            case (R.id.rl_language_field):
                if (SystemUtils.getLanguage(context) == 1) {
                    Intent intent5 = new Intent(context, LoginScreenActivity.class);
                    intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent5);
                    context.finish();
                    SystemUtils.saveLanguage(2, context);
                } else {
                    context.finish();
                    Intent intent5 = new Intent(context, LoginScreenActivity.class);
                    intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent5);
                    context.finish();
                    SystemUtils.saveLanguage(1, context);
                }
                break;

        }
    }
}
