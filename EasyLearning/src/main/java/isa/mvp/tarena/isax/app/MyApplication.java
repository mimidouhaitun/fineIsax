package isa.mvp.tarena.isax.app;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by tarena on 2017/8/4.
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initBmob();
    }

    public  void initBmob(){
        Bmob.initialize(getApplicationContext(), "73e737a9d1aa149e036406bf923b50bf");
    }




}
