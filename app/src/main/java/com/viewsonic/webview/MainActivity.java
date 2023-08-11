package com.viewsonic.webview;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    WebView bv;
    TextView tvTime,tvData;

    private  final long SEC=1000;

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

             long boottime= SystemClock.uptimeMillis();


            long bootTimeSeconds = boottime / 1000;

            long days = bootTimeSeconds / (60 * 60 * 24);
            long hours = (bootTimeSeconds % (60 * 60 * 24)) / (60 * 60);
            long minutes = (bootTimeSeconds % (60 * 60)) / 60;
            long seconds = bootTimeSeconds % 60;

             String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);



            String show= "Current Time Type:\n"+Long.toString(now)+"\n"+now2.toString()+"\n"+nowDate.toString()+"\n"+sdf.format(nowDate)+"\n"+sdf2.format(nowDate)
                         +"\nBootTime \n"+ String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds)
                         + "\n"+"開機時間:"+ sdf.format(new Date(now-boottime));

                    ;

           //      tvTime.setText(Long.toString(now));
            tvTime.setText(show);
                 h.postDelayed(time,SEC);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




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
    }
}