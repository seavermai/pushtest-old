package com.example.pushtest;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.utils.Log;

/**
 * Created by Seaver on 8/30/2016.
 */
public class RegistrationIntentService extends IntentService {
    private static final String TAG = "RegIntentService";
    public static final String INTENT_PUSH_REGISTRATION_COMPLETED = "com.example.pushtest.COMPLETED";
    public static final String PARAM_ERROR_MESSAGE = "ErrorMessage";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String error = null;
        try {
            synchronized (TAG) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String senderId = getString(R.string.gcm_defaultSenderId);
                String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                boolean development = true;
                KiiUser.pushInstallation(development).install(token);
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            error = e.getLocalizedMessage();
        }
        Intent registrationComplete = new Intent(INTENT_PUSH_REGISTRATION_COMPLETED);
        registrationComplete.putExtra(PARAM_ERROR_MESSAGE, error);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}