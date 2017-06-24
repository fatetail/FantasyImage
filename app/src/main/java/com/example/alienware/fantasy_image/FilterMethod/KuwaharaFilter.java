package com.example.alienware.fantasy_image.FilterMethod;

import android.content.Context;
import android.graphics.Bitmap;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;

/**
 * Created by hasee on 2017/6/24.
 */

public class KuwaharaFilter {
    static public Bitmap process(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageKuwaharaFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }
}
