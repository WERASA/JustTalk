package com.example.a700_15isk.justtalk.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.example.a700_15isk.justtalk.R;
import com.example.a700_15isk.justtalk.animators.MorphAddToDialog;
import com.example.a700_15isk.justtalk.animators.MorphDialogToAdd;

public class PopuActivity extends AppCompatActivity {
    private ViewGroup container;
    public static final String EXTRA_MORPH_TYPE = "morph_type";
    public static final String MORPH_TYPE_BUTTON = "morph_type_button";
    public static final String MORPH_TYPE_FAB = "morph_type_fab";
    private boolean isDismissing=false;

    public static Intent getStartIntent(Context context, String type) {
        Intent intent = new Intent(context, PopuActivity.class);
        intent.putExtra(EXTRA_MORPH_TYPE, type);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popu);
        setupSharedElementTransitionsButton(this, container);
        String type = getIntent().getStringExtra(EXTRA_MORPH_TYPE);
        if (type.equals(MORPH_TYPE_BUTTON)) {
            setupSharedElementTransitionsButton(this, container);
        }
        container = (ViewGroup) findViewById(R.id.container);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupSharedElementTransitionsButton(@NonNull Activity activity,
                                                    @Nullable View target) {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);
        int color = ContextCompat.getColor(activity, R.color.itemBackground);
        Interpolator easeInOut =
                AnimationUtils.loadInterpolator(activity, android.R.interpolator.fast_out_slow_in);
        MorphAddToDialog sharedEnter = new MorphAddToDialog(color);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);
        MorphDialogToAdd sharedReturn = new MorphDialogToAdd(color);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);
        if (target != null) {
            sharedEnter.addTarget(target);
            sharedReturn.addTarget(target);
        }
        activity.getWindow().setSharedElementEnterTransition(sharedEnter);
        activity.getWindow().setSharedElementReturnTransition(sharedReturn);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dismiss(View view) {
        isDismissing = true;
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }
    @Override
    public void onBackPressed() {
        dismiss(null);
    }

}
