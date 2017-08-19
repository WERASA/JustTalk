package com.example.a700_15isk.justtalk.tools;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.ViewGroup;

/**
 * Created by 700-15isk on 2017/8/18.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChangeAddToDiolog extends ChangeBounds {

    private static final String PROPERTY_COLOR = "property_color";
    private static final String PROPERTY_CORNER_RADIUS = "property_corner_radius";
    private static final String[] TRANSITION_PROPERTIES = {
            PROPERTY_COLOR,
            PROPERTY_CORNER_RADIUS
    };

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
