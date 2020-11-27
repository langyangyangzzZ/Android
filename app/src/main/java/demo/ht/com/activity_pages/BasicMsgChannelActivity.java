package demo.ht.com.activity_pages;

import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.androidproject.FlutterAppActivity;
import demo.ht.com.androidproject.R;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class BasicMsgChannelActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_msg_channel);

        EditText edit = findViewById(R.id.edit);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlutterAppActivity.start(BasicMsgChannelActivity.this,edit.getText().toString(),1);
            }
        });
    }
}