package com.example.alienware.fantasy_image;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
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
    private static final int CUT_OK = 24;
    private Uri img_uri;

    private String sdPath;
    private String picPath;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /*获取XML中的控件*/
    private void InitViewById() {
        camera_button = (Button) findViewById(R.id.from_camera);
        album_button = (Button) findViewById(R.id.from_album);
        nextstep_button = (Button) findViewById(R.id.next_step);
        pick_image_view = (ImageView) findViewById(R.id.pick_image_view);
    }

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
                startActivityForResult(album_intent, REQUEST_FROM_ALBUM);

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
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri);
                startActivityForResult(camera_intent, REQUEST_FROM_CAMERA);


            }
        });

        nextstep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyBitmap.getBmp() != null) {
                    Intent intent = new Intent(PickImageActivity.this, ProcessImageActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);

        setContentView(R.layout.activity_pickimage);

        InitViewById();
        setListener();

        sdPath = Environment.getExternalStorageDirectory().getPath();
        picPath = sdPath + "/" + "temp.png";
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /*调用系统函数库进行图片的裁剪*/
    private void clipPhoto(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setFreeStyleCropEnabled(true);
        uCrop.withOptions(options);
        uCrop.start(this);
    }


    /*将摄像机或者相册获取的照片设置在程序中*/
    private void setPicToView(Bitmap bitmap) {
        pick_image_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        pick_image_view.setImageBitmap(bitmap);
        MyBitmap.setBmp(bitmap);
        MyBitmap.setOrigin(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_FROM_ALBUM:
                    try {
                        if (data != null) {
                            clipPhoto(data.getData());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_FROM_CAMERA:
                    File file = new File(picPath);
                    if (file.exists()) {
                        clipPhoto(Uri.fromFile(file));
                    }

                    break;
                case UCrop.REQUEST_CROP:

                    Uri croppedFileUri = UCrop.getOutput(data);
                    FileInputStream inStream = null;
                    try {
                        inStream = new FileInputStream(new File(croppedFileUri.getPath()));
                        bitmap = BitmapFactory.decodeStream(inStream);
                        setPicToView(bitmap);
                        nextstep_button.setEnabled(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            inStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    break;
                case UCrop.RESULT_ERROR:
                    Toast.makeText(this, "裁剪失败", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PickImageActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PickImage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}
