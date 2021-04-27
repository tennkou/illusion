package me.zt.illusion.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.graphics.toRect
import me.zt.illusion.log

/**
 * 适用于 sdk_int > 27。  高版本对于 透明区域，不进行绘制。所以用 DST_OUT
 * @see https://stackoverflow.com/questions/51538443/xfermode-in-android-p-beta
 * 低版本用 DST_IN
 *
 * 使用 xfermode。 需要 saveLayer 、或者设置 setLayerType（soft or hardware）。 （或者新建 canvas ，不推荐）
 * 不使用 saveLayer 裁剪的角有黑色背景。应该是 绘制时把 整个 View 的图层裁剪掉了。露出了 activity 的背景色
 *
 * rectF ( 当 topRadius 大致在 60 - 72 之间时)需要设置大一点 (设置 offset)， 具体原因待调查
 *
 */
class XfermodeCornerFrameLayout : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  private var topRadius = 60F
  private var bottomRadius = 0F
  private val path = Path()
  private val srcPath = Path()
  private val rectF = RectF()
  private val paint = Paint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
    xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
  }

//  init {
//    log("$layerType")
//    setLayerType(View.LAYER_TYPE_HARDWARE, null)
//  }

  override fun dispatchDraw(canvas: Canvas) {
    val layerCount = canvas.saveLayer(rectF, null)
    val start = SystemClock.elapsedRealtime()
    super.dispatchDraw(canvas)
    canvas.drawPath(srcPath, paint)
    canvas.restoreToCount(layerCount)
    log("XfermodeCornerFrameLayout cost:${SystemClock.elapsedRealtime() - start}")
  }


  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)

    log("density: ${context.resources.displayMetrics.density}")
    topRadius = context.resources.displayMetrics.density * 20F


    val f = 0.0F  // offset
    rectF.set(-f, -f, w + f, h + f)
    path.reset()
    val radii = floatArrayOf(topRadius, topRadius, topRadius, topRadius, 0F, 0f, 0f, 0f)
    path.addRoundRect(rectF, radii, Path.Direction.CCW)

    srcPath.reset()
    srcPath.addRect(rectF, Path.Direction.CCW)
    srcPath.op(path, Path.Op.DIFFERENCE)

    val r = RectF()
    srcPath.computeBounds(r, true)
    val re : Region = Region()
    re.setPath(srcPath, Region(rectF.left.toInt(), rectF.top.toInt(),
      rectF.right.toInt(), rectF.bottom.toInt()))

    log("contain ${re.contains(0, (topRadius / 2).toInt())}")
  }
}