package com.viewsonic.webview;

import android.util.Log;

import java.io.File;

public class FileUtil {

    public static final String StroageFolderRoot="/storage/emulated/0/Android/data/";
    public static final String StroageFolder="com.viewsonic.bg";

     static  Boolean FileCheck(String Parrent,String Child)
    {
        File checkfie=new File(Parrent,Child);
        Log.i("Eric"," 2023.File filecheck"+checkfie.getPath());
        Log.i("Eric","2023  filecheck exist?::"+checkfie.exists());

        return checkfie.exists();

    }
    //public final String StroageFolderRoot="/storage/emulated/0/Android/data/com.viewsonic.bg/";
    //public final String StroageFolder="com.viewsonic.bg";


    static Boolean MakeFolder(String Parrent,String Child)
    {
        File makefolder=new File(Parrent,Child);
        File makddir2=new File(Parrent+Child+"//");
        File makddir3=new File(Parrent+"rrr");

        Log.i("Eric","12121"+makefolder.getPath());
        Log.i("Eric","12121"+makddir2.getPath());
        Log.i("Eric","12121"+makddir3.getPath());

        boolean S=makefolder.mkdir();
        boolean S2=makddir2.mkdir();
        boolean S3=makddir3.mkdir();


        Log.i("Eric","12121 S:"+S);
        Log.i("Eric","12121 S2"+S2);
        Log.i("Eric","12121 S3"+S3);

       //Log.i("Eric","File filecheck"+checkfie.getPath());
       // Log.i("Eric","File filecheck exist?::"+checkfie.exists());

        return S;

    }




}
