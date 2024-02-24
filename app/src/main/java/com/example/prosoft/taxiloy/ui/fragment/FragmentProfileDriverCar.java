package com.example.prosoft.taxiloy.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.activity.RegisterDriverActivity;
import com.example.prosoft.taxiloy.ui.adapter.GenderAdapter;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by prosoft on 2/2/16.
 */
public class FragmentProfileDriverCar extends BaseFragment implements View.OnClickListener {

    public static FragmentProfileDriverCar _instance;
    private RegisterDriverActivity mActivity;

    public static FragmentProfileDriverCar getInstance() {
        if (_instance == null) {
            _instance = new FragmentProfileDriverCar();
        }
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_register_pending_car_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (RegisterDriverActivity) getActivity();
    }

    private void initViews() {
        getBtnNext().setOnClickListener(this);

        if (SystemUtils.getIDCar(getActivity()) != 0) {
            showInfo();
        }
//        if (Model.getInstance().getCurrentDriver().getActive() == 1) {
//            disableFields();
//        }

        disableFields();
    }

    private void showInfo() {
        getFieldBrand().setText(Model.getInstance().getCurrentDriver().getCarbrand());
        getFieldModel().setText(Model.getInstance().getCurrentDriver().getCarmodel());
        getFieldPlate().setText(Model.getInstance().getCurrentDriver().getCarno());
        getFieldSeats().setText(Model.getInstance().getCurrentDriver().getCarseats());

    }

    private void disableFields() {
        getFieldBrand().setInputType(InputType.TYPE_NULL);
        getFieldModel().setInputType(InputType.TYPE_NULL);
        getFieldPlate().setInputType(InputType.TYPE_NULL);
        getFieldSeats().setInputType(InputType.TYPE_NULL);
        getIvBrand().setVisibility(View.GONE);
        getIvModel().setVisibility(View.GONE);
        getIvPlate().setVisibility(View.GONE);
        getIvSeats().setVisibility(View.GONE);
        getBtnNext().setText(getResources().getString(R.string.back));
    }

    private void actionSummit() {

    }

    private void actionNext() {
        mActivity.setLayoutRegisterPersonal();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.bt_next):
                actionNext();
                break;
        }
    }

    private EditText getFieldBrand() {
        return (EditText) getView().findViewById(R.id.edt_pending_brand);
    }

    private EditText getFieldModel() {
        return (EditText) getView().findViewById(R.id.edt_pending_model);
    }

    private EditText getFieldPlate() {
        return (EditText) getView().findViewById(R.id.edt_pending_plate);
    }

    private EditText getFieldSeats() {
        return (EditText) getView().findViewById(R.id.edt_pending_seat);
    }

    private EditText getFieldProduction() {
        return (EditText) getView().findViewById(R.id.edt_pending_production);
    }

    private ImageView getIvBrand() {
        return (ImageView) getView().findViewById(R.id.iv_edit_brand);
    }

    private ImageView getIvModel() {
        return (ImageView) getView().findViewById(R.id.iv_edit_model);
    }

    private ImageView getIvPlate() {
        return (ImageView) getView().findViewById(R.id.iv_edit_plate);
    }

    private ImageView getIvSeats() {
        return (ImageView) getView().findViewById(R.id.iv_edit_seat);
    }

    private ImageView getIvProduction() {
        return (ImageView) getView().findViewById(R.id.iv_edit_production);
    }

    private Button getBtnNext() {
        return (Button) getView().findViewById(R.id.bt_next);
    }
}
