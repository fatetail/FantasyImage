package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class ConvertYProcess {
    static public Bitmap process(Bitmap bm) {
        bm = ImageProcess.convertYProcess(bm);
        return bm;
    }
}
