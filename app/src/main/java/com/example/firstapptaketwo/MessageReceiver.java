package com.example.firstapptaketwo;

/**
 * Created by ftan on 12/19/17.
import android.app.Application;
 */
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.GcmListenerService;

// Per Carnival Docs.
public class MessageReceiver extends GcmListenerService {
    public MessageReceiver() {
        super();
    }

//    @Override
//    public void onMessageReceived(String sender, Bundle data) {
//        Log.d("testTag", "in MessageReceiver:onMessageReceived");
//        String testVal = data.getString("TestKey");
//        Log.d("testTag", testVal);

        /* This is Target's Code.

         Carnival.setMessageReceivedListener(CarnivalMessageReceiver.class);

        UserPreferenceService userPreferenceService = new UserPreferenceService(context);

        if (!userPreferenceService.isNotificationEnabled()) {
            //Swallows notification
            return;
        }

        String origin = data.getString(ORIGIN_KEY);
        if (origin != null && origin.equals(HELPSHIFT_ORIGIN)) {
            Core.handlePush(this, data);
        } else {
            CarnivalMessageReceivedListener carnival = new CarnivalMessageReceivedListener();
            carnival.onMessageReceived(context, data, null);
        }
        */
//    }
}