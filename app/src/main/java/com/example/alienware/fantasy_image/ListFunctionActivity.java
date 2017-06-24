package com.example.alienware.fantasy_image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private String[] function_names = new String[] {"色调","饱和度","亮度",
            "旋转","镜像旋转","高斯模糊","漫画滤镜", "卡通滤镜", "浮雕滤镜",
            "水粉滤镜", "阴影滤镜", "素描滤镜"};

    /*图像功能默认参数值*/
    private float hueValue = 3;
    private float satValue = 3;
    private float lumValue = 3;
    private float rotValue = 0;
    private int raidus = 1;

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
                    MyBitmap.setHueValue(hueValue);
                    PassedData passedData = new PassedData(0,hueValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择调节饱和度的函数
                if (i == 1) {
                    MyBitmap.setSatValue(satValue);
                    PassedData passedData = new PassedData(1,satValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择调节亮度的函数
                if (i == 2) {
                    MyBitmap.setLumValue(lumValue);
                    PassedData passedData = new PassedData(2,lumValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择旋转图像的函数
                if (i == 3) {
                    MyBitmap.setRotValue(rotValue);
                    PassedData passedData = new PassedData(3,rotValue);
                    intent.putExtra("PassedData",passedData);
                }
                //选择垂直反转的函数
                if (i == 4) {
                    PassedData passedData = new PassedData(4);
                    intent.putExtra("PassedData",passedData);
                }
                //选择模糊的函数
                if (i == 5) {
                    MyBitmap.setRaidus(raidus);
                    PassedData passedData = new PassedData(5);
                    intent.putExtra("PassedData",passedData);
                }
                //选择滤镜处理的函数
                if (i >= 6) {
                    PassedData passedData = new PassedData(i);
                    intent.putExtra("PassedData",passedData);
                }

                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);

        setContentView(R.layout.activity_listfunction);

        InitViewById();
        setListener();
    }

}
