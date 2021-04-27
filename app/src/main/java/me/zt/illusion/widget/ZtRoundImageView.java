package me.zt.illusion.widget;

import java.util.Arrays;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * author: yangliu
 * created on: 2020/6/30 8:44 PM
 * description:
 */
public class ZtRoundImageView extends ImageView {

  private int cornerRadius = 72;
  private Xfermode xfermode;
  private float[] srcRadii;

  private RectF srcRectF; // 图片占的矩形区域

  private Paint paint;
  private Path path; // 用来裁剪图片的ptah
  private Path srcPath; // 图片区域大小的path

  public ZtRoundImageView(Context context) {
    this(context, null);
  }

  public ZtRoundImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ZtRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
//    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ZtRoundImageView, 0, 0);
//    for (int i = 0; i < ta.getIndexCount(); i++) {
//      int attr = ta.getIndex(i);
//      if (attr == R.styleable.ZtRoundImageView_corner_radius) {
//        cornerRadius = ta.getDimensionPixelSize(attr, cornerRadius);
//      }
//    }
//    ta.recycle();

//    srcRadii = new float[8];
    srcRectF = new RectF();
    paint = new Paint();
    path = new Path();
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
      xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    } else {
      xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
      srcPath = new Path();
    }
    srcRadii = new float[] {cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0, 0, 0};
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    srcRectF.set(0, 0, w, h);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (null != srcPath) {
      srcPath.reset();
    }
    canvas.saveLayer(srcRectF, null, Canvas.ALL_SAVE_FLAG);
    super.onDraw(canvas);
    paint.reset();
    path.reset();
    path.addRoundRect(srcRectF, srcRadii, Path.Direction.CCW);
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.FILL);
    paint.setXfermode(xfermode);
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
      canvas.drawPath(path, paint);
    } else {
      if (null != srcPath) {
        srcPath.addRect(srcRectF, Path.Direction.CCW);
        srcPath.op(path, Path.Op.DIFFERENCE);
        canvas.drawPath(srcPath, paint);
      }
    }
    paint.setXfermode(null);
    // 恢复画布
    canvas.restore();
  }
}
