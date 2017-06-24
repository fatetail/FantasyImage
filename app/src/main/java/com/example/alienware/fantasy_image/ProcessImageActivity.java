package com.example.alienware.fantasy_image;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.alienware.fantasy_image.Bean.PassedData;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import com.example.alienware.fantasy_image.Bean.PassedData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;

public class ProcessImageActivity extends AppCompatActivity {
    /*声明View*/
    private Button edit_button;
    private Button save_button;
    private Button cancel_button;
    private ImageView process_image_view;

    /*声明跳转Activity返回码*/
    private static final int REQUEST_FROM_ALBUM = 0;
    private static final int REQUEST_FROM_CAMERA = 1;

    private Bitmap bitmap;

    private PassedData passedData;

    private static boolean firstOpen = true;
    /*手势检测*/
    private GestureDetector gestureDetector;

    /*获取XML中的控件*/
    private void InitViewById() {
        save_button = (Button) findViewById(R.id.save_photo);
        edit_button = (Button) findViewById(R.id.edit_photo);
        cancel_button = (Button) findViewById(R.id.cancel);
        process_image_view = (ImageView) findViewById(R.id.process_image_view);
        bitmap = MyBitmap.getBmp();
        Drawable drawable = new BitmapDrawable(bitmap);
        process_image_view.setBackground(drawable);
        process_image_view.setImageBitmap(null);
        process_image_view.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    /*设置监听*/
    private void setListener() {
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*创建保存图片目录文件夹*/
                File appDir = new File(Environment.getExternalStorageDirectory(), "FantasyImage");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }

                /*根据系统时间命名图片名*/
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);

                /*写入文件夹中*/
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                /*广播通知图片更新*/
                /*
                try {
                    Uri uri = Uri.fromFile(file);
                    sendBroadcast(new Intent());
                    // MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),fileName,null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
*/
                Uri uri = Uri.fromFile(file);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstOpen) {
                    Dialog builder = new AlertDialog.Builder(ProcessImageActivity.this).setTitle("是否保存当前修改?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int k) {
                                    MyBitmap.setOrigin(MyBitmap.getBmp());
                                    Intent intent = new Intent(ProcessImageActivity.this, ListFunctionActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    builder.show();
                } else {
                    firstOpen = false;
                    Intent intent = new Intent(ProcessImageActivity.this, ListFunctionActivity.class);
                    startActivity(intent);
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBitmap.setBmp(MyBitmap.getOrigin());
                process_image_view.setImageBitmap(bitmap);
            }
        });
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            if (y > 0) {
                int valueNum = passedData.getValueNum();
                int i = passedData.getFuncId();

                // 选择调节色调的函数
                if (i == 0) {
                    float hueValue = MyBitmap.getHueValue();
                    hueValue -= 3;
                    hueValue = (hueValue <= 0 ? 0.1f : hueValue);
                    MyBitmap.setHueValue(hueValue);
                    Bitmap bitmap = ImageProcess.hueProcess(MyBitmap.getOrigin(), hueValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose hue");
                }
                //选择调节饱和度的函数
                if (i == 1) {
                    float satValue = MyBitmap.getSatValue();
                    satValue -= 3;
                    satValue = (satValue <= 0 ? 0.1f : satValue);
                    MyBitmap.setSatValue(satValue);
                    Bitmap bitmap = ImageProcess.saturationProcess(MyBitmap.getOrigin(), satValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose sat");
                }
                //选择调节透明度的函数
                if (i == 2) {
                    float tranValue = MyBitmap.getTranValue();
                    tranValue -= 3;
                    tranValue = (tranValue <= 0 ? 0.1f : tranValue);
                    MyBitmap.setTranValue(tranValue);
                    Bitmap bitmap = ImageProcess.transparencyProcess(MyBitmap.getOrigin(), tranValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose transparent");
                }
                //选择调节亮度的函数
                if (i == 3) {
                    float lumValue = MyBitmap.getLumValue();
                    lumValue -= 3;
                    lumValue = (lumValue <= 0 ? 0.1f : lumValue);
                    MyBitmap.setLumValue(lumValue);
                    Bitmap bitmap = ImageProcess.lumProcess(MyBitmap.getOrigin(), lumValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose luminance");
                }
                //选择旋转图像的函数
                if (i == 4) {
                    float rotValue = MyBitmap.getRotValue();
                    rotValue -= 5;
                    MyBitmap.setRotValue(rotValue);
                    Bitmap bitmap = ImageProcess.rotateProcess(MyBitmap.getOrigin(), rotValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose rotate");
                }
                //选择沿X轴垂直反转的函数
                if (i == 5) {
                    Bitmap bitmap = ImageProcess.convertXProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    bitmap = ImageProcess.convertXProcess(MyBitmap.getOrigin());
                    MyBitmap.setOrigin(bitmap);
                    Log.i("ProcessImageActivity", "choose covert X");
                }
                //选择沿Y轴垂直反转的函数
                if (i == 6) {
                    Bitmap bitmap = ImageProcess.convertYProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    bitmap = ImageProcess.convertYProcess(MyBitmap.getOrigin());
                    MyBitmap.setOrigin(bitmap);
                    Log.i("ProcessImageActivity", "choose convert Y");
                }
                //选择模糊的函数
                if (i == 7) {
                    int raidus = MyBitmap.getRaidus();
                    raidus -= 1;
                    raidus = (raidus <= 0 ? 1 : raidus);
                    MyBitmap.setRaidus(raidus);
                    if (raidus != 1) {
                        Bitmap temp = Bitmap.createBitmap(MyBitmap.getOrigin());
                        Bitmap bitmap = ImageProcess.blurProcess(temp, raidus);
                        MyBitmap.setBmp(bitmap);
                        process_image_view.setImageBitmap(bitmap);
                    } else {
                        bitmap = MyBitmap.getOrigin();
                        process_image_view.setImageBitmap(bitmap);
                    }
                    Log.i("ProcessImageActivity", ""+MyBitmap.getRaidus());
                    Log.i("ProcessImageActivity", "choose blur");
                }
                //选择滤镜的函数
                if (i == 8) {
                    Log.i("ProcessImageActivity", "choose filter");
                    showFilter();
                }

            } else {
                int valueNum = passedData.getValueNum();
                int i = passedData.getFuncId();

                // 选择调节色调的函数
                if (i == 0) {
                    float hueValue = MyBitmap.getHueValue();
                    hueValue += 3;
                    MyBitmap.setHueValue(hueValue);
                    Bitmap bitmap = ImageProcess.hueProcess(MyBitmap.getOrigin(), hueValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose hue");
                }
                //选择调节饱和度的函数
                if (i == 1) {
                    float satValue = MyBitmap.getSatValue();
                    satValue += 3;
                    MyBitmap.setSatValue(satValue);
                    Bitmap bitmap = ImageProcess.saturationProcess(MyBitmap.getOrigin(), satValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose sat");
                }
                //选择调节透明度的函数
                if (i == 2) {
                    float tranValue = MyBitmap.getTranValue();
                    tranValue += 3;
                    MyBitmap.setTranValue(tranValue);
                    Bitmap bitmap = ImageProcess.transparencyProcess(MyBitmap.getOrigin(), tranValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose transparent");
                }
                //选择调节亮度的函数
                if (i == 3) {
                    float lumValue = MyBitmap.getLumValue();
                    lumValue += 3;
                    MyBitmap.setLumValue(lumValue);
                    Bitmap bitmap = ImageProcess.lumProcess(MyBitmap.getOrigin(), lumValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose resize");
                }
                //选择旋转图像的函数
                if (i == 4) {
                    float rotValue = MyBitmap.getRotValue();
                    rotValue += 5;
                    MyBitmap.setRotValue(rotValue);
                    Bitmap bitmap = ImageProcess.rotateProcess(MyBitmap.getOrigin(), rotValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", "choose rotate");
                }
                //选择沿X轴垂直反转的函数
                if (i == 5) {
                    Bitmap bitmap = ImageProcess.convertXProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    bitmap = ImageProcess.convertXProcess(MyBitmap.getOrigin());
                    MyBitmap.setOrigin(bitmap);
                    Log.i("ProcessImageActivity", "choose covert X");
                }
                //选择沿Y轴垂直反转的函数
                if (i == 6) {
                    Bitmap bitmap = ImageProcess.convertYProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    bitmap = ImageProcess.convertYProcess(MyBitmap.getOrigin());
                    MyBitmap.setOrigin(bitmap);
                    Log.i("ProcessImageActivity", "choose convert Y");
                }
                //选择模糊的函数
                if (i == 7) {
                    int raidus = MyBitmap.getRaidus();
                    raidus += 1;
                    MyBitmap.setRaidus(raidus);
                    Bitmap temp = Bitmap.createBitmap(MyBitmap.getOrigin());
                    Bitmap bitmap = ImageProcess.blurProcess(temp, raidus);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                    Log.i("ProcessImageActivity", ""+MyBitmap.getRaidus());
                    Log.i("ProcessImageActivity", "choose blur");
                }
                //选择滤镜的函数
                if (i == 8) {
                    Log.i("ProcessImageActivity", "choose filter");
                    showFilter();
                }
            }
            return true;
        }
    };

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_processimage);

        gestureDetector = new GestureDetector(ProcessImageActivity.this, onGestureListener);

        passedData = (PassedData) getIntent().getSerializableExtra("PassedData");

        InitViewById();
        setListener();


    }

    /*回收Bitmap*/
    @Override
    protected void onDestroy() {
        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void showFilter() {
        bitmap = new Filter().comicFilter(this, bitmap);
        process_image_view.setImageBitmap(bitmap);
    }
}
