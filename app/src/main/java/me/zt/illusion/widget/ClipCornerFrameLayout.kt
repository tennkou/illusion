package me.zt.illusion.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import me.zt.illusion.log
import me.zt.illusion.util.Flag
import me.zt.illusion.util.JavaUtil

/**
 * draw dispatchDraw ：
 * ViewGroup 如果不设置 background，只会触发 dispatchDraw 原因可能是设置 background 后 ViewGroup 中有 PFLAG_SKIP_DRAW
 *
 * clipPath 圆角无法抗锯齿，但是性能较好
 *
 * Shader   圆角可以抗锯齿，但是性能较差，
 * @see ShaderCornerFrameLayout
 *
 * ViewOutlineProvider 形状只能是 rect、oval、round rect
 *
 * xfermode, 需要判断版本 （支持抗锯齿, 抗锯齿效果不如 ShaderCornerFrameLayout），性能较好（使用 LAYER_TYPE_SOFTWARE 性能下降）
 * @see XfermodeCornerFrameLayout
 *
 * @see https://stackoverflow.com/questions/51538443/xfermode-in-android-p-beta
 */
class ClipCornerFrameLayout : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  private var topRadius = 40F
  private var bottomRadius = 0F
  private val path = Path()

  private var inDraw = false

  override fun draw(canvas: Canvas) {
    inDraw = true
    canvas.drawFilter = PaintFlagsDrawFilter(0,
      Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    canvas.clipPath(path)
    super.draw(canvas)
    inDraw = false
  }

  override fun dispatchDraw(canvas: Canvas) {
    val start = SystemClock.elapsedRealtime()
    if (!inDraw) {
      canvas.drawFilter = PaintFlagsDrawFilter(0,
        Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
      canvas.clipPath(path)
    }
    super.dispatchDraw(canvas)
    log("ClipCornerFrameLayout ${inDraw} cost ${SystemClock.elapsedRealtime() - start}")
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    path.reset()
    if (topRadius > 0F && bottomRadius > 0F) {
      throw IllegalStateException("$topRadius, $bottomRadius")
    }
    val rectF = RectF(0F, 0F, w.toFloat(), h.toFloat())
    val radii = floatArrayOf(topRadius, topRadius, topRadius, topRadius, 0F, 0f, 0f, 0f)
    path.addRoundRect(rectF, radii, Path.Direction.CW)
//    if (topRadius > 0) {
//      path.moveTo(w.toFloat(), h.toFloat())
//      path.lineTo(0F, h.toFloat())
//      path.lineTo(0F, topRadius)
//      val diameter = topRadius * 2F
//      path.arcTo(0F, 0F, diameter, diameter,
//        -180f, 90f, false)
//      path.lineTo(w - topRadius, 0F)
//      path.arcTo(w - diameter, 0F, w.toFloat(), diameter,
//        -90F, 90F, false)
//      path.lineTo(w.toFloat(), h.toFloat())
//    }
//    if (bottomRadius > 0) {
//      path.moveTo(0F, 0F)
//      path.lineTo(w.toFloat(), 0F)
//      path.lineTo(w.toFloat(), h.toFloat() - bottomRadius)
//      val diameter = bottomRadius * 2F
//      path.arcTo(w - diameter, h - diameter, w.toFloat(), h.toFloat(),
//        0F, 90f, false)
//      path.lineTo(bottomRadius, h.toFloat())
//      path.arcTo(0F, h - diameter, diameter, h.toFloat(),
//        90F, 90F, false)
//      path.lineTo(0F, 0F)
//    }
//    path.close()
  }

//  @RequiresApi(api = 21)
//  private class OutlineRoundProcessor private constructor() : RoundProcessor {
//    private val mRect: Rect
//    private val mViewOutlineProvider: ViewOutlineProvider
//    override fun setCornerRadius(radius: Float) {
//      this@RoundedRelativeLayout.invalidateOutline()
//    }
//
//    override fun beforeDraw(canvas: Canvas?, dispatch: Boolean) {}
//    override fun afterDraw(canvas: Canvas?, dispatch: Boolean) {}
//    override fun sizeChanged(w: Int, h: Int) {
//      mRect[0, 0, w] = h
//      this@RoundedRelativeLayout.setClipToOutline(true)
//      this@RoundedRelativeLayout.setOutlineProvider(mViewOutlineProvider)
//    }
//
//    init {
//      mRect = Rect()
//      mViewOutlineProvider = object : ViewOutlineProvider() {
//        override fun getOutline(view: View, outline: Outline) {
//          outline.setRoundRect(mRect, this@RoundedRelativeLayout.mRadius)
//        }
//      }
//    }
//  }
//
//  private class DrawRoundProcessor private constructor() : RoundProcessor {
//    private val mPath: Path
//    private val mPaint: Paint
//    private val mRectF: RectF
//    override fun setCornerRadius(radius: Float) {
//      mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
//      this@RoundedRelativeLayout.postInvalidate()
//    }
//
//    override fun beforeDraw(canvas: Canvas, dispatch: Boolean) {
//      if (dispatch || this@RoundedRelativeLayout.mIsClipBackground) {
//        canvas.saveLayer(mRectF, null as Paint?, 31)
//      }
//    }
//
//    override fun afterDraw(canvas: Canvas, dispatch: Boolean) {
//      if (dispatch || this@RoundedRelativeLayout.mIsClipBackground) {
//        canvas.drawPath(mPath, mPaint)
//        canvas.restore()
//      }
//    }
//
//    override fun sizeChanged(w: Int, h: Int) {
//      mRectF[0.0f, 0.0f, w.toFloat()] = h.toFloat()
//      mPath.reset()
//      mPath.addRoundRect(mRectF, this@RoundedRelativeLayout.mRadius, this@RoundedRelativeLayout.mRadius, Path.Direction.CW)
//    }
//
//    init {
//      mPath = Path()
//      mPaint = Paint(1)
//      mRectF = RectF()
//    }
//  }
//
//  private interface RoundProcessor {
//    fun setCornerRadius(var1: Float)
//    fun beforeDraw(var1: Canvas?, var2: Boolean)
//    fun afterDraw(var1: Canvas?, var2: Boolean)
//    fun sizeChanged(var1: Int, var2: Int)
//  }
}