package me.zt.illusion.widget.recycler

import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView

class TableLayoutManager(private val spanCount: Int) : RecyclerView.LayoutManager() {

    private lateinit var recyclerView: RecyclerView

    private val cacheAdditionalW = ArrayList<Int>()
    private val cacheAdditionalH = ArrayList<Int>()

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        recyclerView = view
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        val itemCount = state.itemCount
        val totalWidth = recyclerView.measuredWidth - recyclerView.paddingLeft - recyclerView.paddingRight
        val totalHeight = recyclerView.measuredHeight - recyclerView.paddingTop - recyclerView.paddingBottom
        if (totalHeight <= 0 || itemCount <= 0) {
            return
        }
        val rowCount = (itemCount + spanCount - 1) / spanCount
        val itemWidth = totalWidth / spanCount
        val itemHeight = totalHeight / rowCount
        calculateAdditional(cacheAdditionalW, spanCount, totalWidth)
        calculateAdditional(cacheAdditionalH, rowCount, totalHeight)

        (0 until state.itemCount).forEach { index->
            val col = index % spanCount
            val row = index / spanCount
            val child = getChildAt(index)!!
            val wSpec = MeasureSpec.makeMeasureSpec(
                itemWidth - getLeftDecorationWidth(child) - getRightDecorationWidth(child),
                MeasureSpec.EXACTLY) + cacheAdditionalW[index]
            val hSpec = MeasureSpec.makeMeasureSpec(
                itemHeight - getTopDecorationHeight(child) - getBottomDecorationHeight(child),
                MeasureSpec.EXACTLY) + cacheAdditionalH[index]
            if (shouldMeasureChild(child, wSpec, hSpec, child.layoutParams)) {
                child.measure(wSpec, hSpec)
            }
            val left = itemWidth * col + getLeftDecorationWidth(child)
            val top = itemHeight * row + getTopDecorationHeight(child)
            child.layout(left, top , left + child.measuredWidth, top + child.measuredHeight)
        }
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    }

    private fun calculateAdditional(
        cache: ArrayList<Int>,
        spanCount: Int,
        totalSpace: Int
    ) {
        cache.clear()
        val sizePerSpanRemainder = totalSpace % spanCount
        var additionalSize = 0
        for (i in 0 until spanCount) {
            additionalSize += sizePerSpanRemainder
            if (additionalSize > 0 && spanCount - additionalSize < sizePerSpanRemainder) {
                additionalSize -= spanCount
                cache[i] = 1
            } else {
                cache[i] = 0
            }
        }
    }

    // we may consider making this public
    private fun shouldMeasureChild(child: View,
        widthSpec: Int,
        heightSpec: Int,
        lp: ViewGroup.LayoutParams
    ): Boolean {
        return child.isLayoutRequested
                || !isMeasurementUpToDate(child.width, widthSpec, lp.width)
                || !isMeasurementUpToDate(child.height, heightSpec, lp.height)
    }

    private fun isMeasurementUpToDate(
        childSize: Int,
        spec: Int,
        dimension: Int
    ): Boolean {
        val specMode = MeasureSpec.getMode(spec)
        val specSize = MeasureSpec.getSize(spec)
        if (dimension > 0 && childSize != dimension) {
            return false
        }
        when (specMode) {
            MeasureSpec.UNSPECIFIED -> return true
            MeasureSpec.AT_MOST -> return specSize >= childSize
            MeasureSpec.EXACTLY -> return specSize == childSize
        }
        return false
    }
}