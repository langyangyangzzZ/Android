package demo.ht.com.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;

public class FlutterAppActivity extends FlutterActivity {

    public final static String INIT_PARAMS = "initParams";

    public static void start(Context context, String initParams) {
        Intent intent = new Intent(context, FlutterAppActivity.class);
        intent.putExtra(INIT_PARAMS, initParams);
        context.startActivity(intent);
    }

    String mInitParam;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInitParam = getIntent().getStringExtra(INIT_PARAMS);

    }

    /**
     * 传递初始化参数给Flutter
     * @return
     */
    @NonNull
    @Override
    public String getInitialRoute() {
        return mInitParam == null ? super.getInitialRoute() : mInitParam;

    }

//         使用在MyApplication预先初始化好的Flutter引擎以提升Flutter页面打开速度，
//         注意：在这种模式下会导致getInitialRoute 不被调用所以无法设置初始化参数
//        @Override
//        public String getCachedEngineId() {
//            return App.ENG_INED;
//        }
}
