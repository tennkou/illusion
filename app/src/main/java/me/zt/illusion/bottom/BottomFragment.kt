package me.zt.illusion.bottom

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import me.zt.illusion.R

class BottomFragment : BaseBottomDF() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pager = view.findViewById<ViewPager2>(R.id.pager)
        pager.adapter = PagerAdapter2()
//        val pager = view.findViewById<ViewPager>(R.id.pager)
//        pager.adapter = PagerAdapter1()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog!!.window!!.findViewById<View>(R.id.container)?.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }


    override fun onStart() {
        super.onStart()
        val window = dialog!!.window!!
        window.setLayout(-1, -1)
        window.setDimAmount(0.5F)
//
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setBackgroundDrawable(ColorDrawable())
//        val layoutParams = window.attributes
//        // todo check 此处如果不设置MATCH_PARENT，则不需要上面的addFlags也能隐藏状态栏
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.horizontalMargin = 0F
//        layoutParams.verticalMargin = 0F
//        window.attributes = layoutParams
    }

}