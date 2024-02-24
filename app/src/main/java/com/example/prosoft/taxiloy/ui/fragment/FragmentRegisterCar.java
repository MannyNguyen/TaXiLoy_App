package com.example.prosoft.taxiloy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prosoft.taxiloy.R;

/**
 * Created by prosoft on 1/7/16.
 */
public class FragmentRegisterCar extends BaseFragment implements View.OnClickListener {

    public static FragmentRegisterCar _instance;

    public static FragmentRegisterCar getInstance() {
        if (_instance == null) {
            _instance = new FragmentRegisterCar();
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
    public void onClick(View v) {
    }
}
