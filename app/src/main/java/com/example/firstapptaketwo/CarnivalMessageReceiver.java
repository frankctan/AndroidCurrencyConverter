package com.example.firstapptaketwo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.carnival.sdk.CarnivalMessageListener;
import com.carnival.sdk.Message;

/**
 * Created by ftan on 12/20/17.
 */

public class CarnivalMessageReceiver extends CarnivalMessageListener {
    @Override
    public boolean onMessageReceived(Context context, Bundle bundle, Message message) {
        // Return false to allow Carnival to generate the notification for me.
        Log.d("testTag", "CarnivalMessageReceiver:onMessageReceived");

        String testValue = bundle.getString("TestKey");

        if (testValue != null) {
            Log.d("testTag", testValue);
        }

        return false;
    }
}
