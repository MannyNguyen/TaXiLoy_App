package com.example.prosoft.taxiloy.ui.enums;

import com.example.prosoft.taxiloy.R;

/**
 * Created by prosoft on 12/25/15.
 */
public enum SliderDriverEnum {

    SLIDER1(R.string.slider_text_1, R.drawable.bg_slider_1),
    SLIDER2(R.string.slider_text_2, R.drawable.bg_slider_2),
    SLIDER3(R.string.slider_text_3, R.drawable.bg_slider_3),
    SLIDER4(R.string.slider_text_4, R.drawable.bg_slider_4);

    private int mTitleResId;
    private int mLayoutResId;

    SliderDriverEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }


}
