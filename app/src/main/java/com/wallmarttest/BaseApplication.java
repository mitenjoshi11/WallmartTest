package com.wallmarttest;

import android.app.Application;

import com.wallmarttest.components.ApplicationComponent;
import com.wallmarttest.components.DaggerApplicationComponent;
import com.wallmarttest.modules.ApplicationModule;

public class BaseApplication extends Application {

    private static BaseApplication instance;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule())
                .build();

    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
