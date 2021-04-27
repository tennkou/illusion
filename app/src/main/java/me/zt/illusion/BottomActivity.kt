package me.zt.illusion

import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import me.zt.illusion.bottom.BottomFragment

class BottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(Runnable {

            (window.decorView as ViewGroup).getChildAt(0).setOnClickListener {
                BottomFragment().show(supportFragmentManager, "BottomActivity")
            }

            BottomFragment().show(supportFragmentManager, "BottomActivity")

        }, 200)

    }
}