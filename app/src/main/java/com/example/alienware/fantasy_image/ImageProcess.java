package com.example.alienware.fantasy_image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Administrator on 2017/6/9.
 * 图像处理类
 */

public class ImageProcess {

    /**
     * 调整图像色调
     * @param bm
     * 传入位图
     * @param mHueValue
     * 修改色调的倍数
     * @return
     * 返回修改后位图
     */
    public static Bitmap hueProcess(Bitmap bm, float mHueValue) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(bmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

        ColorMatrix mHueMatrix = new ColorMatrix();
        mHueMatrix.reset();
        mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1);

        paint.setColorFilter(new ColorMatrixColorFilter(mHueMatrix)); // 设置颜色变换效果
        canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return  bmp;
    }

    /**
     * 调整图像饱和度
     * @param bm
     * 传入位图
     * @param mSaturationValue
     * 最小可设为0，此时对应的是灰度图，
     * 为1表示饱和度不变，设置大于1，就显示过饱和
     * @return
     * 返回修改后的位图
     */
    public static Bitmap saturationProcess(Bitmap bm, float mSaturationValue) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(bmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

        ColorMatrix mSaturationMatrix = new ColorMatrix();
        mSaturationMatrix.reset();
        mSaturationMatrix.setSaturation(mSaturationValue);

        paint.setColorFilter(new ColorMatrixColorFilter(mSaturationMatrix)); // 设置颜色变换效果
        canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return  bmp;
    }

    /**
     * 调整图像亮度
     * @param bm
     * 传入位图
     * @param mLumValue
     * 色轮旋转的角度,正值表示顺时针旋转（变亮），负值表示逆时针旋转（变暗）
     * @return
     * 返回修改后的位图
     */
    public static Bitmap lumProcess(Bitmap bm, float mLumValue) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(bmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

        ColorMatrix mLightnessMatrix = new ColorMatrix();
        mLightnessMatrix.reset(); // 设为默认值
        mLightnessMatrix.setRotate(0, mLumValue); // 控制让红色区在色轮上旋转的角度
        mLightnessMatrix.setRotate(1, mLumValue); // 控制让绿红色区在色轮上旋转的角度
        mLightnessMatrix.setRotate(2, mLumValue); // 控制让蓝色区在色轮上旋转的角度
        // 这里相当于改变的是全图的亮度

        paint.setColorFilter(new ColorMatrixColorFilter(mLightnessMatrix)); // 设置颜色变换效果
        canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return  bmp;
    }

    /**
     * 调整图像透明度
     * @param bm
     * 传入位图
     * @param mTransparencyValue
     * 传入透明度倍数
     * @return
     * 返回处理后的位图
     */
    public static Bitmap transparencyProcess(Bitmap bm, float mTransparencyValue) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(bmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

        ColorMatrix mTransparencMatrix = new ColorMatrix();
        mTransparencMatrix.reset();
        mTransparencMatrix.setScale(1, 1, 1, mTransparencyValue);

        paint.setColorFilter(new ColorMatrixColorFilter(mTransparencMatrix)); // 设置颜色变换效果
        canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return  bmp;
    }

    /**
     * 进行图像缩放
     * @param bm
     * 传入位图
     * @param w
     * 需要放缩到的宽度
     * @param h
     * 需要放缩到的高度
     * @return
     * 返回放缩后的位图
     */
    public static Bitmap resizeProcess(Bitmap bm, int w, int h) {
        int width = bm.getWidth(); //获得位图宽度
        int height = bm.getHeight(); //获得位图高度

        float scaleWidth = ((float) w) / width; //计算宽度缩放比例
        float scaleHeight = ((float) h) / height; //计算高度缩放比例

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight); //计算缩放矩阵

        //生成缩放后位图,并返回
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 进行图像旋转
     * @param bm
     * 传入位图
     * @param rotateValue
     * 旋转角度，角度制
     * @return
     * 旋转后位图
     */
    public static Bitmap rotateProcess(Bitmap bm, float rotateValue) {
        int width = bm.getWidth(); //获得位图宽度
        int height = bm.getHeight(); //获得位图高度

        Matrix matrix = new Matrix();
        matrix.postRotate(rotateValue); //计算旋转矩阵
        //生成缩放后位图,并返回
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 进行图像裁剪,截取以（ltX，ltY）--(rbX,rbY)为对角线的区域
     * @param bm
     * 传入图像
     * @param ltX
     * 裁剪区域左上角的x坐标
     * @param ltY
     * 裁剪区域左上角的y坐标
     * @param rbX
     * 裁剪区域右下角的x坐标
     * @param rbY
     * 裁剪区域右下角的y坐标
     * @return
     * 返回处理后的图像
     */
    public static Bitmap cropProcess(Bitmap bm, int ltX, int ltY, int rbX, int rbY) {
        return  Bitmap.createBitmap(bm, ltX, ltY, rbX, rbY);
    }

    /**
     * 进行图像水平镜像翻转
     * @param bm
     * 传入位图
     * @return
     * 返回位图
     */
    public static Bitmap convertXProcess(Bitmap bm) {
        int width = bm.getWidth(); //获得位图宽度
        int height = bm.getHeight(); //获得位图高度

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 计算镜像水平翻转矩阵
        //生成缩放后位图,并返回
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 进行图像垂直镜像翻转
     * @param bm
     * 传入位图
     * @return
     * 返回位图
     */
    public static Bitmap convertYProcess(Bitmap bm) {
        int width = bm.getWidth(); //获得位图宽度
        int height = bm.getHeight(); //获得位图高度

        Matrix matrix = new Matrix();
        matrix.postScale(1, -1); // 计算镜像水平翻转矩阵
        //生成缩放后位图,并返回
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }
}
