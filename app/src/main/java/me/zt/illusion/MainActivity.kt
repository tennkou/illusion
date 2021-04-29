package me.zt.illusion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Cancellable
import io.reactivex.functions.Consumer
import io.reactivex.internal.disposables.CancellableDisposable
import io.reactivex.schedulers.Schedulers
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

    val handler = Handler()

//    Observable.fromCallable()
    val disposable = Observable.just(10)
      .flatMap {
        Observable.create<String> { emitter ->
          val r = Runnable {
            log("emitter onNext $it")
            emitter.onNext("$it")
          }
          emitter.setDisposable(CancellableDisposable(Cancellable {
            log("emitter dispose")
            handler.removeCallbacks(r)
          }))
          handler.postDelayed(r, 5000)
        }
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        log(it)
      }, {
        log("error$it")
      })
    handler.postDelayed({
      disposable.dispose()
    } , 1500)

  }

  private fun sCurve(x: Float) : Float {
    val t = x * 2.0F - 1.0F
    return (-t) * abs(t) * 0.5F + x + 0.5F
  }
}
