package demo.ht.com.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import demo.ht.com.utils.BezierEvaluator;

public class PathBezierView extends View implements View.OnClickListener{
    //起点
    private int mStartPointX;
    private int mStartPointY;
    //终点
    private int mEndPointX;
    private int mEndPointY;
    //控制点
    private int mFlagPointX;
    private int mFlagPointY;
    //移动小球
    private int mMovePointX;
    private int mMovePointY;

    private Path mPath;
    private Paint mPaintPath;
    private Paint mPaintCircle;

    public PathBezierView(Context context) {
        super(context);
    }

    public PathBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setStrokeWidth(8);
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);

        mStartPointX = 100;
        mStartPointY = 100;

        //小球刚开始位置在起点
        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;

        mEndPointX = 600;
        mEndPointY = 600;

        mFlagPointX = 500;
        mFlagPointY = 0;

        setOnClickListener(this);
    }

    public PathBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mStartPointX, mStartPointY, 20, mPaintCircle);
        canvas.drawCircle(mEndPointX, mEndPointY, 20, mPaintCircle);
        canvas.drawCircle(mMovePointX, mMovePointY, 20, mPaintCircle);

        mPath.reset();
        //绘制贝塞尔曲线，即运动路径
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintPath);
    }

    @Override
    public void onClick(View view) {
//        //创建贝塞尔曲线坐标的换算类
//        BezierEvaluator evaluator = new BezierEvaluator(new PointF(mFlagPointX, mFlagPointY));
//        //指定动画移动轨迹
//        ValueAnimator animator = ValueAnimator.ofObject(evaluator,
//                new PointF(mStartPointX, mStartPointY),
//                new PointF(mEndPointX, mEndPointY));
//        animator.setDuration(600);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                //改变小球坐标，产生运动效果
//                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
//                mMovePointX = (int) pointF.x;
//                mMovePointY = (int) pointF.y;
//                //刷新UI
//                invalidate();
//            }
//        });
//        //添加加速插值器，模拟真实物理效果
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.start();
    }
}