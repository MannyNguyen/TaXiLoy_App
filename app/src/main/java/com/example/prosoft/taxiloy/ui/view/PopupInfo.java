package com.example.prosoft.taxiloy.ui.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.activity.DialogBookSuccess;
import com.example.prosoft.taxiloy.ui.gcm.QuickstartPreferences;
import com.example.prosoft.taxiloy.ui.objects.Driver;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by prosoft on 1/19/16.
 */
public class PopupInfo extends FrameLayout {

    Context context;
    private TextView tvName;
    private TextView tvDistance;
    private TextView tvPlate;
    private TextView tvBrand;
    private TextView tvModel;
    private TextView tvYear;
    private TextView tvSeat;
    private TextView tvType;
    private Button bt_book;
    private CircleImageView avatar;

    public PopupInfo(Context context, Driver driver, OnClickListener onClickListener, Location myLocation, ProgressDialog dialog) {
        super(context);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dialog_information_driver, this);

        loadViews();
        setUI(driver, onClickListener, myLocation, dialog);
    }

    public PopupInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dialog_information_driver, this);

        loadViews();
    }

    private void loadViews() {
        tvName = (TextView) findViewById(R.id.txt_info_name);
        bt_book = (Button) findViewById(R.id.btn_book_ride);
        tvDistance = (TextView) findViewById(R.id.txt_distan);
        tvPlate = (TextView) findViewById(R.id.txt_plate);
        tvBrand = (TextView) findViewById(R.id.txt_brand_car);
        tvModel = (TextView) findViewById(R.id.txt_model_car);
        tvYear = (TextView) findViewById(R.id.txt_year);
        tvSeat = (TextView) findViewById(R.id.txt_num_seat);
        tvType = (TextView) findViewById(R.id.txt_type_car);
        avatar = (CircleImageView)findViewById(R.id.iv_avatar);

    }

    private void setUI(final Driver driver, OnClickListener onClickListener, final Location myLocation, final ProgressDialog dialog) {
        bt_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        bt_book.setOnTouchListener(new OnInfoWindowElemTouchListener(bt_book) {
            //Call driver
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                if (Model.getInstance().statusOfPassenger == 1) {
                    Model.getInstance().callApiCheckVacant(SystemUtils.getAccessToken(context), driver.idcar,
                            myLocation, SystemUtils.getIDPassenger(context));
                    dialog.show();
                    Model.getInstance().mDriver = driver;
                }
            }

        });

        tvName.setText(driver.name);
        tvBrand.setText(driver.carbrand);
        tvModel.setText(driver.carmodel);
        tvYear.setText(driver.year);
        tvSeat.setText(driver.carseats);
        tvType.setText(String.valueOf(driver.idcartype));
        if (SystemUtils.StringToBitMap(driver.avatar, 50, 50) != null) {
            avatar.setImageBitmap(SystemUtils.StringToBitMap(driver.avatar, 50, 50));
        }
    }

}
