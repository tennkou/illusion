package me.zt.illusion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById<View>(R.id.button_1).setOnClickListener {

      startActivity(Intent(this@MainActivity, GLActivity::class.java))
    }

    findViewById<View>(R.id.button_2).setOnClickListener {
      startActivity(Intent(this@MainActivity, BlurActivity::class.java))
    }

    findViewById<View>(R.id.button_3).setOnClickListener {
      startActivity(Intent(this, ClipActivity::class.java))
    }

    test()
  }


  private fun test() {
//    val a = "保存%1$s%";

  }

  private fun sCurve(x: Float) : Float {
    val t = x * 2.0F - 1.0F
    return (-t) * abs(t) * 0.5F + x + 0.5F
  }
}
