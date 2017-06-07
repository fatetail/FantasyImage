package com.example.alienware.fantasy_image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ProcessImageActivity extends AppCompatActivity {
    /*声明View*/
    private Button camera_button;
    private Button album_button;
    private Button nextstep_button;
    private ImageView pick_image_view;

    /*声明跳转Activity返回码*/
    private static final int REQUEST_FROM_ALBUM = 0;
    private static final int REQUEST_FROM_CAMERA = 1;

    private Uri img_uri;

    /*获取XML中的控件*/
    private void InitViewById() {
        camera_button = (Button)findViewById(R.id.from_camera);
        album_button = (Button)findViewById(R.id.from_album);
        nextstep_button = (Button)findViewById(R.id.next_step);
        pick_image_view = (ImageView)findViewById(R.id.pick_image_view);
    }

    /*设置监听*/
    private void setListener() {

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
