package me.zt.illusion.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.DownloadBottomSheetBehavior
import me.zt.illusion.R

open class BaseBottomDF : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return AppCompatDialog(requireContext())
        return DownloadBottomSheetDialog(requireContext(), R.style.MyTheme)
    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        val bottomSheet = dialog?.window?.findViewById<View>(R.id.design_bottom_sheet)?:return
//
//        (BottomSheetBehavior.from(bottomSheet) as DownloadBottomSheetBehavior).apply {
//            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onStateChanged(bottomSheet: View, state: Int) {
//                    if (state == BottomSheetBehavior.STATE_HIDDEN) {
//                        dismissAllowingStateLoss()
//                    }
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
//            })
//        }
//    }

}