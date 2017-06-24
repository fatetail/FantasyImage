package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class SaturationProcess {
    static public Bitmap process(Bitmap bm, float satValue) {
        bm = ImageProcess.saturationProcess(bm, satValue);
        return bm;
    }
}
