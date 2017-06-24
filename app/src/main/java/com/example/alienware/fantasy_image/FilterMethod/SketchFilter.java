package com.example.alienware.fantasy_image.FilterMethod;

import android.content.Context;
import android.graphics.Bitmap;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;

/**
 * Created by hasee on 2017/6/24.
 */

public class SketchFilter {
    static public Bitmap process(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageSketchFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }
}
