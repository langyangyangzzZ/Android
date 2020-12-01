package demo.ht.com.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.RESTART;

public class SnowFlakeView extends View {

    private int height;//屏幕高
    private int width;//屏幕宽

    public SnowFlakeView(Context context) {
        this(context, null);
    }

    public SnowFlakeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Paint paint = new Paint();
    List<SnowBean> mList = new ArrayList<SnowBean>();


   Random mRandom =  new Random();
    public SnowFlakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        //获取屏幕宽度
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;

        //初始化数据
        initData();
//
//        //刷新数据
        Refresh();
    }

    //初始化雪花
    private void initData() {
       new  Thread(new Runnable() {
           @Override
           public void run() {
               for (int i = 0; i < 300; i++) {
                   SnowBean snowBean = new SnowBean();

                   //随机起始位置  mRandom.nextDouble()取值为0-1
                   snowBean.setPosition(new SnowBean.Position(mRandom.nextDouble() * width, mRandom.nextDouble() * height));

                   //随机偏移幅度   mRandom.nextDouble()取值为0-1
                   snowBean.setDeviation(mRandom.nextDouble() + 2);

                   //随机雪花大小  mRandom.nextDouble()取值为0-1  得到5-10之间的公式: mRandom.nextInt(MAX - MIN + 1) + MIN;
                   snowBean.setSize(mRandom.nextInt(10 - 5 + 1) + 5);

                   //随机运行速度  随机得到5-10之间的数   得到5-10之间的公式: mRandom.nextInt(MAX - MIN + 1) + MIN;
                   snowBean.setSpeed(mRandom.nextInt(10 - 5 + 1) + 5);

                   mList.add(snowBean);
               }
           }
       }).start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //循环绘制雪花!
        for (int i = 0; i < mList.size(); i++) {
            SnowBean snowBean = mList.get(i);
            canvas.drawCircle((float) snowBean.position.x, (float) snowBean.position.y, (float) snowBean.size, paint);
        }
    }

    public void Refresh() {
        //设置移动的位置
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0);
        //通过addUpdateListener来监听ofInt的变化
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //刷新
                initRefresh();
            }
        });
        //设置动画模式无限滚动等
        valueAnimator.setRepeatCount(INFINITE);
        valueAnimator.setRepeatMode(RESTART);
        valueAnimator.start();
    }

    //刷新
    private void initRefresh() {
        for (int i = 0; i < mList.size(); i++) {
            SnowBean snowBean = mList.get(i);
            //重新绘制雪花, 新雪花x = 旧的雪花x + 随机偏移度数
            snowBean.position.x = snowBean.position.x +  snowBean.deviation;
            //新的雪花y = 旧的雪花y + 雪花下降速度speed + 随机运行速度
            snowBean.position.y = snowBean.position.y + snowBean.getSpeed();

            //重置高度 当雪花y出屏幕时 则让他归0重新绘制 height是屏幕高度
            if (snowBean.position.y > height) {
                snowBean.position.y = 0;
            }
            //重置宽度 当雪花x 出屏幕时,让他归0重新绘制 width是屏幕宽度
            if (snowBean.position.x > width) {
                snowBean.position.x = 0;
            }
        }
        //刷新onDraw
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthmeasure = measure(widthMeasureSpec);
        int heightmeasure = measure(heightMeasureSpec);
        setMeasuredDimension(widthmeasure,heightmeasure);
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

class SnowBean {

    //坐标
    Position position = new Position(100,100);

    //偏移幅度
    double deviation;

    //大小
    double size = 50;

    //运行速度 dp/s
    double speed = 10;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public SnowBean() {
    }

    public SnowBean(Position position, double deviation,double size) {
        this.position = position;
        this.deviation = deviation;
        this.size = size;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    static class Position {
        double x;
        double y;

        public Position(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
