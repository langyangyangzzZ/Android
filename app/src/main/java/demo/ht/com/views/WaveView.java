package demo.ht.com.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;
import demo.ht.com.utils.BezierEvaluator;

public class WaveView extends View {

    private int mMovePointX = -1;
    private int mMovePointY = -1;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    Paint paint = new Paint();

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

    }

    private void initAnimation() {
        //创建贝塞尔曲线坐标的换算类                                 //x1y1是控制点1      x2,y2是控制点2
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(x1, y1),new PointF(x2,y2));
        //指定动画移动轨迹
        ValueAnimator animator = ValueAnimator.ofObject(evaluator,
                new PointF(x0, y0),//起始点
                new PointF(x3, y3));//结束点
        animator.setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //改变小球坐标，产生运动效果
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                /**
                 * mMovePointX 是最终贝塞尔曲线x坐标
                 * mMovePointY是最终贝塞尔曲线y坐标
                 */
                mMovePointX = (int) pointF.x;
                mMovePointY = (int) pointF.y;
                //刷新UI
                invalidate();
            }
        });
        //添加加速插值器，模拟真实物理效果
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    Path path =  new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * getMeasuredWidth()获取的是view原始的大小，也就是这个view在XML文件中配置或者是代码中设置的大小。
         *
         * getWidth（）获取的是这个view最终显示的大小，这个大小有可能等于原始的大小也有可能不等于原始大小。
         */
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        initXY(width,height);
        path.moveTo(x0,y0);
        path.cubicTo(x1,y1,x2,y2,x3,y3);

        //不填充
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        //绘制贝塞尔线
        canvas.drawPath(path,paint);


        //填充黑色小球
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(mMovePointX,mMovePointY,20,paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initAnimation();
        }
        return true;
    }

    public int x0 ;//起始点
    public int y0;
    public int x1;//控制点1
    public int y1;
    public int x2;//控制点2
    public int y2;
    public int x3;//结束点
    public int y3;
    private void initXY(int width, int height) {
        x0 = 0;
        y0 = 300;

        x1 = width / 2;
        y1 = height / 4 * 3;

        x2 = width / 2;
        y2 = height / 4 ;

        x3 = width;
        y3 = 300;

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
