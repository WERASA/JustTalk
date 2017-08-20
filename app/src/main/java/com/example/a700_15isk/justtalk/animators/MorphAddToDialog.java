package com.example.a700_15isk.justtalk.animators;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.a700_15isk.justtalk.R;

/**
 * Created by 700-15isk on 2017/8/20.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MorphAddToDialog extends ChangeBounds {
    private static final String PROPERTY_COLOR = "property_color";
    private static final String[] TRANSITION_PROPERTIES = {
            PROPERTY_COLOR
    };
    private @ColorInt
    int startColor = Color.TRANSPARENT;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MorphAddToDialog(@ColorInt int startColor) {
        super();
        setStartColor(startColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MorphAddToDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStartColor(@ColorInt int startColor) {
        this.startColor = startColor;
    }

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) return;
        transitionValues.values.put(PROPERTY_COLOR, startColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) return;
        transitionValues.values.put(PROPERTY_COLOR,
                ContextCompat.getColor(view.getContext(), R.color.background));
    }



    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (startValues == null || endValues == null || changeBounds == null) {
            return null;
        }
        MorphDrawable background = new MorphDrawable(startColor, 0);
        endValues.view.setBackground(background);



        if (endValues.view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) endValues.view;
            float offset = vg.getHeight() / 3;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View v = vg.getChildAt(i);
                v.setTranslationY(offset);
                v.setAlpha(0f);
                v.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(500)
                        .setStartDelay(150)
                        .setInterpolator(AnimationUtils.loadInterpolator(vg.getContext(),
                                android.R.interpolator.fast_out_slow_in));
                offset *= 1.8f;
            }
        }
        AnimatorSet transition = new AnimatorSet();
        transition.playTogether(changeBounds);
        transition.setDuration(500);
        transition.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(),
                android.R.interpolator.fast_out_slow_in));
        return transition;

    }


}
