package com.example.alienware.fantasy_image;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class PickImageActivity extends AppCompatActivity {
    /*声明View*/
    private Button camera_button;
    private Button album_button;
    private Button nextstep_button;
    private ImageView pick_image_view;

    /*声明跳转Activity返回码*/
    private static final int REQUEST_FROM_ALBUM = 22;
    private static final int REQUEST_FROM_CAMERA = 23;

    private Uri img_uri;

    private String sdPath;
    private String picPath;

    /*获取XML中的控件*/
    private void InitViewById() {
        camera_button = (Button)findViewById(R.id.from_camera);
        album_button = (Button)findViewById(R.id.from_album);
        nextstep_button = (Button)findViewById(R.id.next_step);
        pick_image_view = (ImageView)findViewById(R.id.pick_image_view);
    }

    /*判断相机Intent是否有效*/
    /*
    private boolean isIntentAvaliable(Context context,Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfoList.size()>0;
    }
    */
    /*设置监听*/
    private void setListener() {
        album_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(picPath);
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent album_intent = new Intent(Intent.ACTION_PICK);
                album_intent.setType("image/*");
                startActivityForResult(album_intent,REQUEST_FROM_ALBUM);
                /*
                img_uri = Uri.fromFile(file);
                Intent album_intent = new Intent("android.intent.action.GET_CONTENT");
                album_intent.putExtra(MediaStore.EXTRA_OUTPUT,img_uri);
                startActivityForResult(album_intent,REQUEST_FROM_ALBUM);
                */
            }
        });

        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(picPath);
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                img_uri = Uri.fromFile(file);
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,img_uri);
                startActivityForResult(camera_intent,REQUEST_FROM_CAMERA);


            }
        });

        nextstep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PickImageActivity.this,ProcessImageActivity.class);
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(img_uri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MyBitmap.setBmp(bitmap);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pickimage);

        InitViewById();
        setListener();

        sdPath = Environment.getExternalStorageDirectory().getPath();
        picPath = sdPath+"/"+"temp.png";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_FROM_ALBUM:
                    try {
                        img_uri = data.getData();
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(img_uri));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_FROM_CAMERA:
                    FileInputStream fileInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(picPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bitmap = BitmapFactory.decodeStream(fileInputStream);
                    break;
            }
            if (bitmap == null) {
                Toast.makeText(PickImageActivity.this,"No Pictiure",Toast.LENGTH_LONG).show();
            } else {
                pick_image_view.setImageBitmap(bitmap);
                nextstep_button.setEnabled(true);
            }
        }
    }
}
