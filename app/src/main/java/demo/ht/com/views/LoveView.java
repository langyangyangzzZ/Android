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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.RESTART;

public class LoveView extends View {

    private int[] colors = {Color.CYAN, Color.YELLOW, Color.WHITE, Color.LTGRAY, Color.GREEN, Color.RED};
    public Random random = new Random();
    //心形半径
    private float rate = 5;
    Path path = new Path();
    private Paint paint = new Paint();
    List<LoveBean> mList = new ArrayList<>();
    private int width;//屏幕宽
    private int height;//屏幕高
    private ValueAnimator valueAnimator;

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
        initData(-100, -100);


    }

    /**
     * @param x 初始化x位置
     * @param y 初始化y位置
     *          用来初始化爱心
     */
    private void initData(float x, float y) {
        mList.clear();
        for (int i = 0; i < 5; i++) {
            LoveBean loveBean = new LoveBean();
            //设置随颜色
            loveBean.color.add(colors[random.nextInt(colors.length)]);

            //爱心的偏移位置
            loveBean.deviation.x = (float) (random.nextDouble() + 2);
            loveBean.deviation.y = random.nextInt(10);

            //现在的位置 = 手指点击的位置 + 偏移的位置  (偏移的位置是为了有一种参差不齐的感觉!)
            loveBean.pointF.x = x + loveBean.deviation.x;
            loveBean.pointF.y = y + loveBean.deviation.y;

            mList.add(loveBean);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthmeasure = measure(widthMeasureSpec);
        int heightmeasure = measure(heightMeasureSpec);
        setMeasuredDimension(widthmeasure, heightmeasure);
        Log.i("szjwidth2", "");
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

//            canvas.drawCircle(loveBean.pointF.x,loveBean.pointF.y,30,paint);


            // 重置画板
            path.reset();
            // 得到屏幕的长宽的一半
            // 路径的起始点
            path.moveTo(loveBean.pointF.x, loveBean.pointF.y - 5 * rate);
            // 根据心形函数画图
            for (double i = 0; i <= 2 * Math.PI; i += 0.001) {
                float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));
                float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));
                x *= rate;
                y *= rate;
                x = loveBean.pointF.x - x;
                y = loveBean.pointF.y - y;
                path.lineTo(x, y);
            }
            canvas.drawPath(path, paint);
        }
    }

    private void initLove() {
        paint.setAlpha(255);
        isImplement = false;
        if (valueAnimator != null) {
            //移除上一个valueAnimator
            valueAnimator.removeAllUpdateListeners();

        }
        valueAnimator = ValueAnimator.ofInt(255, 100, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                //设置向上移动
                initAnimator();
                //设置透明度
                paint.setAlpha(animatedValue);

            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();

    }

    private void initAnimator() {
        for (int i = 0; i < mList.size(); i++) {
            LoveBean loveBean = mList.get(i);

            for (int j = 0; j < loveBean.color.size(); j++) {
                Log.i("szjloveBeancolor", loveBean.color.get(j) + "");

                paint.setColor(loveBean.color.get(j));

            }

            float dx = loveBean.pointF.x + loveBean.deviation.x;
            float dy = loveBean.pointF.y - loveBean.deviation.y;
            loveBean.pointF = new PointF(dx, dy);

        }
        invalidate();

    }

    Boolean isImplement = true;//是否执行完动画

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重新初始化Love和现在的位置
                initData(event.getX(), event.getY());
                //重新初始化Love动画
                initLove();
                //重新绘制
                invalidate();

                break;
        }
        return true;
    }
}

class LoveBean {
    //爱心的位置
    PointF pointF = new PointF(-100, -100);

    //爱心的偏移位置
    PointF deviation = new PointF();
    //爱心的颜色
    ArrayList<Integer> color = new ArrayList<>();
}
