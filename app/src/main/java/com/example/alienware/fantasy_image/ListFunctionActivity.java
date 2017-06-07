package com.example.alienware.fantasy_image;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListFunctionActivity extends AppCompatActivity {
    /*声明View*/
    private ListView listView;

    private List<Map<String,Object>> list_data = new ArrayList<>();
    private String[] function_names = new String[] {"裁剪","旋转","缩放","透明度","镜像","直方图均衡化","模糊","浮雕","边缘","锐化"};


    /*获取XML中的控件*/
    private void InitViewById() {
        listView = (ListView)findViewById(R.id.function_list);

        for (int i = 0; i < 10; i++) {
            Map<String,Object> temp = new LinkedHashMap<>();
            temp.put("function",function_names[i]);
            list_data.add(temp);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list_data,R.layout.list_item,new String[]{"function"},new int[] {R.id.function_item});
        listView.setAdapter(simpleAdapter);
    }

    /*设置监听*/
    private void setListener() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listfunction);

        InitViewById();
        setListener();
    }

}
