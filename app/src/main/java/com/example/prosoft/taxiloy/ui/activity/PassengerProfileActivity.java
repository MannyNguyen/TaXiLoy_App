package com.example.prosoft.taxiloy.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.example.prosoft.taxiloy.ui.fragment.FragmentGoogleMap;
import com.example.prosoft.taxiloy.ui.objects.CurrentPassenger;
import com.example.prosoft.taxiloy.ui.service.GPSTracker;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.CircleImageView;
import com.example.prosoft.taxiloy.ui.view.NavigationBar;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by prosoft on 12/30/15.
 */
public class PassengerProfileActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private int mBaseTranslationY;
    private int gender;
    private String[] userGender;
    private GenderAdapter adapter;
    public static final String POINT = "%f  Points";
    private boolean isStateEdit = false;
    private final int SELECT_AVATAR = 41;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_profile_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = getWindow();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userGender = getResources().getStringArray(R.array.gender);
        ArrayList<String> optionArrayStairsToBuilding = new ArrayList<>();
        Collections.addAll(optionArrayStairsToBuilding, userGender);
        adapter = new GenderAdapter(this, R.layout.cell_spinner_gender_1,
                optionArrayStairsToBuilding, getResources());
        getSpinGender().setAdapter(adapter);
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


//        ViewCompat.setElevation(getTopBanner(),
//                getResources().getDimension(R.dimen.toolbar_elevation));
        getScrollView().setScrollViewCallbacks(this);
        getBtnEdit().setOnClickListener(this);
        getBirthField().setOnClickListener(this);
        getImgViewAvatar().setOnClickListener(this);

        initViews();

    }

    private void initViews() {
        isStateEdit = false;
        CurrentPassenger currentPassenger = Model.getInstance().getCurrentPassenger();

        if (SystemUtils.StringToBitMap(currentPassenger.getAvatar(), 50, 50) != null) {
            getImgViewAvatar().setImageBitmap(SystemUtils
                    .StringToBitMap(currentPassenger.getAvatar(), 50, 50));
        }
        getNameField().setText(currentPassenger.getName());
        getNameField().setInputType(InputType.TYPE_NULL);
        getPonitField().setText(String.format(POINT, currentPassenger.getPoint()));
        getPhoneField().setText(currentPassenger.getPhone());
        getPhoneField().setInputType(InputType.TYPE_NULL);
        getBirthField().setText(currentPassenger.getBirthday());
        getBirthField().setInputType(InputType.TYPE_NULL);

        String gender = ("M".equalsIgnoreCase(currentPassenger.getGender())) ?
                getResources().getStringArray(R.array.gender)[0]
                : getResources().getStringArray(R.array.gender)[1];
        getGenderField().setText(gender);
        getGenderField().setInputType(InputType.TYPE_NULL);
        getGenderField().setVisibility(View.VISIBLE);
        getSpinGender().setVisibility(View.GONE);

        getImgViewBirth().setVisibility(View.GONE);
        getImgViewName().setVisibility(View.GONE);
        getImgViewEmail().setVisibility(View.GONE);
        getImgViewPhone().setVisibility(View.GONE);
        getImgViewAddAvatar().setVisibility(View.GONE);

        getEmailField().setText(currentPassenger.getEmail());
        getEmailField().setInputType(InputType.TYPE_NULL);

        getBtnEdit().setText(getString(R.string.edit_profile));

    }

    private void enableEditViews() {
        isStateEdit = true;
        getBtnEdit().setText(getString(R.string.save));
        getNameField().setInputType(InputType.TYPE_CLASS_TEXT);
        getGenderField().setVisibility(View.GONE);
        getSpinGender().setVisibility(View.VISIBLE);

        getSpinGender().setSelection(gender);
        getEmailField().setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        getImgViewBirth().setVisibility(View.VISIBLE);
        getImgViewName().setVisibility(View.VISIBLE);
        getImgViewEmail().setVisibility(View.VISIBLE);
//        getImgViewPhone().setVisibility(View.VISIBLE);
        getImgViewAddAvatar().setVisibility(View.VISIBLE);

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        finish();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btn_edit):
                if (!isStateEdit) {
                    enableEditViews();
                } else {
                    requestPutUpdatePassenger();
                }
                break;
            case (R.id.et_birth_passenger):
                setDateText(getBirthField());
                if (isStateEdit) {
                    showDialog(DATE_PICKER_ID);
                }
                break;
            case (R.id.iv_avatar):
                if (isStateEdit) {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_AVATAR);
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_AVATAR && resultCode == RESULT_OK) {

            final Uri contentUri = data.getData();
            getImgViewAvatar().setImageURI(contentUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap
                        (this.getContentResolver(), contentUri);
                encodedImage = SystemUtils.BitMapToString(bitmap);
            } catch (Exception e) {
                encodedImage = null;
            }

        }

    }

    private void requestPutUpdatePassenger() {
        CurrentPassenger currentPassenger = new CurrentPassenger();
        currentPassenger.setName(getNameField().getText().toString());
        currentPassenger.setPhone(getPhoneField().getText().toString());
        currentPassenger.setBirthday(getBirthField().getText().toString());
        if (encodedImage == null) {
            currentPassenger.setAvatar(null);
        } else {
            currentPassenger.setAvatar(encodedImage);
        }
        if (gender == 0) {
            currentPassenger.setGender("M");
        } else {
            currentPassenger.setGender("F");
        }
        currentPassenger.setEmail(getEmailField().getText().toString());
        getBusyIndicator("Saving...").show();
        Model.getInstance().putUpdatePassenger(SystemUtils.getAccessToken(this),
                currentPassenger, SystemUtils.getDeviceID(this));
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

//        if (dragging) {
//            int toolbarHeight = navBar.getHeight();
//            if (firstScroll) {
//                float currentHeaderTranslationY = ViewHelper.getTranslationY(rl_top_banner);
//                if (-toolbarHeight < currentHeaderTranslationY) {
//                    mBaseTranslationY = scrollY;
//                }
//            }
//            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
//            ViewPropertyAnimator.animate(rl_top_banner).cancel();
//            ViewHelper.setTranslationY(rl_top_banner, headerTranslationY);
//        }
        ViewHelper.setTranslationY(getTopBanner(), scrollY / 2);
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.PUT_DETAIL_PASSENGER_SUCCESS) {
            getBusyIndicator().dismiss();
            initViews();
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

//        if (rl_top_banner == null) {
//            return;
//        }
//        if (scrollState == ScrollState.DOWN) {
//            showToolbar();
//        } else if (scrollState == ScrollState.UP) {
//            int toolbarHeight = navBar.getHeight();
//            int scrollY = scrollView.getCurrentScrollY();
//            if (toolbarHeight <= scrollY) {
//                hideToolbar();
//            } else {
//                showToolbar();
//            }
//        } else {
//            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
//            if (!toolbarIsShown() && !toolbarIsHidden()) {
//                // Toolbar is moving but doesn't know which to move:
//                // you can change this to hideToolbar()
//                showToolbar();
//            }
//        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(getScrollView().getCurrentScrollY(), false, false);
    }

    private EditText getNameField() {
        return (EditText) findViewById(R.id.tv_name_passenger);
    }

    private TextView getPonitField() {
        return (TextView) findViewById(R.id.tv_point_passenger);
    }

    private EditText getPhoneField() {
        return (EditText) findViewById(R.id.et_phone_passenger);
    }

    private EditText getBirthField() {
        return (EditText) findViewById(R.id.et_birth_passenger);
    }

    private EditText getGenderField() {
        return (EditText) findViewById(R.id.et_gender_passenger);
    }

    private EditText getEmailField() {
        return (EditText) findViewById(R.id.et_email_passenger);
    }

    private RelativeLayout getTopBanner() {
        return (RelativeLayout) findViewById(R.id.rl_top_banner);
    }

    private NavigationBar getNavBar() {
        return (NavigationBar) findViewById(R.id.navBar);
    }

    private ObservableScrollView getScrollView() {
        return (ObservableScrollView) findViewById(R.id.scroll);
    }

    private Button getBtnEdit() {
        return (Button) findViewById(R.id.btn_edit);
    }

    private Spinner getSpinGender() {
        return (Spinner) findViewById(R.id.spin_gender_passenger);
    }

    private ImageView getImgViewBirth() {
        return (ImageView) findViewById(R.id.iv_edit_birth);
    }

    private ImageView getImgViewPhone() {
        return (ImageView) findViewById(R.id.iv_edit_phone);
    }

    private ImageView getImgViewEmail() {
        return (ImageView) findViewById(R.id.iv_edit_email);
    }

    private ImageView getImgViewName() {
        return (ImageView) findViewById(R.id.iv_edit_name);
    }

    private ImageView getImgViewAddAvatar() {
        return (ImageView) findViewById(R.id.iv_plus_add);
    }

    private CircleImageView getImgViewAvatar() {
        return (CircleImageView) findViewById(R.id.iv_avatar);
    }
}
