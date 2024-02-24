package com.example.prosoft.taxiloy.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.adapter.GenderAdapter;
import com.example.prosoft.taxiloy.ui.adapter.NationAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.enums.RequestCode;
import com.example.prosoft.taxiloy.ui.enums.UserAccountTypeEnum;
import com.example.prosoft.taxiloy.ui.objects.CurrentPassenger;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.SliderDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by prosoft on 12/16/15.
 */
public class LoginScreenActivity extends BaseActivity {

    private GenderAdapter adapter;
    private NationAdapter nationAdapter;
    private String[] userGender;
    private String[] nationName;
    private String[] nationCode;
    private int gender, nation;
    private String userType;
    private static boolean isAlreadyLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        //Change app language
        if (SystemUtils.getLanguage(this) == 2) {
            Locale locale = new Locale("kh");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        } else {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }

        if (SystemUtils.getStatusLogin(this)) {
            if (UserAccountTypeEnum.values()[0].getType()
                    .equalsIgnoreCase(SystemUtils.getUserType(this))) {
                Intent intent = new Intent(this, MainScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, SliderScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            finish();
        }

        initViews();
    }

    private void initViews() {

        userGender = getResources().getStringArray(R.array.gender);
        ArrayList<String> optionArrayStairsToBuilding = new ArrayList<>();
        Collections.addAll(optionArrayStairsToBuilding, userGender);
        adapter = new GenderAdapter(this, R.layout.cell_spinner_gender,
                optionArrayStairsToBuilding, getResources());
        getSpinGender().setAdapter(adapter);

        nationName = getResources().getStringArray(R.array.nation_name);
        ArrayList<String> arrayNationName = new ArrayList<>();
        Collections.addAll(arrayNationName, nationName);
        nationAdapter = new NationAdapter(this, R.layout.cell_spinner_gender,
                arrayNationName, getResources());
        getSpinNation().setAdapter(nationAdapter);

        getButtonConfirm().setOnClickListener(this);
        getButtonCancel().setOnClickListener(this);
        getOptionDriver().setOnClickListener(this);
        getOptionPassenger().setOnClickListener(this);
        userType = UserAccountTypeEnum.values()[0].getType();

        getSpinGender().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initializedAdapter != parent.getAdapter()) {
                    initializedAdapter = parent.getAdapter();
                    return;
                }
                gender = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getSpinNation().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initializedAdapter != parent.getAdapter()) {
                    initializedAdapter = parent.getAdapter();
                    return;
                }
                nation = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.bt_confirm_login):
                validateLogin();
                break;
            case (R.id.bt_cancel_login):
                Intent intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
                break;
            case (R.id.rl_option_passenger):
                userType = UserAccountTypeEnum.values()[0].getType();
                getTickOptionDriver().setVisibility(View.GONE);
                getTickOptionPassenger().setVisibility(View.VISIBLE);
                break;
            case (R.id.rl_option_driver):
                getTickOptionPassenger().setVisibility(View.GONE);
                getTickOptionDriver().setVisibility(View.VISIBLE);
                userType = UserAccountTypeEnum.values()[1].getType();
                break;
        }
    }

    private void onUserLogin() {
        getBusyIndicator().dismiss();
        Intent intent = new Intent(this, ConfirmSMSCode.class);
        intent.putExtra("userType", userType);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_1.getCode());
    }

    private void goDriverSignup() {
        getBusyIndicator().dismiss();
        Intent intent = new Intent(LoginScreenActivity.this, RegisterDriverActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            SystemUtils.saveStatusLogin(true, this);
            SystemUtils.saveUserType(userType, this);
            SystemUtils.saveAccessToken(Model.getInstance().getAccessToken(), this);
            if (userType.equalsIgnoreCase(UserAccountTypeEnum.values()[0].getType())) {
                SystemUtils.saveIDPassenger(Model.getInstance().getCurrentPassenger().getIdPassenger(), this);
                Intent intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
            } else {
                SystemUtils.saveIDCar(Model.getInstance().getCurrentDriver().getIdcar(), this);
                Intent intent = new Intent(this, SliderScreenActivity.class);
                startActivity(intent);

            }
            finish();
        }

    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.GETSMS_SUCCESS) {
            onUserLogin();
            isAlreadyLogin = false;
        } else if (evt == ModelEvent.GETSMS_ALREADY) {
            onUserLogin();
            isAlreadyLogin = true;
        }
    }

    private void validateLogin() {

        if (SystemUtils.isEmpty(getUserPhone())) {
            getTextErrorPhone().setVisibility(View.VISIBLE);
            getImgErrorPhone().setVisibility(View.VISIBLE);
            return;
        } else {
            String phone = getUserPhone().getText().toString();
            if ((phone.length() != 8 && phone.length() != 9 && phone.length() != 10) || phone.startsWith("0")) {
                Toast.makeText(this, "Please check your phone again", Toast.LENGTH_SHORT).show();
                return;
            } else {

            }
        }
        getTextErrorPhone().setVisibility(View.INVISIBLE);
        getImgErrorPhone().setVisibility(View.INVISIBLE);
        String phone = getUserPhone().getText().toString();
        if (SystemUtils.isEmpty(getUserName())) {
            getImgErrorName().setVisibility(View.VISIBLE);
            getTextErrorName().setVisibility(View.VISIBLE);
            return;
        }

        if (userType == null) {
            Toast.makeText(this, "Please check your account type", Toast.LENGTH_SHORT).show();
            return;
        }

        getImgErrorName().setVisibility(View.INVISIBLE);
        getTextErrorName().setVisibility(View.INVISIBLE);
        String username = getUserName().getText().toString();

        nationCode = getResources().getStringArray(R.array.nation_code);
        CurrentPassenger passenger = new CurrentPassenger();
        passenger.setName(username);
        passenger.setPhone(nationCode[nation] + phone);

        if (gender == 0) {
            passenger.setGender("M");
        } else {
            passenger.setGender("F");
        }

        getBusyIndicator().show();

        //goi API response tu Model.java
        if (userType.equalsIgnoreCase(UserAccountTypeEnum.values()[0].getType())) {
            Model.getInstance().callAPILoginPasserger(passenger, SystemUtils.getDeviceID(this),
                    SystemUtils.getDeviceToken(this));
        } else {
            Model.getInstance().callAPILoginDriver(passenger, SystemUtils.getDeviceID(this),
                    SystemUtils.getDeviceToken(this));
        }


    }

    private EditText getUserPhone() {
        return (EditText) findViewById(R.id.et_phone_num);
    }

    private EditText getUserName() {
        return (EditText) findViewById(R.id.et_user_name);
    }

    private ImageView getImgErrorPhone() {
        return (ImageView) findViewById(R.id.iv_error_phone_num);
    }

    private ImageView getImgErrorName() {
        return (ImageView) findViewById(R.id.iv_error_user_name);
    }

    private TextView getTextErrorPhone() {
        return (TextView) findViewById(R.id.tv_error_phone_num);
    }

    private TextView getTextErrorName() {
        return (TextView) findViewById(R.id.tv_error_user_name);
    }

    private Spinner getSpinGender() {
        return (Spinner) findViewById(R.id.spin_user_gender);
    }

    private RelativeLayout getOptionPassenger() {
        return (RelativeLayout) findViewById(R.id.rl_option_passenger);
    }

    private RelativeLayout getOptionDriver() {
        return (RelativeLayout) findViewById(R.id.rl_option_driver);
    }

    private ImageView getTickOptionDriver() {
        return (ImageView) findViewById(R.id.iv_ticker_driver);
    }

    private ImageView getTickOptionPassenger() {
        return (ImageView) findViewById(R.id.iv_ticker_passenger);
    }

    private Button getButtonConfirm() {
        return (Button) findViewById(R.id.bt_confirm_login);
    }

    private Button getButtonCancel() {
        return (Button) findViewById(R.id.bt_cancel_login);
    }

    private Spinner getSpinNation() {
        return (Spinner) findViewById(R.id.spin_nation_code);
    }
}
