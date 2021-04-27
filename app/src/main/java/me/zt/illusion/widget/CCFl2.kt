package me.zt.illusion.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import me.zt.illusion.log


/**
 */
class CCFl2 : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  private var topRadius = 60F
  private var bottomRadius = 0F
  private val path = Path()
  private val srcPath = Path()
  private val rectF = RectF()
  private val paint = Paint().apply {
//    color = Color.TRANSPARENT
    xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
  }


  init {
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
  }
  private var inDraw = false

  override fun dispatchDraw(canvas: Canvas) {
    paint.isAntiAlias = true
    paint.style = Paint.Style.FILL
    super.dispatchDraw(canvas)
    canvas.drawPath(srcPath, paint)
  }


  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    rectF.set(0F, 0f, w.toFloat(), h.toFloat());
    path.reset()
    val radii = floatArrayOf(topRadius, topRadius, topRadius, topRadius, 0F, 0f, 0f, 0f)
    path.addRoundRect(rectF, radii, Path.Direction.CW)

    srcPath.addRect(rectF, Path.Direction.CW)
    srcPath.op(path, Path.Op.DIFFERENCE)
//    path.close()
  }
}