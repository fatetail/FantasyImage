package com.example.alienware.fantasy_image.Factory;

import android.content.Context;
import android.graphics.Bitmap;

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
        @param [gesture] < 0 means value minus, > 0 means value add
        @param [option]  different image process method
    * */
    static public Bitmap processImage(float _gesture, int option) {
        Bitmap bm = null;
        int gesture = (_gesture < 0)?-1:1;

        if (option == 0) {                  // choose process hue
            float hueValue = MyBitmap.getHueValue();
            hueValue += 3 * gesture;
            MyBitmap.setHueValue(hueValue);
            bm = HueProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), hueValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 1) {           // choose process saturation
            float satValue = MyBitmap.getSatValue();
            satValue += 3 * gesture;
            satValue = (satValue <= 0 ? 0.1f : satValue);
            MyBitmap.setSatValue(satValue);
            bm = SaturationProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), satValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 2) {           // choose process luminance
            float lumValue = MyBitmap.getLumValue();
            lumValue += 3 * gesture;
            lumValue = (lumValue <= 0 ? 0.1f : lumValue);
            MyBitmap.setLumValue(lumValue);
            bm = LuminanceProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), lumValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 3) {           // choose process rotation
            float rotValue = MyBitmap.getRotValue();
            rotValue += 5 * gesture;
            MyBitmap.setRotValue(rotValue);
            bm = RotationProcess.process(Bitmap.createBitmap(MyBitmap.getOrigin()), rotValue);
            MyBitmap.setBmp(Bitmap.createBitmap(bm));
        } else if (option == 4) {
            if (gesture < 0) {// choose convert X
                bm = ConvertXProcess.process(MyBitmap.getBmp());
                MyBitmap.setBmp(Bitmap.createBitmap(bm));
            } else {
                bm = ConvertYProcess.process(MyBitmap.getBmp());
                MyBitmap.setBmp(Bitmap.createBitmap(bm));
            }
        }  else if (option == 5) {           // choose blur
            int raidus = MyBitmap.getRaidus();
            raidus += 1 * gesture;
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
}
