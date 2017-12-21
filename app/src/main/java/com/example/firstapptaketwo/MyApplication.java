package com.example.firstapptaketwo;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.carnival.sdk.Carnival;
import com.onesignal.OneSignal;

/**
 * Created by ftan on 12/20/17.
 */

public class MyApplication extends Application {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("testTag", "in MyApplication:onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("testTag", "in MyApplication:onCreate");
        Carnival.setMessageReceivedListener(CarnivalMessageReceiver.class);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
