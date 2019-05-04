package com.elshaabany.nyTimes;

import android.app.Application;

import com.elshaabany.nyTimes.components.DaggerNetComponent;
import com.elshaabany.nyTimes.components.NetComponent;
import com.elshaabany.nyTimes.modules.AppModule;
import com.elshaabany.nyTimes.modules.NetModule;

/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */

public class MyApplication extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BuildConfig.BASE_URL))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}