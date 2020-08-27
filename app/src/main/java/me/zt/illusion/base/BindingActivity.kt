package me.zt.illusion.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<T : ViewDataBinding> : BaseActivity() {

    protected lateinit var viewBinding: T

    override fun createContentView() {
        viewBinding = DataBindingUtil.setContentView(this, getLayoutId())
    }

}