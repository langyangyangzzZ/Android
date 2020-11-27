package demo.ht.com.activity_pages;

import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.androidproject.FlutterAppActivity;
import demo.ht.com.androidproject.R;
import demo.ht.com.utils.BatteryReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class EventChannelActivity extends AppCompatActivity {

    BatteryReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_channel);

        TextView textView = findViewById(R.id.tv);

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver(textView);
        registerReceiver(receiver, filter);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlutterAppActivity.start(EventChannelActivity.this,textView.getText().toString(),2);
            }
        });


    }
}