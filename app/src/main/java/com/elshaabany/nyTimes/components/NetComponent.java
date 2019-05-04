package com.elshaabany.nyTimes.components;

import com.elshaabany.nyTimes.activities.MainActivity;
import com.elshaabany.nyTimes.modules.AppModule;
import com.elshaabany.nyTimes.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
}