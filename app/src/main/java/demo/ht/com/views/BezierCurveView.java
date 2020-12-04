package demo.ht.com.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * szj2020/12/1
 * 微信公众号 码上变有钱
 * CSDN博客地址:https://blog.csdn.net/weixin_44819566
 * 微信号:ohhzzZ
 * <p>
 *  贝塞尔曲线
 */
public class BezierCurveView extends View {

    private Paint paint;
    private Path path;
    private int mType = -1;


    public BezierCurveView(Context context) {
        this(context, null);
    }

    public BezierCurveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierCurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();

        paint = new Paint();
        //填充
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //一阶贝塞尔曲线
        initOneStage(canvas);

//        //二阶贝塞尔曲线
        initTwoStage(canvas);

//        //三阶段贝塞尔曲线
        initThree(canvas);
    }

    /**
     * 三阶贝塞尔曲线
     *
     * @param canvas
     */
    private void initThree(Canvas canvas) {
        /**
         * 三阶贝塞尔曲线
         * 开始点为100*1000
         * 结束点为1000*1000
         * 控制点1为: (float) threeBean.ThreeControlPointX1,(float) threeBean.ThreeControlPointY1,
         * 控制点2位 (float) threeBean.ThreeControlPointX2,(float) threeBean.ThreeControlPointY2,
         */

        paint.setColor(Color.RED);
        path.moveTo(100, 1000);//起始点
        path.cubicTo((float) threeBean.ThreeControlPointX1, (float) threeBean.ThreeControlPointY1,//控制点1
                (float) threeBean.ThreeControlPointX2, (float) threeBean.ThreeControlPointY2,//控制点2
                1000, 1000);//结束点
        canvas.drawPath(path, paint);
        path.reset();//重置


        //绘制三阶段贝塞尔辅助线
        paint.setColor(Color.BLACK);
        path.moveTo(100, 1000);//起始点
        path.lineTo((float) threeBean.ThreeControlPointX1, (float) threeBean.ThreeControlPointY1);
        path.lineTo((float) threeBean.ThreeControlPointX2, (float) threeBean.ThreeControlPointY2);
        path.lineTo(1000, 1000);//结束点
        canvas.drawPath(path, paint);


        //绘制贝塞尔三阶圆点
        paint.setColor(Color.YELLOW);
        canvas.drawCircle((float) threeBean.ThreeControlPointX1, (float) threeBean.ThreeControlPointY1, 10, paint);
        canvas.drawCircle((float) threeBean.ThreeControlPointX2, (float) threeBean.ThreeControlPointY2, 10, paint);


    }

    /**
     * 二阶贝塞尔曲线
     *
     * @param canvas
     */
    private void initTwoStage(Canvas canvas) {
        /**
         * 二阶贝塞尔曲线
         * 起始点:100*500
         * 控制点   (float)towBean.TowControlPointX * (float)towBean.TowControlPoinY,
         * 结束点  1000,500)
         */
//        起始点
        paint.setColor(Color.RED);
        path.moveTo(100, 500);
        //x1控制点x y1控制点y   x2结束点x y2结束点y
        path.quadTo(
                (float) towBean.TowControlPointX,
                (float) towBean.TowControlPoinY,
                1000,
                500);
        canvas.drawPath(path, paint);
        path.reset();//重置


        //二阶贝塞尔曲线 辅助线
        paint.setColor(Color.BLACK);
        path.moveTo(100, 500);
        path.lineTo((float) towBean.TowControlPointX, (float) towBean.TowControlPoinY);
        path.lineTo(1000, 500);
        canvas.drawPath(path, paint);


        //二阶贝塞尔圆
        path.reset();//重置
        paint.setColor(Color.YELLOW);
        canvas.drawCircle((float) towBean.TowControlPointX, (float) towBean.TowControlPoinY, 10, paint);
        canvas.drawPath(path, paint);
        path.reset();//重置
    }

    /**
     * 一阶贝塞尔曲线
     */
    private void initOneStage(Canvas canvas) {
        //一阶贝塞尔曲线
        paint.setColor(Color.RED);
        path.moveTo(50, 50);
        path.lineTo(100, 150);
        canvas.drawPath(path, paint);
        path.reset();//重置
    }

    TowBean towBean = new TowBean();
    ThreeBean threeBean = new ThreeBean();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mType == 1) {
                    towBean.TowControlPointX = (int) event.getX();
                    towBean.TowControlPoinY = (int) event.getY();
                    invalidate();
                } else if (mType == 2) {
                    threeBean.ThreeControlPointX1 = (int) event.getX();
                    threeBean.ThreeControlPointY1 = (int) event.getY();
                    invalidate();
                } else if (mType == 3) {
                    threeBean.ThreeControlPointX2 = (int) event.getX();
                    threeBean.ThreeControlPointY2 = (int) event.getY();
                    invalidate();
                }


                break;

            case MotionEvent.ACTION_DOWN:
                boolean two = isPointInCircle(new PointF(event.getX(), event.getY()),
                        new PointF((float) towBean.TowControlPointX, (float) towBean.TowControlPoinY),
                        50);

                boolean three1 = isPointInCircle(new PointF(event.getX(), event.getY()),
                        new PointF((float) threeBean.ThreeControlPointX1, (float) threeBean.ThreeControlPointY1),
                        50);

                boolean three2 = isPointInCircle(new PointF(event.getX(), event.getY()),
                        new PointF((float) threeBean.ThreeControlPointX2, (float) threeBean.ThreeControlPointY2),
                        50);
                if (two) {
                    //二阶贝塞尔圆是否选中
                    mType = 1;
                } else if (three1) {
                    mType = 2;
                } else if (three2) {
                    mType = 3;
                } else {
                    mType = -1;
                }
                break;

        }
        return true;
    }



    /**
     * 判断点是否在圆内
     *
     * @param pointF 待确定点
     * @param circle 圆心的位置
     * @param radius 半径
     * @return true在圆内
     */
    private boolean isPointInCircle(PointF pointF, PointF circle, float radius) {
        return Math.pow((pointF.x - circle.x), 2) + Math.pow((pointF.y - circle.y), 2) <= Math.pow(radius, 2);
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


/**
 * 二阶控制点Bean类
 */
class TowBean {
    public double TowControlPointX = 100;
    public double TowControlPoinY = 100;
}

class ThreeBean {
    public double ThreeControlPointX1 = 400;//控制点1 x坐标
    public double ThreeControlPointY1 = 600;//控制点2 y坐标

    public double ThreeControlPointX2 = 800;///控制点2 x坐标
    public double ThreeControlPointY2 = 600;//控制点2 y坐标
}
