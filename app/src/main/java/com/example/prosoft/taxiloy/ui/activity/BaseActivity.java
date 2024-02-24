package com.example.prosoft.taxiloy.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.dialogs.SimpleAlertDialog;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by prosoft on 12/16/15.
 */
public class BaseActivity extends AppCompatActivity implements Observer, View.OnClickListener {
    private boolean isVisible;
    static final int DATE_PICKER_ID = 1111;

    private Tracker mTracker;

    private EditText editTextDate;

    protected void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    protected boolean isVisible() {
        return isVisible;
    }

    protected ProgressDialog busyIndicator;
    protected AlertDialog alertDialog;

    // UI components
    protected ProgressDialog getBusyIndicator() {
        if (busyIndicator == null) {
            busyIndicator = SimpleAlertDialog.createBusyIndicator(this, getString(R.string.logging_in_message));
        }
        return busyIndicator;
    }

    protected ProgressDialog getBusyIndicator(int resString) {
        if (busyIndicator == null) {
            busyIndicator = SimpleAlertDialog.createBusyIndicator(this, getString(resString));
        }
        return busyIndicator;
    }

    public ProgressDialog getBusyIndicator(String string) {
        if (busyIndicator == null) {
            busyIndicator = SimpleAlertDialog.createBusyIndicator(this, string);
        }
        return busyIndicator;
    }

    protected AlertDialog showMessageWithEditText(int string, int cancel, int accept, Runnable onCancel, Runnable onAccept) {
        if (alertDialog == null) {
            alertDialog = SimpleAlertDialog.showMessageWithCancelAndAcceptButtons(this, null, getString(string), getString(cancel).toUpperCase(), getString(accept).toUpperCase(), onCancel, onAccept).create();
        }
        return alertDialog;
    }

    protected AlertDialog showErrorMessage(int string, int cancel, int accept, Runnable onCancel, Runnable onAccept) {
        if (alertDialog == null) {
            alertDialog = SimpleAlertDialog.showMessageWithCancelAndAcceptButtons(this, null, getString(string), getString(cancel).toUpperCase(), getString(accept).toUpperCase(), onCancel, onAccept).create();
        }
        return alertDialog;
    }

    protected AlertDialog showErrorMessage(int string, int accept, Runnable onAccept) {
        if (alertDialog == null) {
            alertDialog = SimpleAlertDialog.showMessageWithOkButton(this, getString(string), getString(accept).toUpperCase(), onAccept).create();
        }
        return alertDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        setIsVisible(true);
        Model.getInstance().addObserver(this);

    }

    @Override
    public void onPause() {
        setIsVisible(false);
        Model.getInstance().deleteObserver(this);
        super.onPause();
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable == Model.getInstance() && isVisible()) {
            onModelUpdated((ModelEvent) object);
        }
    }

    protected void onModelUpdated(ModelEvent evt) {
        // Nothing to do, yet.
    }


    @Override
    public void onClick(View v) {

    }

    public void setDateText(EditText editText) {
        editTextDate = editText;
    }

    public EditText getDateText() {
        if (editTextDate != null) {
            return editTextDate;
        }
        return null;
    }

    @Deprecated
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                Calendar c = Calendar.getInstance();
                return new DatePickerDialog(this, pickerListener
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            // Show selected date
            getDateText().setText(new StringBuilder().append(selectedMonth + 1)
                    .append("/").append(selectedDay).append("/").append(selectedYear)
                    .append(" "));

        }
    };
}