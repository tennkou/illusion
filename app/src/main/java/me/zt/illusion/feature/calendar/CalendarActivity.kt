package me.zt.illusion.feature.calendar

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import me.zt.illusion.R
import me.zt.illusion.base.BaseActivity
import me.zt.illusion.base.BindingActivity
import me.zt.illusion.databinding.CalendarBinding

class CalendarActivity : BindingActivity<CalendarBinding>() {

    override fun getLayoutId(): Int = R.layout.calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}