package demo.ht.com.activity_pages;

import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.androidproject.FlutterAppActivity;
import demo.ht.com.androidproject.R;

import android.os.Bundle;
import android.view.View;

public class MethodChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method_channel);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlutterAppActivity.start(MethodChannelActivity.this,"",3);
            }
        });
    }
}