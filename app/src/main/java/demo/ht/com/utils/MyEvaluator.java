package demo.ht.com.utils;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

public class MyEvaluator implements TypeEvaluator<PointF> {
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        Log.i("szjstartValue",startValue.x+"\t\t"+startValue.y+"\tfraction:"+fraction);
        Log.i("szjendValue",endValue.x+"\t\t"+endValue.y+"\tfraction:"+fraction);
        float x = startValue.x + fraction * (endValue.x - startValue.x);
        float y = startValue.y + fraction * (endValue.y - startValue.y);
        PointF point = new PointF(x, y);
        return point;

    }
}