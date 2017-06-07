package com.example.alienware.fantasy_image;

import android.graphics.Bitmap;

/**
 * Created by ALIENWARE on 2017/6/7.
 */


/*Bitmap类，全局静态方法，用于存储要处理的那张图片，可以在所有Activity中调用*/
public class MyBitmap {
    static private Bitmap bmp = null;

    static void setBmp(Bitmap bm) {
        bmp = bm;
    }

    static Bitmap getBmp() {
        return bmp;
    }
}
