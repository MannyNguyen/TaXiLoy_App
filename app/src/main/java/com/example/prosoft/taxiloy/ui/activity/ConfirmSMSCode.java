package com.example.prosoft.taxiloy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.enums.UserAccountTypeEnum;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;

import org.w3c.dom.Text;

/**
 * Created by prosoft on 12/17/15.
 */
public class ConfirmSMSCode extends BaseActivity {

    private final int TOTALTIME = 120 * 1000;
    private final int MAXLENGTH = 6;
    private CounterClass counterClass;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_code_sms);
        this.setFinishOnTouchOutside(false);

        initViews();
    }

    private void initViews() {
        userType = getIntent().getStringExtra("userType");
        counterClass = new CounterClass(TOTALTIME, 1000);
        counterClass.start();

        getUserCode().setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAXLENGTH)});
        getBt_Ok().setOnClickListener(this);
        getBt_Cancel().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.bt_ok):
                checkSMSCode();
                break;
            case (R.id.bt_cancel):
                finish();
                break;

        }
    }

    private void checkSMSCode() {
        if (getUserCode().getText().toString().trim().length() < 6) {
            Toast.makeText(this, getString(R.string.error_fill_6), Toast.LENGTH_SHORT).show();
            return;
        }
        getBusyIndicator().show();
        if (userType.equalsIgnoreCase(UserAccountTypeEnum.values()[0].getType())) {
            Model.getInstance().checkSMSCode(getUserCode().getText().toString());
        } else {
            Model.getInstance().checkSMSCodeDriver(getUserCode().getText().toString());
        }
    }

    private void checkSMSSuccess() {
        counterClass.cancel();
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        if (evt == ModelEvent.CHECK_SMS_CODE_SUCCESS) {
            getBusyIndicator().dismiss();
            checkSMSSuccess();
        } else {
            getBusyIndicator().dismiss();
            Toast.makeText(this, getString(R.string.error_fill_6), Toast.LENGTH_SHORT).show();
        }
    }

    private EditText getUserCode() {
        return (EditText) findViewById(R.id.et_code_field);
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        counterClass.cancel();
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        // prevent back
    }

    private TextView getTime() {
        return (TextView) findViewById(R.id.tv_time);
    }

    private RelativeLayout getBt_Ok() {
        return (RelativeLayout) findViewById(R.id.bt_ok);
    }

    private RelativeLayout getBt_Cancel() {
        return (RelativeLayout) findViewById(R.id.bt_cancel);
    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getTime().setText("" + millisUntilFinished / 1000);
        }
    }


}
