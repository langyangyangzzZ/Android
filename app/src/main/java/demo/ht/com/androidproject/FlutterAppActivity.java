package demo.ht.com.androidproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import demo.ht.com.plugins.BasicMessageChannelPlugin;
import demo.ht.com.plugins.EventChannelPlugin;
import demo.ht.com.plugins.MethodChannelPlugin;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class FlutterAppActivity extends FlutterActivity implements IShowMessage {
    private BasicMessageChannelPlugin basicMessageChannelPlugin;

    public final static String INIT_PARAMS = "initParams";
    /**
     * 0 给Flutter传递初始化数据
     * 1 使用BasicMsgChannel传递数据
     * 2 使用EventChannel传递当前电量
     * 3 使用MethodChannel获取数据
     */
    private static int mtype;

    public static void start(Context context, String initParams, int type) {
        mtype = type;
        Intent intent = new Intent(context, FlutterAppActivity.class);
        intent.putExtra(INIT_PARAMS, initParams);
        context.startActivity(intent);
    }

    String mInitParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //使用BasicMsgChannel传递数据
        String initParam = getIntent().getStringExtra(INIT_PARAMS);
        this.sendMessage(initParam);
    }

    /**
     * configureFlutterEngine优先级比onCreate高！
     *
     * @param flutterEngine
     */
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        Log.i("szjonCreate", mtype + "");


        if (mtype == 0) {
            // 给Flutter传递初始化数据
            mInitParam = getIntent().getStringExtra(INIT_PARAMS);
        } else if (mtype == 1) {
            //使用BasicMsgChannel传递数据
            basicMessageChannelPlugin = BasicMessageChannelPlugin.registerWith(flutterEngine.getDartExecutor().getBinaryMessenger(), FlutterAppActivity.this);
        } else if (mtype == 2) {
            //使用EventMessage传递当前电量  传递的是当前的flutterEngine 与 电量
            EventChannelPlugin.registerWith(flutterEngine.getDartExecutor(),
                    getIntent().getStringExtra(INIT_PARAMS));
        }else if(mtype == 3){
            MethodChannelPlugin.registerWith(flutterEngine.getDartExecutor(),this);
        }

    }


    /**
     * 传递初始化参数给Flutter
     *
     * @return
     */
    @NonNull
    @Override
    public String getInitialRoute() {
        return mInitParam == null ? super.getInitialRoute() : mInitParam;
    }

    /**
     * 使用在MyApplication预先初始化好的Flutter引擎以提升Flutter页面打开速度，
     * 注意：在这种模式下会导致getInitialRoute 不被调用所以无法设置初始化参数
     * 使用EventChannel传递数据必须注释掉！！！
     *
     * @return
     */
//    @Override
//    public String getCachedEngineId() {
//        return App.ENG_INED;
//    }


    @Override //BasicMessageChannel Flutter --> Android 数据
    public void onShowMessage(String message) {
        Log.i("szjonShowMessage", message);
    }


    @Override //BasicMessageChannel Android --> Flutter 数据
    public void sendMessage(String message) {
        Log.i("szjsendMessage", message);
        if (mtype == 1) {
            basicMessageChannelPlugin.send(message, FlutterAppActivity.this::onShowMessage);
        }
    }


}
