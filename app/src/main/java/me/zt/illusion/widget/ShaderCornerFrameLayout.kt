package me.zt.illusion.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.FrameLayout
import me.zt.illusion.log

/**
 * Shader 圆角可以抗锯齿，但是性能较差
 */
class ShaderCornerFrameLayout : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  private var topRadius = 40F
  private val path = Path()
  private val rectF = RectF()
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

  private var inDraw = false

  override fun setBackground(background: Drawable?) {
    super.setBackground(background)
    throw IllegalStateException("")
  }

  private val tempCanvas = Canvas()
  private var tempBitmap: Bitmap? = null

  override fun dispatchDraw(canvas: Canvas) {
    val start = SystemClock.elapsedRealtime()

    super.dispatchDraw(tempCanvas)
    val shader: Shader = BitmapShader(tempBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    paint.shader = shader
    canvas.drawPath(path, paint)

    log("ShaderCornerFrameLayout cost ${SystemClock.elapsedRealtime() - start}")
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    tempBitmap?.recycle()
    tempBitmap = null
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    rectF.set(0F, 0f, w.toFloat(), h.toFloat());
    path.reset()
    val radii = floatArrayOf(topRadius, topRadius, topRadius, topRadius, 0F, 0f, 0f, 0f)
    path.addRoundRect(rectF, radii, Path.Direction.CW)
    path.close()
    tempBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    tempCanvas.setBitmap(tempBitmap)
  }
}