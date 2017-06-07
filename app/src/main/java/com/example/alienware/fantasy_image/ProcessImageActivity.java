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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),fileName,null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE),fileName);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_processimage);

        InitViewById();
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
