package com.example.alienware.fantasy_image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button start_button, quit_button;   // 首页开始和结束按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /* 获取XML中的开始和结束按钮 */
        start_button = (Button) this.findViewById(R.id.start_button);
        quit_button = (Button)this.findViewById(R.id.quit_button);

        /* 开始按钮的监听函数 */
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PickImageActivity.class);
                startActivity(intent);
            }
        });

        /**/
    }
}
