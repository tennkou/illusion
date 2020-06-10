package me.zt.illusion

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import me.zt.illusion.base.BaseActivity
import me.zt.illusion.databinding.IllussionBinding
import me.zt.illusion.test.Coroutines

class IllusionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.illussion)
        val binding = DataBindingUtil.setContentView<IllussionBinding>(this, R.layout.illussion)

        Coroutines().load("my url")
    }
}