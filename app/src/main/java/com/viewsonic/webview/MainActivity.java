package com.viewsonic.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    WebView bv;
    TextView tvTime,tvData;
    List<String> InstallPackages;

    private  final long SEC=1000;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    Handler h=new Handler();
    Runnable time=new Runnable() {
        @Override
        public void run() {
           long now= System.currentTimeMillis();

            LocalDateTime now2=LocalDateTime.now();

          //  long now= System.currentTimeMillis();
           Date nowDate=new Date();
           nowDate.setTime(now);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault());
            SimpleDateFormat sdf2 = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
            SimpleDateFormat sdf3 = new SimpleDateFormat("MMddkkmmss", Locale.getDefault());
             long boottime= SystemClock.uptimeMillis();


            long bootTimeSeconds = boottime / 1000;

            long days = bootTimeSeconds / (60 * 60 * 24);
            long hours = (bootTimeSeconds % (60 * 60 * 24)) / (60 * 60);
            long minutes = (bootTimeSeconds % (60 * 60)) / 60;
            long seconds = bootTimeSeconds % 60;

             String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);



            String show= "Current Time Type:\n"+Long.toString(now)+"\n"+now2.toString()+"\n"+nowDate.toString()+"\n"+sdf.format(nowDate)+"\n"+sdf3.format(nowDate)
                         +"\nBootTime \n"+ String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds)
                         + "\n"+"開機時間:"+ sdf.format(new Date(now-boottime));


            InstallPackages=PackageUtil.getAllInstalledApps(MainActivity.this);
            //   Log.i("Eric","2023.07.27:"+Packages.size());


            String show2="inatall:"+InstallPackages.size() +"\n1:\n"+InstallPackages.get(0)
                          +"\n AppName:"+PackageUtil.getPackageName(MainActivity.this,InstallPackages.get(0))
                       +"\n App Version:"+PackageUtil.getPackageVersion(MainActivity.this,InstallPackages.get(0))
                    ;
            String show3="";

            show3=show3+"::"+PackageUtil.getPackageName(MainActivity.this,"android");

            for(int i=0;i<InstallPackages.size();i++){
                show3=show3+"\n"+(i+1)+": "+InstallPackages.get(i);
            }


            //      tvTime.setText(Long.toString(now));
          //  tvTime.setText(show);
            tvTime.setText(show3);
                 h.postDelayed(time,SEC);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //判断是否以授权相机权限，没有则授权
            ActivityCompat.requestPermissions(MainActivity.this, //动态申请权限的方法，参数是上下文
                    new
                            String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }

      // bv=(WebView) findViewById(R.id.dd);
        bv=new WebView(this);

        tvTime=(TextView)findViewById(R.id.tv1);

        h.post(time);

        WebSettings webSettings = bv.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用 JavaScript
       // bv.setWebViewClient(new WebViewClient());

        bv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // 执行 JavaScript 以获取 IP LOCATION 数据
                bv.evaluateJavascript("javascript:(function() { " +
                        "var locationSpan = document.querySelector('.ipinfo--location'); " +
                        "if (locationSpan) { " +
                        "   var locationText = locationSpan.innerText; " +
                        "   Android.onLocationInfoReceived(locationText); " +
                        "} })();", null);
            }


        });

// 创建一个 WebView 的 JavaScript 接口
        class LocationInfoInterface {
            @JavascriptInterface
            public void onLocationInfoReceived(String locationText) {

             Log.i("Eric","Eric : ll:"+locationText);
                // 在这里处理从 JavaScript 获取到的位置信息
                // locationText 包含 "Taipei, Taipei (TW)" 和其他信息
              //  locationText.split("(");
                // 您可以使用字符串处理方法来提取需要的部分
            }
        }


        bv.addJavascriptInterface(new LocationInfoInterface(), "Android");

        //bv.loadUrl("https://www.yahoo.com.tw");
        bv.loadUrl("https://www.iplocation.net/");

     //   FileUtil F=new FileUtil();
      //  Boolean d=F.FileCheck(F.StroageFolderRoot,F.StroageFolder);
        Boolean d=FileUtil.FileCheck(FileUtil.StroageFolderRoot,
                FileUtil.StroageFolder);
       // if(!d)
      //  FileUtil.MakeFolder("/storage/emulated/0/Android/data/","com.viewsonic.bg");
        List<String> InstallPackages=PackageUtil.getAllInstalledApps(this);
        //   Log.i("Eric","2023.07.27:"+Packages.size());

        // Boolean d2=FileUtil.FileCheck("/storage/emulated/0/","com.viewsonic.bg");


        ;


     //   Log.i("Eric","!!"+d2);
    }
}