package com.example.prosoft.taxiloy.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.enums.ModelEvent;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;


public class BaseFragment extends Fragment implements Observer {

    static final int DATE_PICKER_ID = 1111;
    private EditText editTextDate;

    protected ProgressDialog busyIndicator;
    // UI components
//    protected ProgressDialog getBusyIndicator() {
//        if (busyIndicator == null) {
//            busyIndicator = SimpleAlertDialog.createBusyIndicator(getActivity(), getString(R.string.logging_in_message));
//        }
//        return busyIndicator;
//    }
//
//    protected ProgressDialog getBusyIndicator(int resString) {
//        //if (busyIndicator == null) {
//        busyIndicator = SimpleAlertDialog.createBusyIndicator(getActivity(), getString(resString));
//        /// }
//        return busyIndicator;
//    }
//
//    protected ProgressDialog getBusyIndicator(String string) {
//        if (busyIndicator == null) {
//            busyIndicator = SimpleAlertDialog.createBusyIndicator(getActivity(), string);
//        }
//        return busyIndicator;
//    }

    @Override
    public void onResume() {
        super.onResume();
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    backPress();
//                    return true;
//                }
//                return false;
//            }
//        });
        Model.getInstance().addObserver(this);

    }

    @Override
    public void onPause() {
        Model.getInstance().deleteObserver(this);
        super.onPause();
    }

    public void update(Observable observable, Object o) {
        if (observable == Model.getInstance()) {
            onModelUpdated((ModelEvent) o);
        }
    }

    protected void onModelUpdated(ModelEvent evt) {
        // Nothing to do, yet.
    }

    protected void backPress(){

    }

    @Override
    public void onDetach() {
        super.onDetach();

//        try {
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
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

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                Calendar c = Calendar.getInstance();
                return new DatePickerDialog(getActivity(), pickerListener
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
