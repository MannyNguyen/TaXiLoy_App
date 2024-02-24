package com.example.prosoft.taxiloy.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.example.prosoft.taxiloy.ui.objects.ReportPassenger;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.NavigationBar;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

/**
 * Created by prosoft on 1/18/16.
 */
public class ReportScreenActivity extends BaseActivity {
    ReportPassenger reportPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_screen);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Su kien click tu btn_report_send
        getBtnSend().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case (R.id.btn_report_send):

                String txtPhone = getEdtTextPhone().getText().toString();
                if (txtPhone.matches("")) {
                    getTxtMissPhone().setVisibility(View.VISIBLE);
                    //Call method Toast custom
                    ReportScreenActivity.showToastMessage(this, getString(R.string.miss_phone_number));
                    return;
                } else {
                    getTxtMissPhone().setVisibility(View.GONE);
                }

                String txtName = getEdtTextName().getText().toString();
                if (txtName.matches("")) {
                    gettxtMissName().setVisibility(View.VISIBLE);
                    ReportScreenActivity.showToastMessage(this, getString(R.string.miss_person_name));
                    return;
                } else {
                    gettxtMissName().setVisibility(View.GONE);
                }

                String txtCode = getEdtTextCode().getText().toString();
                if (txtCode.matches("")) {
                    getTxtMissCode().setVisibility(View.VISIBLE);
                    ReportScreenActivity.showToastMessage(this, getString(R.string.miss_ride_code));
                    return;
                } else {
                    getTxtMissCode().setVisibility(View.GONE);
                }

                String txtContent = getEdtTextContent().getText().toString();
                if (txtContent.matches("")) {
                    getTxtMissContent().setVisibility(View.VISIBLE);
                    ReportScreenActivity.showToastMessage(this, getString(R.string.miss_content));

                    return;
                } else {
                    getTxtMissContent().setVisibility(View.GONE);
                }

                reportPassenger = new ReportPassenger(txtName, txtPhone, txtCode, txtContent);
                Model.getInstance().callApiReportPassenger(SystemUtils.getAccessToken(this),
                        reportPassenger, SystemUtils.getIDPassenger(this));

                //Check wifi connection
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWifi.isConnected()) {
                    ReportScreenActivity.showToastMessage(this, getString(R.string.success));
                    break;
                } else {
                    ReportScreenActivity.showToastMessage(this, getString(R.string.check_wifi));
                    break;
                }
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

    private EditText getEdtTextPhone() {
        return (EditText) findViewById(R.id.edt_phone);
    }

    private TextView getTxtMissPhone() {
        return (TextView) findViewById((R.id.txt_miss_phonenumber));
    }

    private EditText getEdtTextName() {
        return (EditText) findViewById(R.id.edt_name);
    }

    private TextView gettxtMissName() {
        return (TextView) findViewById(R.id.txt_miss_personname);
    }

    private EditText getEdtTextCode() {
        return (EditText) findViewById(R.id.edt_code);
    }

    private TextView getTxtMissCode() {
        return (TextView) findViewById(R.id.txt_missridecode);
    }

    private EditText getEdtTextContent() {
        return (EditText) findViewById(R.id.edt_content);
    }

    private TextView getTxtMissContent() {
        return (TextView) findViewById(R.id.txt_miss_content);
    }

    private Button getBtnSend() {
        return (Button) findViewById(R.id.btn_report_send);
    }

    private static void showToastMessage(Context context, String toastContent) {
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
}
