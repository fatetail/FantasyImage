package com.example.alienware.fantasy_image;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.alienware.fantasy_image.Bean.PassedData;
import com.example.alienware.fantasy_image.Factory.FilterFactory;
import com.example.alienware.fantasy_image.Factory.ProcessImageFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProcessImageActivity extends AppCompatActivity {
    /*声明View*/
    private Button edit_button;
    private Button save_button;
    private Button cancel_button;
    private ImageView process_image_view;

    /*声明跳转Activity返回码*/
    private static final int REQUEST_FROM_ALBUM = 0;
    private static final int REQUEST_FROM_CAMERA = 1;

    private static final int MINUS = -1;
    private static final int ADD = 1;

    private Bitmap bitmap;

    private PassedData passedData;

    private static boolean firstOpen = true;
    private static boolean isChange = false;
    /*手势检测*/
    private GestureDetector gestureDetector;

    /*获取XML中的控件*/
    private void InitViewById() {
        save_button = (Button) findViewById(R.id.save_photo);
        edit_button = (Button) findViewById(R.id.edit_photo);
        cancel_button = (Button) findViewById(R.id.cancel);
        process_image_view = (ImageView) findViewById(R.id.process_image_view);
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
                            .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int k) {
                                    MyBitmap.setOrigin(MyBitmap.getBmp());
                                    Intent intent = new Intent(ProcessImageActivity.this, ListFunctionActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int k) {
                                }
                            })
                            .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MyBitmap.setBmp(MyBitmap.getOrigin());
                                    process_image_view.setImageBitmap(bitmap);
                                    Intent intent = new Intent(ProcessImageActivity.this, ListFunctionActivity.class);
                                    startActivity(intent);
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
                bitmap = MyBitmap.getBmp();
                process_image_view.setImageBitmap(bitmap);
                isChange = false;
            }
        });
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();
            if (passedData == null) {
                return true;
            }
            int id = passedData.getFuncId();
            bitmap = ProcessImageFactory.processImage(y, id);
            if (id >= 6 && !isChange) {
                FilterFactory.processImage(id, ProcessImageActivity.this);
                isChange = true;
            }
            if (bitmap != null) {
                process_image_view.setImageBitmap(bitmap);
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
        SysApplication.getInstance().addActivity(this);

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

    @Override
    public void onBackPressed() {
        Dialog builder = new AlertDialog.Builder(ProcessImageActivity.this).setTitle("是否保存?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        MyBitmap.setOrigin(MyBitmap.getBmp());
                        Intent intent = new Intent(ProcessImageActivity.this, PickImageActivity.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ProcessImageActivity.this, PickImageActivity.class);
                        startActivity(intent);
                    }
                }).create();
        builder.show();
    }
}
