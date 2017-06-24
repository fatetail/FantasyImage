package com.example.alienware.fantasy_image.ImageProcessMethod;

import android.graphics.Bitmap;

/**
 * Created by hasee on 2017/6/24.
 */

public class BlurProcess {
    static public Bitmap process(Bitmap bm, int blurValue) {
        //如果图片过大为了避免内存被回收对图片进行下采样
        if (bm.getWidth() * bm.getHeight() > 1600000) {
            int w = (int)(bm.getWidth() / 1.5);
            int h = (int)(bm.getHeight() / 1.5);
            while (w*h> 1600000) {
                w = (int)(bm.getWidth() / 1.5);
                h = (int)(bm.getHeight() / 1.5);
            }
            bm = ImageProcess.resizeProcess(bm, w, h);
        }
        bm = ImageProcess.blurProcess(bm, blurValue);
        return bm;
    }
}
