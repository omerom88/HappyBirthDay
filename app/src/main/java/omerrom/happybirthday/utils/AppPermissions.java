package omerrom.happybirthday.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import omerrom.happybirthday.R;

/**
 * Created by omerom88 on 23-Oct-18
 */
public class AppPermissions {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    /**
     * check permission for open gallery and camera
     * @param context calling activity context
     * @return true if permit
     */
    public static boolean checkPermission(final Context context)
    {
        if(checkPhoneApi())
        {
            if (checkReadExternalPermission(context) != PackageManager.PERMISSION_GRANTED) {
                if (shouldRequestPermission((Activity) context)) {
                    createDialog(context);
                } else {
                    requestPermission((Activity) context);
                }
                return false;
            }
            return true;
        }
        return true;
    }

    private static boolean shouldRequestPermission(Activity context) {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private static boolean checkPhoneApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private static void requestPermission(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    private static int checkReadExternalPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private static void createDialog(Context context) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(R.string.permission_necessary);
        alertBuilder.setMessage(R.string.external_storage_permission);
        alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) -> ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}