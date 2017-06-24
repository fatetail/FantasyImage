package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class ConvertXProcess {
    static public Bitmap process(Bitmap bm) {
        bm = ImageProcess.convertXProcess(bm);
        return bm;
    }
}
