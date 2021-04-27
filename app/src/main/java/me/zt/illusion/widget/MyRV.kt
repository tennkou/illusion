package me.zt.illusion.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class MyRV : RecyclerView {

    private lateinit var helper: RecyclerViewHelper

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {


    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        helper = RecyclerViewHelper.createHelper(this)
    }

    fun canScorll() :Boolean {
        val p = helper.findFirstVisibleItemPosition()
        if (p > 0) {
            return true;
        } else {
            val v = getChildAt(p)

            if (v.top < 0) {
                return true;
            }
        }
        return false
    }

//    override fun onTouchEvent(e: MotionEvent): Boolean {
//
//
//        if (e.actionMasked == MotionEvent.ACTION_DOWN) {
//            val p = helper.findFirstVisibleItemPosition()
//            if (p > 0) {
//                requestDisallowInterceptTouchEvent(true)
//            } else {
//                val v = getChildAt(p)
//
//                if (v.top < 0) {
//
//                    requestDisallowInterceptTouchEvent(true)
//                }
//            }
//        } else if (e.actionMasked == MotionEvent.ACTION_UP
//            || e.actionMasked == MotionEvent.ACTION_CANCEL) {
//            requestDisallowInterceptTouchEvent(false)
//        }
//        return super.onTouchEvent(e)
//    }
}