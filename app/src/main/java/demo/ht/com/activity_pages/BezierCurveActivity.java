package demo.ht.com.activity_pages;

import androidx.appcompat.app.AppCompatActivity;
import demo.ht.com.androidproject.R;
import demo.ht.com.views.BezierCurveView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;

public class BezierCurveActivity extends Activity {
    private Bitmap bitmap;
    private int[]colors ={Color.WHITE, Color.CYAN, Color.YELLOW, Color.BLACK , Color.LTGRAY, Color.GREEN, Color.RED};

    public Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_curve);
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ImageView imageView = new ImageView(BezierCurveActivity.this);
////                imageView.setImageBitmap(drawHeart(colors[random.nextInt(colors.length)]));
////
////                ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1,0);
////                ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", 1,-100,-300);
////                alpha.addListener(new AnimatorListenerAdapter() {
////                    @Override
////                    public void onAnimationEnd(Animator animation) {
////                        super.onAnimationEnd(animation);
////                        Log.i("szjonAnimationEnd","动画执行结束啦");
////                        relative.removeView(imageView);
////                    }
////                });
////
////
////
////                AnimatorSet animatorSet = new AnimatorSet();
////                animatorSet.playTogether(alpha,translationY);
////                animatorSet.setDuration(3000);
////                animatorSet.start();
////
////                relative.addView(imageView);
//
//            }
//        });

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);

    }

   Paint paint =  new Paint();

    //改变图片颜色
    private Bitmap drawHeart(int color) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.drawColor(color, PorterDuff.Mode.SRC_ATOP);
        canvas.setBitmap(null);
        return newBitmap;
    }
}