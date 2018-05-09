package com.widgetexample;

import android.app.Application;

/**
 * Created by ajea on 23/06/17.
 */

public class WidgetApplication extends Application {

    private static  WidgetApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static WidgetApplication getApplication() {
        return application;
    }
}
