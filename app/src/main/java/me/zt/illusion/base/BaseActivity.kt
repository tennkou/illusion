package me.zt.illusion.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected open fun getLayoutId() : Int = throw NotImplementedError()

    protected open fun createContentView() {
        setContentView(getLayoutId());
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createContentView()
    }
}