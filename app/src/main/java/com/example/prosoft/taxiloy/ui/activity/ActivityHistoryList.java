package com.example.prosoft.taxiloy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.adapter.HistoryAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.enums.UserAccountTypeEnum;
import com.example.prosoft.taxiloy.ui.objects.HistoryList;
import com.example.prosoft.taxiloy.ui.objects.MessageInbox;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manh on 1/19/2016.
 */
public class ActivityHistoryList extends BaseActivity implements CustomListView.EndlessListener, SwipeRefreshLayout.OnRefreshListener {

    private HistoryAdapter historyAdapter;
    private CustomListView lvHistory;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;
    private String date = "";
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_screen);

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

        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.bt_date):
                setDateText(getBtDate());
                showDialog(DATE_PICKER_ID);
                break;
            case (R.id.btn_apply_fil):
                date = getBtDate().getText().toString();
                name = getBtName().getText().toString();
                View v = this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                refreshDefaultListCar();
                break;

        }
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        if (evt == ModelEvent.GET_HISTORY_NO_LIST_PASSENGER) {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, "No History Transaction", Toast.LENGTH_SHORT).show();
            lvHistory.addNewData();
        } else {
            getTvResult().setText(String.valueOf(Model.getInstance().totalItem));
            swipeRefreshLayout.setRefreshing(false);
            historyAdapter.notifyDataSetChanged();
            lvHistory.addNewData();
        }
    }

    private void initView() {

        getBtDate().setOnClickListener(this);
        getBtApply().setOnClickListener(this);
        getBtDate().setInputType(InputType.TYPE_NULL);
        if (UserAccountTypeEnum.values()[0].getType()
                .equalsIgnoreCase(SystemUtils.getUserType(this))) {
            getBtName().setHint(R.string.passenger_name);
        } else {
            getBtName().setHint(R.string.driver_name);
        }

        lvHistory = (CustomListView) findViewById(R.id.lst_his);
        historyAdapter = new HistoryAdapter(this, Model.getInstance().getMessageHistory());
        lvHistory.setAdapter(historyAdapter);
        lvHistory.setListener(this);
        lvHistory.setLoadingView(R.layout.loading_layout);
        refreshDefaultListCar();

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

    @Override
    public void loadData() {
        page = page + 1;
        if (UserAccountTypeEnum.values()[0].getType()
                .equalsIgnoreCase(SystemUtils.getUserType(this))) {
            Model.getInstance().getHistoryPassenger(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDPassenger(this), page, name, date);
        } else {
            Model.getInstance().getHistoryDriver(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDCar(this), page, name, date);
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
            Model.getInstance().getHistoryPassenger(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDPassenger(this), page, name, date);
        } else {
            Model.getInstance().getHistoryDriver(SystemUtils.getAccessToken(this),
                    SystemUtils.getIDCar(this), page, name, date);
        }

    }

    private EditText getBtDate() {
        return (EditText) findViewById(R.id.bt_date);
    }

    private EditText getBtName() {
        return (EditText) findViewById(R.id.bt_name);
    }

    private Button getBtApply() {
        return (Button) findViewById(R.id.btn_apply_fil);
    }

    private TextView getTvResult() {
        return (TextView) findViewById(R.id.txt_result);
    }

}
