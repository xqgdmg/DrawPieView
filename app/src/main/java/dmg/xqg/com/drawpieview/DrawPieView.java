package dmg.xqg.com.drawpieview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * 画饼图
 */
public class DrawPieView extends View {

    private Paint paint;
    private Context context;
    private Path path;

    public DrawPieView(Context context) {
        this(context, null);
    }

    public DrawPieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 中心和圆角
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int radius = (getMeasuredHeight() - Util.dpToPx(context, 90)) / 2;// 扇形的半径
        Log.e("chris","radius==" + radius);

        // 绘制文字 饼图
        paint.setTextSize(Util.sp2px(context, 14));
        paint.setColor(Color.WHITE);
        canvas.drawText("饼图", centerX, getMeasuredHeight() - Util.dpToPx(context, 16), paint);
        
        // Lollipop 的绘制
        int LollipopOffset = Util.dpToPx(context, 10);// 往中心点偏移10dp，使其突出来
        int LollipopPointX = centerX - LollipopOffset;
        int LollipopPointY = centerY - LollipopOffset;
//        path.addCircle(LollipopPointX, LollipopPointY, 11, Path.Direction.CW);// Lollipop 圆点画个11的白圆点
//        path.lineTo(LollipopPointX - radius / 2, LollipopPointY - radius - 10);//
//        canvas.drawPath(path, paint);

        // 绘制Lollipop标注线和标注名
        int lStopX = LollipopPointX - radius / 2;// 第一条线的终点第二条线的起点x坐标（扇形半径的中点x值）
        int lStopY =  LollipopPointY - radius - 10;// 第一条线的终点第二条线的起点y坐标（扇形半径的中点y值 往上加10）
        canvas.drawLine(LollipopPointX, LollipopPointY, lStopX, lStopY, paint);// 看第一，二个参数，其实是从扇形的圆心开始绘制直线的，只不过绘制的扇形刚好遮盖了这条直线
        canvas.drawLine(lStopX, lStopY, lStopX - Util.dpToPx(context, 48), lStopY, paint);// 第二条线的长度为 48
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(Util.sp2px(context, 10));
        canvas.drawText("Lollipop", lStopX - Util.dpToPx(context, 56), lStopY, paint);// 56-48=8

        // 绘制Marshmallow标注线和标注名，这个标线的位置很难计算
        int mStartX = centerX + radius / 2 + 100;
        int mStartY = centerY - radius/ 2 - 10;
//        int offset = Util.dpToPx(context, 20);
        canvas.drawLine(centerX, centerY, mStartX, mStartY, paint);
        canvas.drawLine(mStartX, mStartY , mStartX + Util.dpToPx(context, 28), mStartY , paint);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Marshmallow", mStartX + Util.dpToPx(context, 36), mStartY, paint);

        // 画各个扇形区
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            paint.setColor(Color.parseColor("#EE2B2A"));
            canvas.drawArc(LollipopPointX - radius, LollipopPointY - radius, LollipopPointX + radius, LollipopPointY + radius, -180, 120, true, paint);

            paint.setColor(Color.parseColor("#FDB50D"));
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, 0, -60, true, paint);

            paint.setColor(Color.parseColor("#118675"));
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, 0, 5, true, paint);

            paint.setColor(Color.parseColor("#8800A0"));
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, 5, 15, true, paint);

            paint.setColor(Color.parseColor("#118675"));
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, 15, 35, true, paint);

            paint.setColor(Color.parseColor("#1E80F0"));
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, 35, 145, true, paint);


        }
    }
}
