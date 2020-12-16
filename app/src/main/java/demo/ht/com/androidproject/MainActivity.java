package demo.ht.com.androidproject;


import android.content.Intent;
import android.os.Bundle;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.activity_pages.BasicMsgChannelActivity;
import demo.ht.com.activity_pages.BezierCurveActivity;
import demo.ht.com.activity_pages.BubbleActivity;
import demo.ht.com.activity_pages.EventChannelActivity;
import demo.ht.com.activity_pages.LoveActivity;
import demo.ht.com.activity_pages.MethodChannelActivity;
import demo.ht.com.activity_pages.SnowActivity;
import demo.ht.com.activity_pages.ToFlutterDataActivity;
import demo.ht.com.activity_pages.ToFlutterPageActivity;
import demo.ht.com.activity_pages.WaveActivity;
import demo.ht.com.beans.AppleBean;
import demo.ht.com.utils.ToastUtil;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
         * 跳转Flutter页面
         */
        findViewById(R.id.bt1).setOnClickListener(v -> start(ToFlutterPageActivity.class));

        /*
         * 给Flutter传递初始化数据
         */
        findViewById(R.id.bt2).setOnClickListener(v -> start(ToFlutterDataActivity.class));


        //  使用BasicMessageChannel互传消息
        findViewById(R.id.bt3).setOnClickListener(v -> start(BasicMsgChannelActivity.class));

        //  使用EventChannel使Android给Flutter发送消息
        findViewById(R.id.bt4).setOnClickListener(v -> start(EventChannelActivity.class));

        //  使用MethodMessage接收Flutter发送的消息
        findViewById(R.id.bt5).setOnClickListener(v -> start(MethodChannelActivity.class));

        /*
         * 雪花页面
         */
        findViewById(R.id.bt6).setOnClickListener(v -> start(SnowActivity.class));

        /*
         * 贝塞尔曲线
         */
        findViewById(R.id.bt7).setOnClickListener(v -> start(BezierCurveActivity.class));

        /*
         * 爱心
         */
        findViewById(R.id.bt8).setOnClickListener(v -> start(LoveActivity.class));

        /*
         * 贝塞尔波浪效果
         */
        findViewById(R.id.bt9).setOnClickListener(v -> start(WaveActivity.class));

        /*
          气泡背景页
         */
        findViewById(R.id.bt10).setOnClickListener(v -> start(BubbleActivity.class));

        /*
         * Toast
         */
        findViewById(R.id.bt11).setOnClickListener(v -> showToast());

        findViewById(R.id.bt12).setOnClickListener(v -> initquote());
    }

    /**
     * 强软弱虚
     */
    private void initquote() {

        //强引用
        AppleBean appleBean = new AppleBean();


        /*//软引用
        SoftReference<AppleBean> sf = new SoftReference<AppleBean>(appleBean);
        //释放强引用
        appleBean = null;
        AppleBean sfAppleBean = sf.get();
        sfAppleBean.setColor(222222);
        */


       /* //弱引用
        WeakReference<AppleBean> wf = new WeakReference<AppleBean>(appleBean);
        //释放强引用
        appleBean = null;
        //有时候会返回null
        //弱引用是在第二次垃圾回收时回收，短时间内通过弱引用取对应的数据，
        // 可以取到，当执行过第二次垃圾回收时，将返回null。
        AppleBean wfAppleBean = wf.get();
        if(wfAppleBean != null){
                wfAppleBean.setColor(33333);
        }
        */


        //虚引用
        PhantomReference<AppleBean> pf = new PhantomReference<AppleBean>(appleBean,new ReferenceQueue<>());
        //释放强引用
        appleBean = null;
        AppleBean pfAppleBean = pf.get();
        if (pfAppleBean != null) {
            pfAppleBean.setColor(444444);
        }
        //垃圾回收标志
        pf.isEnqueued();
    }

    int i = 0;
    private void showToast() {
        ToastUtil.showToast(this,i+++"");
    }

    private void start(Class<?> loveActivityClass) {
        startActivity(new Intent(this, loveActivityClass));
    }

}