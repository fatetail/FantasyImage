package com.example.alienware.fantasy_image.Factory;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.alienware.fantasy_image.FilterMethod.CartoonFilter;
import com.example.alienware.fantasy_image.FilterMethod.ComicFilter;
import com.example.alienware.fantasy_image.FilterMethod.EmbossFilter;
import com.example.alienware.fantasy_image.FilterMethod.KuwaharaFilter;
import com.example.alienware.fantasy_image.FilterMethod.ShadowFilter;
import com.example.alienware.fantasy_image.FilterMethod.SketchFilter;
import com.example.alienware.fantasy_image.MyBitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class FilterFactory {

    static public Bitmap processImage(int option, Context context) {
        Bitmap bm = null;

        if (option == 6) {
            Log.i("FilterFactory","comic start processing");
            bm = ComicFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
            Log.i("FilterFactory","comic end processing");
        } else if (option == 7) {
            Log.i("FilterFactory","cartoon start processing");
            bm = CartoonFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
            Log.i("FilterFactory","cartoon end processing");
        } else if (option == 8) {
            Log.i("FilterFactory","emboss start processing");
            bm = EmbossFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
            Log.i("FilterFactory","emboss end processing");
        } else if (option == 9) {
            Log.i("FilterFactory","kuwahara start processing");
            bm = KuwaharaFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
            Log.i("FilterFactory","kuwahara end processing");
        } else if (option == 10) {
            Log.i("FilterFactory","shadow start processing");
            bm = ShadowFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
            Log.i("FilterFactory","shadow end processing");
        } else if (option == 11) {
            Log.i("FilterFactory","sketch start processing");
            bm = SketchFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
            Log.i("FilterFactory","sketch end processing");
        }
        MyBitmap.setBmp(Bitmap.createBitmap(bm));
        return bm;
    }
}
