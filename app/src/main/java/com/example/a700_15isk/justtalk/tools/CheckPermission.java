package com.example.a700_15isk.justtalk.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.a700_15isk.justtalk.activities.TalkActivity;

/**
 * Created by 700-15isk on 2017/8/25.
 */

public class CheckPermission {


    public static boolean checkPermission(Activity activity,String requestPermission){
        if (ContextCompat.checkSelfPermission(activity, requestPermission)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{requestPermission},1);
                 return true;
        } else{
            return true;}

    }
}
