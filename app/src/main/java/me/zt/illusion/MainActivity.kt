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

//    test()
  }


  private fun test() {
    val count = 50
    var value = -2f
    val step = 0.1f
    (0 until count).forEach { index->
      value += step
      log("x:$value,  y:${sCurve(value)}")
    }
  }

  private fun sCurve(x: Float) : Float {
    val t = x * 2.0F - 1.0F
    return (-t) * abs(t) * 0.5F + x + 0.5F
  }
}
