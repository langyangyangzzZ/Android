package demo.ht.com.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.Nullable;

import static java.lang.Math.sin;

public class BubbleView  extends View {

    private Paint mPaint;
    private int width;
    private int height;
    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimator2;

    public BubbleView(Context context) {
        this(context,null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);

        //获取屏幕宽度
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;//屏幕宽
        height = dm.heightPixels;//屏幕高
        initData();
    }
    //用来存放BubbleBean
    ArrayList<BubbleBean> mList = new ArrayList<>();

    Random mRandom = new Random();
    private void initData() {
        for (int i = 0; i < 40; i++) {
            BubbleBean bubbleBean = new BubbleBean();

            int dx = (int) (width * mRandom.nextDouble());
            int dy = (int) (height * mRandom.nextDouble());

            bubbleBean.position = new PointF(dx,dy);

            /**
             *mRandom.nextInt(150 - 50 + 1) + 50获取的是150 - 50之间的值
             */
            bubbleBean.radius = mRandom.nextInt(150 - 50 + 1) + 50;

            /**
             *  随机透明度
             */
            bubbleBean.alpha = mRandom.nextInt(200);

            /**
             * 随机颜色  255,255,255代表白色
             */
            bubbleBean.color = Color.argb( bubbleBean.alpha,255,255,255);

            /**
             * 初始化偏移位置
             */
            bubbleBean.deviation = new PointF(mRandom.nextInt(21) - 10, mRandom.nextInt(21) - 10);
            Log.i("szjmRandom",mRandom.nextInt(21) - 10+"");

           bubbleBean.pointFDown  =  bubbleBean.deviation;

            mList.add(bubbleBean);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mList.size(); i++) {
            BubbleBean bubbleBean = mList.get(i);

            mPaint.setColor(bubbleBean.color);
            canvas.drawCircle(bubbleBean.position.x,bubbleBean.position.y,bubbleBean.radius,mPaint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("szjonTouchEvent1","ACTION_DOWN");
                if (valueAnimator2 != null) {
                    valueAnimator2.removeAllUpdateListeners();

                }
                initAnimation();
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("szjonTouchEvent2","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                if (valueAnimator != null) {
                    valueAnimator.removeAllUpdateListeners();

                }
                initAnimation2();
                Log.i("szjonTouchEvent3","ACTION_UP");
                break;


        }
        return true;
    }

    private void initAnimation2() {
        valueAnimator2 = ValueAnimator.ofInt(0);
        //永久循环
        valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);

        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                for (int i = 0; i < mList.size(); i++) {
                    BubbleBean bubbleBean = mList.get(i);

                    PointF position = bubbleBean.position;

                    position.x = position.x - bubbleBean.deviation.x;
                    position.y = position.y - bubbleBean.deviation.y;

                    Log.i("szjpositionx",position.x+"\t\t"+width);
                    if ( position.x < 0) {
                        position.x = width;
                    }
                    Log.i("szjpositiony",position.y+"\t\t"+height);
                    if( position.y < 0){
                        position.y = height;
                    }

                    bubbleBean.position = position;

                }
                invalidate();
            }
        });
        valueAnimator2.start();
    }


    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0);
        //永久循环
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                for (int i = 0; i < mList.size(); i++) {
                    BubbleBean bubbleBean = mList.get(i);

                    PointF position = bubbleBean.position;

                    position.x = position.x + bubbleBean.deviation.x;
                    position.y = position.y + bubbleBean.deviation.y;

                    if ( position.x > width) {
                        position.x = 0;
                    }
                    if( position.y > height){
                        position.y = 0;
                    }
                    bubbleBean.position = position;

                }
                invalidate();
            }
        });
        valueAnimator.start();

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
}

class  BubbleBean{

    //气泡位置
    public PointF position = new PointF();

    //半径
    public int radius;

    //透明度
    public int color;
    //透明度
    public int alpha;

    //偏移速度
    public PointF deviation = new PointF();

    //记录按压的位置
    public PointF pointFDown = new PointF();

}
