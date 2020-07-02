package com.hannah.demodataaccessaudit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.AsyncNotedAppOp;
import android.app.SyncNotedAppOp;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static android.os.storage.StorageManager.ACTION_MANAGE_STORAGE;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class ShowCamera extends AppCompatActivity {
    String PERMISSION_TAG = "Permission Alert";
    Context attributionContext;
    AppOpsManager.OnOpNotedCallback appOpsCallback;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_camera);

        appOpsCallback =
                new AppOpsManager.OnOpNotedCallback() {

                    @Override
                    public void onNoted(@NonNull SyncNotedAppOp syncNotedAppOp) {
                        Log.i(PERMISSION_TAG, "Private data accessed. ");

                    }

                    @Override
                    public void onSelfNoted(@NonNull SyncNotedAppOp syncNotedAppOp) {
                        Log.i(PERMISSION_TAG, "Private data accessed. ");

                    }

                    @Override
                    public void onAsyncNoted(@NonNull AsyncNotedAppOp asyncNotedAppOp) {
                        Log.i(PERMISSION_TAG, "Private data accessed. ");


                    }

                };

        getLastLocation();

    }


    private void logPrivateDataAccess(String opCode,
                                      String attributionTag, String trace) {
        Log.i(PERMISSION_TAG, "Private data accessed. " +
                "Operation: $opCode\n " +
                "Attribution Tag:$attributionTag\nStack Trace:\n$trace");
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void getLastLocation() {
        attributionContext = createAttributionContext("sharePhotos");

        AppOpsManager appOpsManager = getSystemService(AppOpsManager.class);

        if (appOpsManager != null) {
            appOpsManager.setOnOpNotedCallback(getMainExecutor(),appOpsCallback);
            Log.i(PERMISSION_TAG, appOpsCallback.onNoted("syncNotedAppOp"));
        } else {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            int phoneType = tm.getPhoneType();

            switch (phoneType) {
                case (TelephonyManager.PHONE_TYPE_CDMA):
                    // your code
                    break;
                case (TelephonyManager.PHONE_TYPE_GSM):
                    // your code
                    break;
                case (TelephonyManager.PHONE_TYPE_NONE):
                    // your code
                    break;
            }
        }
    }


}