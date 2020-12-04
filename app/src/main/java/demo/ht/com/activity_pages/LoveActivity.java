package demo.ht.com.activity_pages;

import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.androidproject.R;
import demo.ht.com.views.LoveView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        LoveView loveView = findViewById(R.id.loveView);

        Button bt1 = findViewById(R.id.bt1);
        //点赞按钮
        bt1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loveView.setLoveNumber(10);
                loveView.setLoveRadius(10);
                loveView.addAnimation(bt1);
            }
        });

        View bt3 = findViewById(R.id.bt3);
        //转发按钮
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loveView.setLoveNumber(1);
                loveView.setLoveRadius(5);
                loveView.addAnimation(bt3);
            }
        });
    }
}