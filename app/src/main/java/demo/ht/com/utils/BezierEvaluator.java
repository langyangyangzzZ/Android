package demo.ht.com.utils;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF1;
    private PointF pointF2;

    public BezierEvaluator(PointF pointF1,PointF pointF2) {
        this.pointF1 = pointF1;
        this. pointF2 = pointF2;
    }

    @Override
    public PointF evaluate(float v, PointF pointF, PointF t1) {
        return BezierUtil.CalculateBezierPointForCubic(v, pointF, pointF1, pointF2,t1);
    }
}