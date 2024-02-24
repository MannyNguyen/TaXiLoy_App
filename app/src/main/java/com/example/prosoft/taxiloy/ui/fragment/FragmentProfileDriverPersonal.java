package com.example.prosoft.taxiloy.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.activity.PassengerProfileActivity;
import com.example.prosoft.taxiloy.ui.activity.RegisterDriverActivity;
import com.example.prosoft.taxiloy.ui.adapter.GenderAdapter;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.objects.CurrentDriver;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by prosoft on 1/7/16.
 */
public class FragmentProfileDriverPersonal extends BaseFragment implements View.OnClickListener {

    public static FragmentProfileDriverPersonal _instance;
    private int gender;
    private GenderAdapter adapter;
    private RegisterDriverActivity mActivity;
    private String[] userGender;

    public static FragmentProfileDriverPersonal getInstance() {
        if (_instance == null) {
            _instance = new FragmentProfileDriverPersonal();
        }
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_register_personal_pending_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userGender = getResources().getStringArray(R.array.gender);
        ArrayList<String> optionArrayStairsToBuilding = new ArrayList<>();
        Collections.addAll(optionArrayStairsToBuilding, userGender);
        adapter = new GenderAdapter(getActivity(), R.layout.cell_spinner_gender_1,
                optionArrayStairsToBuilding, getResources());
        getSpinnerGender().setAdapter(adapter);
        getSpinnerGender().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        initViews();
    }

    private void initViews() {
        getFieldBirth().setOnClickListener(this);
        getBtnNext().setOnClickListener(this);
        getFieldBirth().setInputType(InputType.TYPE_NULL);

        if (SystemUtils.getIDCar(getActivity()) != 0) {
            showInfo();
        }
        disableFields();
//        if (Model.getInstance().getCurrentDriver().getActive() == 1) {
//            disableFields();
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (RegisterDriverActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.et_field_birth):
                if (Model.getInstance().getCurrentDriver().getActive() == 0) {
                    setDateText(getFieldBirth());
                    onCreateDialog(DATE_PICKER_ID).show();
                }
                break;
            case (R.id.btn_driver_submit):
                actionNext();
                break;
        }
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);
        if (evt == ModelEvent.GET_DETAIL_DRIVER_SUCCESS) {
            showInfo();
        } else {

        }
    }

    private void actionNext() {
        mActivity.setLayoutRegisterCar();
    }

    private void actionSummit() {
        CurrentDriver currentDriver = new CurrentDriver();
        currentDriver.setPhonenumber(getFieldPhone().getText().toString());
        currentDriver.setEmail(getFieldMail().getText().toString());
        if (gender == 0) {
            currentDriver.setGender("M");
        } else {
            currentDriver.setGender("F");
        }
        currentDriver.setBirthday(getFieldBirth().getText().toString());
        currentDriver.setDriverliscense(getFieldLicense().getText().toString());
        currentDriver.setIdcard(getFieldCard().getText().toString());

        mActivity.getBusyIndicator("Saving...").show();
        Model.getInstance().putUpdateDriver(SystemUtils.getAccessToken(getActivity()),
                currentDriver, SystemUtils.getDeviceID(getActivity()));
    }

    private void disableFields() {
        getFieldPhone().setInputType(InputType.TYPE_NULL);
        getFieldMail().setInputType(InputType.TYPE_NULL);
        getFieldGender().setInputType(InputType.TYPE_NULL);
        getFieldBirth().setInputType(InputType.TYPE_NULL);
        getFieldLicense().setInputType(InputType.TYPE_NULL);
        getFieldCard().setInputType(InputType.TYPE_NULL);
        getSpinnerGender().setVisibility(View.GONE);
        getImageArrowDown().setVisibility(View.GONE);
        getFieldGender().setVisibility(View.VISIBLE);
        String gender = ("M".equals(Model.getInstance().getCurrentDriver().getGender())) ?
                getResources().getStringArray(R.array.gender)[0]
                : getResources().getStringArray(R.array.gender)[1];
        getFieldGender().setText(gender);
        getIvEdtPhone().setVisibility(View.GONE);
        getIvEdtEmail().setVisibility(View.GONE);
        getIvEdtBirth().setVisibility(View.GONE);
        getIvEdtLisence().setVisibility(View.GONE);
        getIvEdtCard().setVisibility(View.GONE);
        getBtnNext().setText(getResources().getString(R.string.next));
    }

    private void showInfo() {
        getFieldPhone().setText(Model.getInstance().getCurrentDriver().getPhonenumber());
        getFieldMail().setText(Model.getInstance().getCurrentDriver().getEmail());
        getFieldBirth().setText(Model.getInstance().getCurrentDriver().getBirthday());
        getFieldLicense().setText(Model.getInstance().getCurrentDriver().getDriverliscense());
        getFieldCard().setText(Model.getInstance().getCurrentDriver().getIdcard());
        gender = ("M".equals(Model.getInstance().getCurrentDriver().getGender())) ? 0 : 1;
        getSpinnerGender().setSelection(gender);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    private EditText getFieldPhone() {
        return (EditText) getView().findViewById(R.id.et_field_phone);
    }

    private EditText getFieldMail() {
        return (EditText) getView().findViewById(R.id.et_field_email);
    }

    private EditText getFieldBirth() {
        return (EditText) getView().findViewById(R.id.et_field_birth);
    }

    private Spinner getSpinnerGender() {
        return (Spinner) getView().findViewById(R.id.spin_user_gender);
    }

    private EditText getFieldLicense() {
        return (EditText) getView().findViewById(R.id.et_user_license);
    }

    private EditText getFieldCard() {
        return (EditText) getView().findViewById(R.id.et_user_id_card);
    }

    private TextView getFieldGender() {
        return (TextView) getView().findViewById(R.id.edt_driver_gender);
    }

    private ImageView getIvEdtPhone() {
        return (ImageView) getView().findViewById(R.id.iv_edit_phone);
    }

    private ImageView getIvEdtEmail() {
        return (ImageView) getView().findViewById(R.id.iv_edit_email);
    }

    private ImageView getIvEdtBirth() {
        return (ImageView) getView().findViewById(R.id.iv_edit_birth);
    }

    private ImageView getIvEdtLisence() {
        return (ImageView) getView().findViewById(R.id.iv_edit_lisense);
    }

    private ImageView getIvEdtCard() {
        return (ImageView) getView().findViewById(R.id.iv_edit_card);
    }

    private Button getBtnNext() {
        return (Button) getView().findViewById(R.id.btn_driver_submit);
    }

    private ImageView getImageArrowDown() {
        return (ImageView) getView().findViewById(R.id.iv_arrow_down);
    }
}
