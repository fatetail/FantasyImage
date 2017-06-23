package com.example.alienware.fantasy_image;

import android.content.Context;
import android.graphics.Bitmap;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHardLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;

/**
 * Created by hasee on 2017/6/23.
 */

public class Filter {
    // 漫画滤镜
    public Bitmap comicFilter(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageGrayscaleFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

    // 素描滤镜
    public Bitmap sketchFilter(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageSketchFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

    // 卡通滤镜
    public Bitmap cartoonFilter(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageSmoothToonFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

    // 水粉滤镜
    public Bitmap kuwaharaFilter(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageKuwaharaFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

    // 浮雕滤镜
    public Bitmap embossFilter(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageEmbossFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

    // 阴影滤镜
    public Bitmap shadowFilter(Context context, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageHardLightBlendFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }
}
