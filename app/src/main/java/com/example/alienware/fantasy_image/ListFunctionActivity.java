package com.example.alienware.fantasy_image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.alienware.fantasy_image.Bean.PassedData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListFunctionActivity extends AppCompatActivity {
    /*声明View*/
    private ListView listView;

    /*声明List和String*/
    private List<Map<String,Object>> list_data = new ArrayList<>();
    private String[] function_names = new String[] {"图像色调","图像饱和度","图像亮度","图像透明度","图像旋转","图像水平镜像旋转","图像垂直镜像旋转","高斯模糊"};

    /*图像功能默认参数值*/
    private float hueValue = 3;
    private float satValue = 3;
    private float lumValue = 3;
    private float tranValue = 3;
    private int resizeW = 300;
    private int resizeH = 300;
    private float rotValue = 45;
    private int raidus = 3;

    /*获取XML中的控件*/
    private void InitViewById() {
        listView = (ListView)findViewById(R.id.function_list);

        /*实现ListView的适配器*/
        for (int i = 0; i < function_names.length; i++) {
            Map<String,Object> temp = new LinkedHashMap<>();
            temp.put("function",function_names[i]);
            list_data.add(temp);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list_data,R.layout.list_item,new String[]{"function"},new int[] {R.id.function_item});
        listView.setAdapter(simpleAdapter);
    }

    /*设置监听*/
    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*判断Bitmap是否为空*/
                if (MyBitmap.getBmp() == null) {
                    return;
                }
                Intent intent = new Intent(ListFunctionActivity.this, ProcessImageActivity.class);
                // 选择调节色调的函数
                if (i == 0) {
                    Bitmap bitmap = ImageProcess.hueProcess(MyBitmap.getBmp(),hueValue);
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(0,hueValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择调节饱和度的函数
                if (i == 1) {
                    Bitmap bitmap = ImageProcess.saturationProcess(MyBitmap.getBmp(),satValue);
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(1,satValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择调节透明度的函数
                if (i == 2) {
                    Bitmap bitmap = ImageProcess.transparencyProcess(MyBitmap.getBmp(),tranValue);
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(2,tranValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择重新放缩图像的函数
                if (i == 3) {
                    Bitmap bitmap = ImageProcess.resizeProcess(MyBitmap.getBmp(),resizeW,resizeH);
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(3,resizeW,resizeH);
                    intent.putExtra("PassedData",passedData);
                }
                //选择旋转图像的函数
                if (i == 4) {
                    Bitmap bitmap = ImageProcess.rotateProcess(MyBitmap.getBmp(),rotValue);
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(4,rotValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择沿X轴垂直反转的函数
                if (i == 5) {
                    Bitmap bitmap = ImageProcess.convertXProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(5);
                    intent.putExtra("PassedData",passedData);
                }
                //选择沿Y轴垂直反转的函数
                if (i == 6) {
                    Bitmap bitmap = ImageProcess.convertYProcess(MyBitmap.getBmp());
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(6);
                    intent.putExtra("PassedData",passedData);
                }
                //选择模糊的函数
                if (i == 7) {
                    Bitmap bitmap = ImageProcess.blurProcess(MyBitmap.getBmp(),raidus);
                    MyBitmap.setBmp(bitmap);
                    PassedData passedData = new PassedData(7);
                    intent.putExtra("PassedData",passedData);
                }
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listfunction);

        InitViewById();
        setListener();
    }

}
