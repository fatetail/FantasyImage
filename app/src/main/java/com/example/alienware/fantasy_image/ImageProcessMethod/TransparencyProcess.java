package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class TransparencyProcess {
    static public Bitmap process(Bitmap bm, float transValue) {
        bm = ImageProcess.transparencyProcess(bm, transValue);
        return bm;
    }
}
