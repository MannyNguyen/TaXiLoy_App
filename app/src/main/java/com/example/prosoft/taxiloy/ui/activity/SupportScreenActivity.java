package com.example.prosoft.taxiloy.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.objects.SupportPassenger;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.NavigationBar;

/**
 * Created by Manh on 2/4/2016.
 */
public class SupportScreenActivity extends BaseActivity {
    SupportPassenger supportPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        getBtnSend().setOnClickListener(this);
        getBtnCall().setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btn_sp_send):

                String txtTitle = getEdtTextTitle().getText().toString();
                if (txtTitle.matches("")) {
                    //Call method of miss title
                    getTxtMissTitle().setVisibility(View.VISIBLE);
                    //Call method Toast custom
                    SupportScreenActivity.showSupportToast(this, getString(R.string.miss_title));
                    return;
                } else {
                    getTxtMissTitle().setVisibility(View.GONE);
                }

                String txtContent = getEdtTextContent().getText().toString();

                if (txtContent.matches("")) {
                    getTxtMissContent().setVisibility(View.VISIBLE);
                    SupportScreenActivity.showSupportToast(this, getString(R.string.miss_content));
                    return;
                } else {
                    getTxtMissContent().setVisibility(View.GONE);
                }

                supportPassenger = new SupportPassenger(txtTitle, txtContent);
                Model.getInstance().callApiSupportPassenger(SystemUtils.getAccessToken(this),
                        supportPassenger, SystemUtils.getIDPassenger(this));

                //Check wifi connection
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (nWifi.isConnected()) {
                    SupportScreenActivity.showSupportToast(this, getString(R.string.success));
                } else {
                    SupportScreenActivity.showSupportToast(this, getString(R.string.check_wifi));
                }

                break;

            case (R.id.btn_sp_call):
                Intent intent = new Intent(Intent.ACTION_CALL);
                //Add "tel:" before number phone
                intent.setData(Uri.parse("tel:1080"));
                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    SupportScreenActivity.showSupportToast(this, getString(R.string.toast_call));
                }

                break;
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

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private EditText getEdtTextTitle() {
        return (EditText) findViewById(R.id.edt_sp_title);
    }

    private TextView getTxtMissTitle(){
        return (TextView) findViewById(R.id.txt_miss_title);
    }

    private EditText getEdtTextContent() {
        return (EditText) findViewById(R.id.edt_sp_content);
    }

    private TextView getTxtMissContent(){
        return (TextView) findViewById(R.id.txt_miss_content);
    }

    private Button getBtnSend() {
        return (Button) findViewById(R.id.btn_sp_send);
    }

    private Button getBtnCall() {
        return (Button) findViewById(R.id.btn_sp_call);
    }

    //Custom the Toast, need context and Activity
    private static void showSupportToast(Context context, String toastContent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_report,
                (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_report));
        TextView txtToast = (TextView) layout.findViewById(R.id.txt_report_toast);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        txtToast.setText(toastContent);
        toast.show();
    }
}
