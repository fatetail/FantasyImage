package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class LuminanceProcess {
    static public Bitmap process(Bitmap bm, float lumValue) {
        bm = ImageProcess.lumProcess(bm, lumValue);
        return bm;
    }
}
