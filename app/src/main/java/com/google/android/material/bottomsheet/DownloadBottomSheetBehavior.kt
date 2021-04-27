package com.google.android.material.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import me.zt.illusion.widget.MyRV
import java.lang.IllegalArgumentException
import kotlin.math.abs

class DownloadBottomSheetBehavior<V : View> : BottomSheetBehavior<V> {

  constructor() : super()
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

  var enableSlide = true

  private var downX = -1
  private var downY = -1

  override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
    val result = super.onLayoutChild(parent, child, layoutDirection)
    peekHeight = child.height  // STATE_EXPANDED 和 STATE_COLLAPSED 视为同一状态
    return result
  }

  override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
//    return super.onInterceptTouchEvent(parent, child, event)
    var result = enableSlide && super.onInterceptTouchEvent(parent, child, event)

    if (event.actionMasked == MotionEvent.ACTION_DOWN) {
      downX = event.x.toInt()
      downY = event.y.toInt()
    }
    if (result && event.actionMasked == MotionEvent.ACTION_MOVE && abs(event.x - downX) >= abs(event.y - downY)) {
      // 解决快速横向滑动时，导致分享面板关闭的问题
      result = false
    }
    if (result && canChildVerticalScroll(parent)) {
      result = false
    }

    return result
  }

  private fun canChildVerticalScroll(parent: ViewGroup) : Boolean {
    val count = parent.childCount
    (0 until count).forEach { index ->
      val child = parent.getChildAt(index)
      if (child is MyRV) {
        return child.canScorll()
      } else if (child.canScrollVertically(-1)) {
        return true
      } else if (child is ViewGroup && canChildVerticalScroll(child)) {
        return true
      }
    }
    return false
  }


  override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
    try {
      return enableSlide && super.onTouchEvent(parent, child, event)
    } catch (e: IllegalArgumentException) {
      e.printStackTrace()
    }
    return false
  }

  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
    if (axes and ViewCompat.SCROLL_AXIS_VERTICAL == 0) {
      touchingScrollingChild = false
    }
    return enableSlide
      && super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
  }

  override fun shouldHide(child: View, yvel: Float): Boolean {
    return child.top.toFloat() + yvel * 0.2f >= fitToContentsOffset + child.height / 2.0f
  }
}