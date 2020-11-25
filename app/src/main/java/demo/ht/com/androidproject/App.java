package demo.ht.com.androidproject;

import android.app.Application;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class App extends Application {

    private FlutterEngine fe;

    @Override
    public void onCreate() {
        super.onCreate();
        //Flutter引擎
        fe = new FlutterEngine(this);
        /**
         * 设置要缓存的页面
         */
        fe.getNavigationChannel().setInitialRoute("image_page");
        //通过engine_id唯一标识来缓存
        fe.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        FlutterEngineCache
                .getInstance()
                .put("a", fe);

    }


    /**
     * onTerminate()当App销毁时执行
     */
    @Override
    public void onTerminate() {
        //销毁flutter引擎
        fe.destroy();
        super.onTerminate();
    }
}
