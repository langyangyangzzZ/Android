package demo.ht.com.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.embedding.android.FlutterActivity;

public class FlutterAppActivity extends FlutterActivity  implements IShowMessage{
    private BasicMessageChannelPlugin basicMessageChannelPlugin;

    public final static String INIT_PARAMS = "initParams";
    /**
     * 0 给Flutter传递初始化数据
     * 1 使用BasicMsgChannel传递数据
     */
    private static int mtype ;

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
        Log.i("szjonCreate",mtype+"");
        if (mtype == 0) {
           // 给Flutter传递初始化数据
            mInitParam = getIntent().getStringExtra(INIT_PARAMS);

        }else if(mtype == 1){
            //使用BasicMsgChannel传递数据
            basicMessageChannelPlugin = BasicMessageChannelPlugin.registerWith(getFlutterEngine().getDartExecutor(), this);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mtype == 1) {
            String initParam = getIntent().getStringExtra(INIT_PARAMS);
            this.sendMessage(initParam);
        }
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


    @Override
    public void onShowMessage(String message) {
        Log.i("szjonShowMessage",message);
    }

    @Override
    public void sendMessage(String message) {
        Log.i("szjsendMessage",message);
        if (basicMessageChannelPlugin == null) {
            return;
        }
        basicMessageChannelPlugin.send(getIntent().getStringExtra(INIT_PARAMS),this::onShowMessage);
    }
}
