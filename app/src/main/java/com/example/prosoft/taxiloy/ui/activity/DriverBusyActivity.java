package com.example.prosoft.taxiloy.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.example.prosoft.taxiloy.R;

/**
 * Created by Manh on 3/1/2016.
 */
public class DriverBusyActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_driver_busy);
    }

    Button btnGotIt = (Button) findViewById(R.id.btn_got);
}
