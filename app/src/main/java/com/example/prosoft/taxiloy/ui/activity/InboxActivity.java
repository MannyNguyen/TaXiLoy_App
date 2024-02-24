package com.example.prosoft.taxiloy.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.adapter.InboxAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.enums.UserAccountTypeEnum;
import com.example.prosoft.taxiloy.ui.objects.MessageInbox;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.CustomListView;
import com.example.prosoft.taxiloy.ui.view.NavigationBar;
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Manh on 1/18/2016.
 */
public class InboxActivity extends BaseActivity implements CustomListView.EndlessListener, SwipeRefreshLayout.OnRefreshListener {

    private InboxAdapter inboxAdapter;
    private CustomListView lvInbox;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;
    private String datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors
                (getResources().getColor(R.color.colorPrimaryDark));
        initViews();

        datetime = SystemUtils.getTime();

    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        if (evt == ModelEvent.GET_INBOX_NO_LIST_PASSENGER) {
            swipeRefreshLayout.setRefreshing(false);
//            Toast.makeText(this, "No Message Inbox", Toast.LENGTH_SHORT).show();
            InboxActivity.showToastMessage(this, "No Message Inbox");
            lvInbox.addNewData();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            inboxAdapter.notifyDataSetChanged();
            lvInbox.addNewData();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {

        getEtSearch().setInputType(InputType.TYPE_NULL);
        getEtSearch().setOnClickListener(this);
        getBtSearch().setOnClickListener(this);

        lvInbox = (CustomListView) findViewById(R.id.list_inbox);

        inboxAdapter = new InboxAdapter(this, Model.getInstance().getMessageInbox());
        lvInbox.setAdapter(inboxAdapter);
        lvInbox.setListener(this);
        lvInbox.setLoadingView(R.layout.loading_layout);
        refreshDefaultListCar();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case (R.id.et_time_search):
                setDateText(getEtSearch());
                showDialog(DATE_PICKER_ID);
                break;
            case (R.id.iv_search):
                datetime = getEtSearch().getText().toString();
                if (datetime != "") {
                    refreshDefaultListCar();
                }
                break;
        }
    }

    @Override
    public void loadData() {
        page = page + 1;
        if (UserAccountTypeEnum.values()[0].getType()
                .equalsIgnoreCase(SystemUtils.getUserType(this))) {
            Model.getInstance().getListInboxPassenger(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDPassenger(this), page, datetime);
        } else {
            Model.getInstance().getListInboxDriver(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDCar(this), page, datetime);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshDefaultListCar();
    }

    private void refreshDefaultListCar() {
        page = 1;
        if (UserAccountTypeEnum.values()[0].getType()
                .equalsIgnoreCase(SystemUtils.getUserType(this))) {
            Model.getInstance().getListInboxPassenger(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDPassenger(this), page, datetime);
        } else {
            Model.getInstance().getListInboxDriver(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDCar(this), page, datetime);
        }
        ;
    }

    public static void showToastMessage(Context context, String toastContent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_report,
                (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_report));
        TextView txtToast = (TextView) layout.findViewById(R.id.txt_report_toast);
        txtToast.setText(toastContent);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }

    private RelativeLayout getViewSearch() {
        return (RelativeLayout) findViewById(R.id.rl_view_search);
    }

    private EditText getEtSearch() {
        return (EditText) findViewById(R.id.et_time_search);
    }

    private ImageView getBtSearch() {
        return (ImageView) findViewById(R.id.iv_search);
    }
}
