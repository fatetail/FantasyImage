package com.example.alienware.fantasy_image.Factory;

import android.content.Context;
import android.graphics.Bitmap;

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

        if (option == 8) {
            bm = ComicFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
        } else if (option == 9) {
            bm = CartoonFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
        } else if (option == 10) {
            bm = EmbossFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
        } else if (option == 11) {
            bm = KuwaharaFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
        } else if (option == 12) {
            bm = ShadowFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
        } else if (option == 13) {
            bm = SketchFilter.process(context, Bitmap.createBitmap(MyBitmap.getBmp()));
        }
        MyBitmap.setBmp(Bitmap.createBitmap(bm));
        return bm;
    }
}
