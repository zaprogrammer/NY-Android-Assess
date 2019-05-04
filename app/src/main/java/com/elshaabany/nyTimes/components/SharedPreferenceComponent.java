package com.elshaabany.nyTimes.components;

import android.content.SharedPreferences;

import com.elshaabany.nyTimes.modules.SharedPreferenceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ahmed M. ElShaabany on 04/05/2019.
 */

@Singleton
@Component(modules = {SharedPreferenceModule.class})
public interface SharedPreferenceComponent {
    SharedPreferences getSharedPreference();
}