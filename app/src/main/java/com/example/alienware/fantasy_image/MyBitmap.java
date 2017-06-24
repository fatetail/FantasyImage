package com.example.alienware.fantasy_image;

import android.graphics.Bitmap;
import android.view.inputmethod.BaseInputConnection;

/**
 * Created by ALIENWARE on 2017/6/7.
 */


/*Bitmap类，全局静态方法，用于存储要处理的那张图片，可以在所有Activity中调用*/
public class MyBitmap {
    static private Bitmap bmp = null;
    static private Bitmap origin = null;

    /*
     current status value of bmp
     */
    static private float hueValue = 0;
    static private float satValue = 0;
    static private float lumValue = 0;
    static private float tranValue = 0;
    static private float rotValue = 0;
    static private int raidus = 0;

    public static void setBmp(Bitmap bm) {
        bmp = Bitmap.createBitmap(bm);
    }

    public static Bitmap getBmp() {
        return bmp;
    }

    public static Bitmap getOrigin() {
        return origin;
    }

    public static void setOrigin(Bitmap origin) {
        MyBitmap.origin = Bitmap.createBitmap(origin);
    }

    public static void setHueValue(float hueValue) {
        MyBitmap.hueValue = hueValue;
    }

    public static void setSatValue(float satValue) {
        MyBitmap.satValue = satValue;
    }

    public static void setLumValue(float lumValue) {
        MyBitmap.lumValue = lumValue;
    }

    public static void setTranValue(float tranValue) {
        MyBitmap.tranValue = tranValue;
    }

    public static void setRotValue(float rotValue) {
        MyBitmap.rotValue = rotValue;
    }

    public static void setRaidus(int raidus) {
        MyBitmap.raidus = raidus;
    }

    public static float getHueValue() {

        return hueValue;
    }

    public static float getSatValue() {
        return satValue;
    }

    public static float getLumValue() {
        return lumValue;
    }

    public static float getTranValue() {
        return tranValue;
    }

    public static float getRotValue() {
        return rotValue;
    }

    public static int getRaidus() {
        return raidus;
    }
}
