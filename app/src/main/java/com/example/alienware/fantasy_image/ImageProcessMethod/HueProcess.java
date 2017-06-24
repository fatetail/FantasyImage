package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class HueProcess {
    static public Bitmap process(Bitmap bm, float hueValue) {
        bm = ImageProcess.hueProcess(bm, hueValue);
        return bm;
    }
}
