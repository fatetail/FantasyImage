package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class BlurProcess {
    static public Bitmap process(Bitmap bm, int blurValue) {
        bm = ImageProcess.blurProcess(bm, blurValue);
        return bm;
    }
}
