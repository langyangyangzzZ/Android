package demo.ht.com.androidproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.activity_pages.BasicMsgChannelActivity;
import demo.ht.com.activity_pages.ToFlutterDataActivity;
import demo.ht.com.activity_pages.ToFlutterPageActivity;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterFragment;

public class MainActivity extends AppCompatActivity {
    public final static String INIT_PARAMS = "initParams";
    BasicMessageChannelPlugin basicMessageChannelPlugin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * 跳转Flutter页面
         */
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToFlutterPageActivity.class));
            }
        });

        /**
         * 给Flutter传递初始化数据
         */
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToFlutterDataActivity.class));
            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BasicMsgChannelActivity.class));
            }
        });
    }

}