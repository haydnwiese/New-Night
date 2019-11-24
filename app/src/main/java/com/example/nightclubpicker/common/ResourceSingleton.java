package com.example.nightclubpicker.common;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class ResourceSingleton extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Resources getResourcesInstance() {
        return mContext.getResources();
    }
}