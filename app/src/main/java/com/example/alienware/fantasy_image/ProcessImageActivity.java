package com.example.alienware.fantasy_image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alienware.fantasy_image.Bean.PassedData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProcessImageActivity extends AppCompatActivity {
    /*声明View*/
    private Button edit_button;
    private Button save_button;
    private ImageView process_image_view;

    /*声明跳转Activity返回码*/
    private static final int REQUEST_FROM_ALBUM = 0;
    private static final int REQUEST_FROM_CAMERA = 1;

    private Bitmap bitmap;

    private PassedData passedData;
    /*手势检测*/
    private GestureDetector gestureDetector;
    /*获取XML中的控件*/
    private void InitViewById() {
        save_button = (Button)findViewById(R.id.save_photo);
        edit_button = (Button)findViewById(R.id.edit_photo);
        process_image_view = (ImageView)findViewById(R.id.process_image_view);
        bitmap = MyBitmap.getBmp();
        process_image_view.setImageBitmap(bitmap);
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
                String fileName = System.currentTimeMillis()+".jpg";
                File file = new File(appDir,fileName);

                /*写入文件夹中*/
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
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
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProcessImageActivity.this, ListFunctionActivity.class);
                startActivity(intent);
            }
        });
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX()-e1.getX();
            float y = e2.getY()-e1.getY();
            if (y > 0) {
                int valueNum = passedData.getValueNum();
                int  i= passedData.getFuncId();

                // 选择调节色调的函数
                if (i == 0) {
                    float hueValue = passedData.getValue1();
                    hueValue -= 3;
                    hueValue = (hueValue <= 0 ? 0.1f : hueValue);
                    Bitmap bitmap = ImageProcess.hueProcess(MyBitmap.getBmp(),hueValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择调节饱和度的函数
                if (i == 1) {
                    float satValue = passedData.getValue1();
                    satValue -= 3;
                    satValue = (satValue <= 0 ? 0.1f : satValue);
                    Bitmap bitmap = ImageProcess.saturationProcess(MyBitmap.getBmp(),satValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择调节透明度的函数
                if (i == 2) {
                    float tranValue = passedData.getValue1();
                    tranValue -= 3;
                    tranValue = (tranValue <= 0 ? 0.1f : tranValue);
                    Bitmap bitmap = ImageProcess.transparencyProcess(MyBitmap.getBmp(),tranValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择重新放缩图像的函数
                if (i == 3) {
                    int resizeW = (int)passedData.getValue1();
                    int resizeH = (int)passedData.getValue2();
                    resizeW -= 100;
                    resizeH -= 100;
                    if (resizeW <= 0 || resizeH <= 0) {
                        resizeH = 100;
                        resizeW = 100;
                    }
                    Bitmap bitmap = ImageProcess.resizeProcess(MyBitmap.getBmp(),resizeW,resizeH);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);

                }
                //选择旋转图像的函数
                if (i == 4) {
                    float rotValue = passedData.getValue1();
                    Bitmap bitmap = ImageProcess.rotateProcess(MyBitmap.getBmp(),rotValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择沿X轴垂直反转的函数
                if (i == 5) {
                    Bitmap bitmap = ImageProcess.convertXProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择沿Y轴垂直反转的函数
                if (i == 6) {
                    Bitmap bitmap = ImageProcess.convertYProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择模糊的函数
                if (i == 7) {
                    int raidus = (int)passedData.getValue1();
                    raidus -= 3;
                    raidus = (raidus <= 0 ? 1 : raidus);
                    Bitmap bitmap = ImageProcess.blurProcess(MyBitmap.getBmp(),raidus);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }

            } else {
                int valueNum = passedData.getValueNum();
                int  i= passedData.getFuncId();

                // 选择调节色调的函数
                if (i == 0) {
                    float hueValue = passedData.getValue1();
                    hueValue += 3;
                    Bitmap bitmap = ImageProcess.hueProcess(MyBitmap.getBmp(),hueValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择调节饱和度的函数
                if (i == 1) {
                    float satValue = passedData.getValue1();
                    satValue += 3;
                    Bitmap bitmap = ImageProcess.saturationProcess(MyBitmap.getBmp(),satValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择调节透明度的函数
                if (i == 2) {
                    float tranValue = passedData.getValue1();
                    tranValue += 3;
                    Bitmap bitmap = ImageProcess.transparencyProcess(MyBitmap.getBmp(),tranValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择重新放缩图像的函数
                if (i == 3) {
                    int resizeW = (int)passedData.getValue1();
                    int resizeH = (int)passedData.getValue2();
                    resizeW += 100;
                    resizeH += 100;
                    Bitmap bitmap = ImageProcess.resizeProcess(MyBitmap.getBmp(),resizeW,resizeH);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);

                }
                //选择旋转图像的函数
                if (i == 4) {
                    float rotValue = passedData.getValue1();
                    Bitmap bitmap = ImageProcess.rotateProcess(MyBitmap.getBmp(),rotValue);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择沿X轴垂直反转的函数
                if (i == 5) {
                    Bitmap bitmap = ImageProcess.convertXProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择沿Y轴垂直反转的函数
                if (i == 6) {
                    Bitmap bitmap = ImageProcess.convertYProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
                //选择模糊的函数
                if (i == 7) {
                    int raidus = (int)passedData.getValue1();
                    raidus += 3;
                    Bitmap bitmap = ImageProcess.blurProcess(MyBitmap.getBmp(),raidus);
                    MyBitmap.setBmp(bitmap);
                    process_image_view.setImageBitmap(bitmap);
                }
            }
            return true;
        }
    };

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_processimage);

        gestureDetector = new GestureDetector(ProcessImageActivity.this,onGestureListener);

        passedData = (PassedData)getIntent().getSerializableExtra("PassedData");

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
}
