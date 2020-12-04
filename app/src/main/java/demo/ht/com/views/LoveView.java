package demo.ht.com.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.RESTART;

public class LoveView extends View {

    private final int[] colors = {Color.CYAN, Color.YELLOW,  Color.LTGRAY, Color.GREEN, Color.RED};
    public Random random = new Random();
    Path path = new Path();
    private Paint paint = new Paint();
    List<LoveBean> mList = new ArrayList<>();
    private int width;//屏幕宽
    private int height;//屏幕高
    private ValueAnimator valueAnimator;
    private boolean isAnimation  = false;//是否添加动画
    private float x;
    private float y;
    private int animatedValue;

    public LoveView(Context context) {
        this(context, null);
    }

    public LoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        //获取屏幕宽度
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;

        //初始化爱心
        initData(300,300);


    }


    public void addAnimation(View bt){
        //重新初始化Love和现在的位置
        initData(bt.getX()+bt.getMeasuredWidth() / 2, bt.getY()  - bt.getMeasuredHeight() / 2 );
        //重新初始化Love动画
        initLove();
        //重新绘制
        invalidate();
    }

    /**
     * @param x 初始化x位置
     * @param y 初始化y位置
     *          用来初始化爱心
     */
    private void initData(float x, float y) {
        mList.clear();
        for (int i = 0; i < LoveBean.number; i++) {
            LoveBean loveBean = new LoveBean();
            //设置随颜色
            loveBean.color = colors[random.nextInt(colors.length)];

            /**
             * 爱心的偏移位置
             * random.nextDouble()随机的是0-1之间的小数
             * random.nextInt(10 - 5 + 1) + 5; 随机的是5-10之间的整数
             */
            loveBean.deviation.x = (float) (random.nextDouble() );
            loveBean.deviation.y = random.nextInt(7 - 5 + 1) + 5;

            //现在的位置 = 手指点击的位置 + 偏移的位置  (偏移的位置是为了有一种参差不齐的感觉!)
            loveBean.pointF.x = x + loveBean.deviation.x ;
            loveBean.pointF.y = y + loveBean.deviation.y ;

            loveBean.alpha = 255;

            mList.add(loveBean);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthmeasure = measure(widthMeasureSpec);
        int heightmeasure = measure(heightMeasureSpec);
        setMeasuredDimension(widthmeasure, heightmeasure);
    }

    private int measure(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.UNSPECIFIED) {
            result = 200;
        } else {
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //循环绘制爱心
        for (int j = 0; j < mList.size(); j++) {
            LoveBean loveBean = mList.get(j);
            //设置颜色
            paint.setColor(loveBean.color);
            // 重置画板
            path.reset();
            // 得到屏幕的长宽的一半
            // 路径的起始点
            Log.i("szjonDraw",loveBean.pointF.x+"\t"+(loveBean.pointF.y - 5 * LoveBean.radius));
            path.moveTo(loveBean.pointF.x, loveBean.pointF.y - 5 * LoveBean.radius);
            // 根据心形函数画图
            for (double i = 0; i <= 2 * Math.PI; i += 0.001) {
                float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));
                float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));
                x *= LoveBean.radius;
                y *= LoveBean.radius;
                x = loveBean.pointF.x - x;
                y = loveBean.pointF.y - y;
                path.lineTo(x, y);
            }
            //设置透明度
            paint.setAlpha(loveBean.alpha);
            canvas.drawPath(path, paint);
        }
    }

    private void initLove() {
        if (valueAnimator != null) {
            //移除上一个valueAnimator
            valueAnimator.removeAllUpdateListeners();

        }
        valueAnimator = ValueAnimator.ofInt(255, 100, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (int) animation.getAnimatedValue();
                //设置向上移动
                initAnimator();
                //设置透明度
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).alpha = animatedValue;
                }

            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    @Override
    public void clearAnimation() {
        super.clearAnimation();
        valueAnimator.removeAllUpdateListeners();
    }

    //向上移动动画
    private void initAnimator() {
        for (int i = 0; i < mList.size(); i++) {
            LoveBean loveBean = mList.get(i);

            //新的移动位置 = 当前位置 + 偏移位置
            //因为是向上移动,所以Y是-去偏移位置
            float dx = loveBean.pointF.x + loveBean.deviation.x;
            float dy = loveBean.pointF.y - loveBean.deviation.y;
            loveBean.pointF = new PointF(dx, dy);
        }
        invalidate();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
        }
        return true;
    }


    /**
     *
     * @param number 设置红心数量
     */
    public void  setLoveNumber(int number){
        LoveBean.number = number;
    }

    /**
     *
     * @param radius 设置红心半径
     */
    public void  setLoveRadius(int radius){
        LoveBean.radius = radius;
    }
}

class LoveBean {
    //爱心的位置
    PointF pointF = new PointF(-100, -100);

    //爱心的偏移位置
    PointF deviation = new PointF();
    //爱心的颜色
   int color ;
    //透明度
    int alpha;
    //红心个数 默认初始化5个
     public static int number = 5;
     //红心半径
    public static int  radius = 5;
}
