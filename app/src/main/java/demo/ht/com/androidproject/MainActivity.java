package demo.ht.com.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import io.flutter.embedding.android.FlutterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 进入Flutter主Main页面
                 */
//                  startActivity(new Intent(MainActivity.this,FlutterActivity.class));


                /**
                 * 进入Flutter 固定页面 通过缓存进入缓存在Application中声明
                 * 切记:一定要声明App在AndroidManifest.xml中 (android:name=".App")
                 */
                startActivity(FlutterActivity.
                        withCachedEngine("a").
                        build(MainActivity.this));


                /**
                 * 进入Flutter页面 通过路由来指定页面
                 */
//                startActivity(FlutterActivity.
//                        withNewEngine().
//                        initialRoute("image_page").
//                        build(MainActivity.this));

            }
        });





    }

}