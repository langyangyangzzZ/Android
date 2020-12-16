package demo.ht.com.activity_pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.androidproject.App;
import demo.ht.com.androidproject.FlutterAppActivity;
import demo.ht.com.androidproject.R;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.android.TransparencyMode;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

public class ToFlutterPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_flutter_page);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 进入Flutter主Main页面
                 */
//                Intent intent = new Intent(ToFlutterPageActivity.this, FlutterActivity.class);
//                startActivity(intent);

                
                /*
                 * 进入Flutter 固定页面 通过缓存进入缓存在Application中声明
                 * 切记:一定要声明App在AndroidManifest.xml中 (android:name=".App")
                 */
//                startActivity(FlutterActivity.
//                        withCachedEngine(App.ENG_INED).
//                        build(ToFlutterPageActivity.this));


                //过渡动画跳转Flutter页面
                FlutterAppActivity.start(ToFlutterPageActivity.this,"我是过渡动画",4);

                /*
                 * 进入Flutter页面 通过路由来指定页面
                 */
//                startActivity(FlutterActivity.
//                        withNewEngine().
//                        initialRoute("text_page").
//                        build(ToFlutterPageActivity.this));

                
                /*
                 * 进入Flutter使用FlutterFragment
                 */
//             getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment, FlutterFragment.createDefault()).commit();

            }
        });



    }


}