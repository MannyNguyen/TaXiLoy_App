package com.example.prosoft.taxiloy.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
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
import com.example.prosoft.taxiloy.ui.activity.PassengerProfileActivity;
import com.example.prosoft.taxiloy.ui.activity.RegisterDriverActivity;
import com.example.prosoft.taxiloy.ui.adapter.GenderAdapter;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by prosoft on 1/7/16.
 */
public class FragmentRegisterPersonal extends BaseFragment implements View.OnClickListener {

    public static FragmentRegisterPersonal _instance;
    private String[] userGender;
    private int gender;
    private GenderAdapter adapter;
    private final int SELECT_LICENSE = 42;
    private final int SELECT_CARD = 43;
    private final int SELECT_CARD_1 = 44;
    private final int SELECT_CARD_2 = 45;
    private final int SELECT_LICENSE_1 = 46;
    private final int SELECT_LICENSE_2 = 47;
    private RegisterDriverActivity mActivity;
    private String encodeImage1;
    private String encodeImage2;
    private String encodeImage3;
    private String encodeImage4;

    public static FragmentRegisterPersonal getInstance() {
        if (_instance == null) {
            _instance = new FragmentRegisterPersonal();
        }
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.fragment_register_personal_screen, container, false);
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
        getIvLicense().setOnClickListener(this);
        getIvCard().setOnClickListener(this);
        getIvCardFrond().setOnClickListener(this);
        getIvCardBack().setOnClickListener(this);
        getIvLicenseFrond().setOnClickListener(this);
        getIvLicenseBack().setOnClickListener(this);
        getFieldBirth().setOnClickListener(this);
        getBtnNext().setOnClickListener(this);
        getFieldBirth().setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (RegisterDriverActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch (view.getId()) {
            case (R.id.iv_camera_license):
                startActivityForResult(i, SELECT_LICENSE);
                break;
            case (R.id.iv_camera_card):
                startActivityForResult(i, SELECT_CARD);
                break;
            case (R.id.iv_id_card_frond):
                startActivityForResult(i, SELECT_CARD_1);
                break;
            case (R.id.iv_id_card_back):
                startActivityForResult(i, SELECT_CARD_2);
                break;
            case (R.id.iv_license_back):
                startActivityForResult(i, SELECT_LICENSE_2);
                break;
            case (R.id.iv_license_frond):
                startActivityForResult(i, SELECT_LICENSE_1);
                break;
            case (R.id.et_field_birth):
                setDateText(getFieldBirth());
                onCreateDialog(DATE_PICKER_ID).show();
                break;
            case (R.id.bt_next):
                actionNext();
                break;
        }
    }

    private void actionNext() {
        if (SystemUtils.isEmpty(getFieldMail())) {
            getTvErrorEmail().setVisibility(View.VISIBLE);
            return;
        }
        getTvErrorEmail().setVisibility(View.INVISIBLE);
        String email = getFieldMail().getText().toString();
        if (SystemUtils.isEmpty(getFieldBirth())) {
            getTvErrorBirth().setVisibility(View.VISIBLE);
            return;
        }
        getTvErrorBirth().setVisibility(View.INVISIBLE);
        String birthday = getFieldBirth().getText().toString();
        if (SystemUtils.isEmpty(getFieldLicense())
                || encodeImage1 == null || encodeImage2 == null) {
            getTvErrorLicense().setVisibility(View.VISIBLE);
            return;
        }
        getTvErrorLicense().setVisibility(View.INVISIBLE);
        String getFieldLicense = getFieldBirth().getText().toString();
        if (SystemUtils.isEmpty(getFieldCard())
                || encodeImage3 == null || encodeImage4 == null) {
            getTvErrorCard().setVisibility(View.VISIBLE);
            return;
        }
        getTvErrorCard().setVisibility(View.INVISIBLE);
        String getFieldCard = getFieldCard().getText().toString();

        mActivity.setLayoutRegisterCar();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            final Uri contentUri = data.getData();
            Bitmap bitmap = SystemUtils.decodeSampledBitmapFromResource(contentUri, 50, 50, getActivity());
            switch (requestCode) {
                case SELECT_LICENSE:
                    if (getIvLicenseFrond().getVisibility() != View.VISIBLE) {
                        getIvLicenseFrond().setVisibility(View.VISIBLE);
                        getIvLicenseFrond().setImageBitmap(bitmap);
                        encodeImage1 = SystemUtils.BitMapToString(bitmap);
                    } else {
                        getIvLicenseBack().setVisibility(View.VISIBLE);
                        getIvLicenseBack().setImageBitmap(bitmap);
                        encodeImage2 = SystemUtils.BitMapToString(bitmap);
                    }
                    break;
                case SELECT_CARD:
                    if (getIvCardFrond().getVisibility() != View.VISIBLE) {
                        getIvCardFrond().setVisibility(View.VISIBLE);
                        getIvCardFrond().setImageBitmap(bitmap);
                        encodeImage3 = SystemUtils.BitMapToString(bitmap);
                    } else {
                        getIvCardBack().setVisibility(View.VISIBLE);
                        getIvCardBack().setImageURI(contentUri);
                        getIvCardBack().setImageBitmap(bitmap);
                        encodeImage4 = SystemUtils.BitMapToString(bitmap);
                    }
                    break;
                case SELECT_CARD_1:
                    getIvCardFrond().setVisibility(View.VISIBLE);
                    encodeImage3 = SystemUtils.BitMapToString(bitmap);
                    getIvCardFrond().setImageBitmap(bitmap);
                    break;
                case SELECT_CARD_2:
                    getIvCardBack().setVisibility(View.VISIBLE);
                    encodeImage4 = SystemUtils.BitMapToString(bitmap);
                    getIvCardBack().setImageBitmap(bitmap);
                    break;
                case SELECT_LICENSE_1:
                    getIvLicenseFrond().setVisibility(View.VISIBLE);
                    encodeImage1 = SystemUtils.BitMapToString(bitmap);
                    getIvLicenseFrond().setImageBitmap(bitmap);
                    break;
                case SELECT_LICENSE_2:
                    getIvLicenseBack().setVisibility(View.VISIBLE);
                    encodeImage2 = SystemUtils.BitMapToString(bitmap);
                    getIvLicenseBack().setImageBitmap(bitmap);
                    break;
            }
        }

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

    private ImageView getIvLicense() {
        return (ImageView) getView().findViewById(R.id.iv_camera_license);
    }

    private ImageView getIvCard() {
        return (ImageView) getView().findViewById(R.id.iv_camera_card);
    }

    private ImageView getIvCardFrond() {
        return (ImageView) getView().findViewById(R.id.iv_id_card_frond);
    }

    private ImageView getIvCardBack() {
        return (ImageView) getView().findViewById(R.id.iv_id_card_back);
    }

    private ImageView getIvLicenseBack() {
        return (ImageView) getView().findViewById(R.id.iv_license_back);
    }

    private ImageView getIvLicenseFrond() {
        return (ImageView) getView().findViewById(R.id.iv_license_frond);
    }

    private Button getBtnNext() {
        return (Button) getView().findViewById(R.id.bt_next);
    }

    private TextView getTvErrorEmail() {
        return (TextView) getView().findViewById(R.id.tv_error_email);
    }

    private TextView getTvErrorBirth() {
        return (TextView) getView().findViewById(R.id.tv_error_birth);
    }

    private TextView getTvErrorLicense() {
        return (TextView) getView().findViewById(R.id.tv_error_license);
    }

    private TextView getTvErrorCard() {
        return (TextView) getView().findViewById(R.id.tv_error_card);
    }
}
