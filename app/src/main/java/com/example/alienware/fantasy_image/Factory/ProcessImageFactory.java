package com.example.alienware.fantasy_image.Factory;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.WindowManager;

import com.example.alienware.fantasy_image.ImageProcessMethod.BlurProcess;
import com.example.alienware.fantasy_image.ImageProcessMethod.ConvertXProcess;
import com.example.alienware.fantasy_image.ImageProcessMethod.ConvertYProcess;
import com.example.alienware.fantasy_image.ImageProcessMethod.HueProcess;
import com.example.alienware.fantasy_image.ImageProcessMethod.LuminanceProcess;
import com.example.alienware.fantasy_image.ImageProcessMethod.RotationProcess;
import com.example.alienware.fantasy_image.ImageProcessMethod.SaturationProcess;
import com.example.alienware.fantasy_image.ImageProcessMethod.TransparencyProcess;
import com.example.alienware.fantasy_image.MyBitmap;

/**
 * Created by hasee on 2017/6/23.
 */

public class ProcessImageFactory {
    /*
        @param [_x] < 0 means value minus, > 0 means value add
        @param [_y] < 0 means value minus, > 0 means value add
        @param [option]  different image process method
        @param [context] context using this class
        @param [sX] start point X coordinate value
        @param [sY] start point Y coordinate value
    * */
    static public Bitmap processImage(float _x, float _y, int option, Context context, float sX, float sY) {
        Bitmap bm = null;
        int y = (_y < 0)?-1:1;
        int x = (_x < 0)?-1:1;

        if (option == 0) {                  // choose process hue
            float hueValue = MyBitmap.getHueValue();
            hueValue += 1 * y;
            hueValue = (hueValue <= 0 ? 0.1f : hueValue);
            MyBitmap.setHueValue(hueValue);
            bm = HueProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), hueValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 1) {           // choose process saturation
            float satValue = MyBitmap.getSatValue();
            satValue += 1 * y;
            satValue = (satValue <= 0 ? 0.1f : satValue);
            MyBitmap.setSatValue(satValue);
            bm = SaturationProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), satValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 2) {           // choose process luminance
            float lumValue = MyBitmap.getLumValue();
            lumValue += 1 * y;
            lumValue = (lumValue <= 0 ? 0.1f : lumValue);
            MyBitmap.setLumValue(lumValue);
            bm = LuminanceProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), lumValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 3) {           // choose process rotation
            float rotValue = MyBitmap.getRotValue();
            int rotateOrientation = getRotateOrient(x, y, sX, sY, context);
            // int rotateOrientation = y;
            rotValue += 5 * rotateOrientation;
            MyBitmap.setRotValue(rotValue);
            bm = RotationProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), rotValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 4) {
            if (Math.abs(_x) > Math.abs(_y)) {// choose convert X
                bm = ConvertXProcess.process(MyBitmap.getBmp());
                MyBitmap.setBmp(Bitmap.createBitmap(bm));
            } else {
                bm = ConvertYProcess.process(MyBitmap.getBmp());
                MyBitmap.setBmp(Bitmap.createBitmap(bm));
            }
        }  else if (option == 5) {           // choose blur
            int raidus = MyBitmap.getRaidus();
            raidus += 1 * y;
            raidus = (raidus <= 0 ? 1 : raidus);
            MyBitmap.setRaidus(raidus);
            if (raidus != 1) {
                Bitmap temp = Bitmap.createBitmap(Bitmap.createBitmap(MyBitmap.getOrigin()));
                bm = BlurProcess.process(temp, raidus);
                MyBitmap.setBmp(Bitmap.createBitmap(bm));
            } else {
                bm = Bitmap.createBitmap(MyBitmap.getOrigin());
                MyBitmap.setBmp(Bitmap.createBitmap(bm));
            }
        } else {
            bm = Bitmap.createBitmap(MyBitmap.getBmp());
        }
        return bm;
    }

    static private int getRotateOrient(int x, int y, float sX, float sY, Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        if (sX < width / 2 && sY < height / 2) {
            if (x > 0 && y < 0) return 1;
            if (x < 0 && y > 0) return -1;
            if (x > 0 && y > 0) return -1;
            else return 1;
        }
        if (sX < width / 2 && sY >= height / 2) {
            if (x > 0 && y > 0) return -1;
            if (x < 0 && y < 0) return 1;
            if (x > 0 && y < 0) return 1;
            else return -1;
        }
        if (sX >= width / 2 && sY < height / 2) {
            if (x > 0 && y > 0) return 1;
            if (x < 0 && y < 0) return -1;
            if (x > 0 && y < 0) return 1;
            else return -1;
        }
        if (sX >= width / 2 && sY >= height / 2) {
            if (x > 0 && y < 0) return -1;
            if (x < 0 && y > 0) return 1;
            if (x < 0 && y < 0) return 1;
            else return -1;
        }
        return 0;
    }
}
