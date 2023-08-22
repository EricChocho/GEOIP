package com.viewsonic.webview;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PackageUtil {


    public static List<String> getAllInstalledApps(Context context) {
        List<String> packageNames = new ArrayList<>();

        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
            String packageName = packageInfo.packageName;
            packageNames.add(packageName);
        }

        return packageNames;
    }

    public static String getPackageName(Context context, String packageName) {

        PackageManager packageManager = context.getPackageManager();
                //  PackageInfo packageInfo=null;

        String AppName="";
        try {
            // 獲取指定 Package 的詳細資訊
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);

            // 獲取應用程式名稱
            AppName = packageInfo.applicationInfo.loadLabel(packageManager).toString();

        }
        catch (Exception e)
        {
            Log.i("Eric","Error :"+e.toString());
        }

        return  AppName;

    }

    public static String getPackageVersion(Context context, String packageName) {

        PackageManager packageManager = context.getPackageManager();
        //  PackageInfo packageInfo=null;

        String versionName="";
        try {
            // 獲取指定 Package 的詳細資訊
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);

            // 獲取應用程式名稱
            versionName = packageInfo.versionName;

        }
        catch (Exception e)
        {
            Log.i("Eric","Error :"+e.toString());
        }

        return  versionName;

    }

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        //  PackageInfo packageInfo=null;
        try {
            // 獲取指定 Package 的詳細資訊
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);

            // 獲取應用程式名稱
            String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();



            // 獲取版本號
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            // 獲取安裝時間
            long installTime = packageInfo.firstInstallTime;

            // 獲取權限列表
            String[] permissions = packageInfo.requestedPermissions;

            // 輸出資訊
            Log.i("Eric", "App Name: " + appName);
            Log.i("Eric", "Version Name: " + versionName);
            Log.i("Eric", "Version Code: " + versionCode);
            Log.i("Eric", "Install Time: " + installTime);


            long bootTimeSeconds = installTime / 1000;

            long days = bootTimeSeconds / (60 * 60 * 24);
            long hours = (bootTimeSeconds % (60 * 60 * 24)) / (60 * 60);
            long minutes = (bootTimeSeconds % (60 * 60)) / 60;
            long seconds = bootTimeSeconds % 60;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String formattedBootTime = sdf.format(new Date(installTime));


            Log.i("Eric", "Install Time 2: " + formattedBootTime);

            Log.i("Eric", "Permissions: ");
            if (permissions != null) {
                for (String permission : permissions) {
                    Log.i("Eric", permission);
                }
            }
            return packageInfo;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

            // PackageInfo packageInfo;
            // return packageInfo;
        }
        return null;
    }
}
