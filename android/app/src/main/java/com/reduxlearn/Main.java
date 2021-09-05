package com.reduxthunkk;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    public Main(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "Main";
    }

    @ReactMethod
    public void show(String text) {
        Toast.makeText(reactContext, text, Toast.LENGTH_LONG).show();
    }


    @ReactMethod
    public void getSMS(Promise promise) {
        try {
            WritableArray sms = new WritableNativeArray();
            Uri uri = Uri.parse("content://sms/inbox");

            Cursor cursor = reactContext.getContentResolver().query(uri, null, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                WritableMap info = new WritableNativeMap();
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                info.putString("number", address);
                info.putString("message", body);
                sms.pushMap(info);
            }
            if (cursor != null) {
                cursor.close();
            }
            System.out.println(sms);
            promise.resolve(sms);
        } catch (Exception error) {
            promise.reject("Error", error);
        }
    }


}
