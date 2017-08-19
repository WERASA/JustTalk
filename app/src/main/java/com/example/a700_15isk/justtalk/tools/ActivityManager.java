package com.example.a700_15isk.justtalk.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 700-15isk on 2017/8/18.
 */

public class ActivityManager  {
  public  static   List<Activity>mActivities=new ArrayList<>();
    public static void addActivity(Activity activity){

        mActivities.add(activity);

    }

    public static void removeActivity(Activity activity){
        mActivities.remove(activity);
    }

    public static void clearActivities(){
        mActivities.clear();
    }
}
