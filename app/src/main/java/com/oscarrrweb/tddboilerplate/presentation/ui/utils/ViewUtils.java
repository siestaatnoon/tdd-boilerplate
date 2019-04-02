package com.oscarrrweb.tddboilerplate.presentation.ui.utils;

import android.view.View;
import android.view.animation.TranslateAnimation;

public final class ViewUtils {

    public static void toggleShow(View view, int duration) {
        if (view == null) {
            return;
        }

        if (view.getVisibility() == View.VISIBLE) {
            TranslateAnimation animate = new TranslateAnimation(
                    0,               // fromXDelta
                    0,                 // toXDelta
                    view.getHeight(),          // fromYDelta
                    0);                // toYDelta
            animate.setDuration(duration);
            animate.setFillAfter(true);
            view.startAnimation(animate);
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,               // fromXDelta
                    0,                 // toXDelta
                    0,               // fromYDelta
                    view.getHeight()); // toYDelta
            animate.setDuration(duration);
            animate.setFillAfter(true);
            view.startAnimation(animate);
        }
    }
}
