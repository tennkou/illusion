package me.zt.illusion

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import me.zt.illusion.egl.EglHelper
import me.zt.illusion.gl.BlurDrawer
import me.zt.illusion.gl.glGetBitmap
import me.zt.illusion.util.FastBlur

class BlurActivity : AppCompatActivity() {

    private lateinit var image1: ImageView
    private lateinit var image2: ImageView

    private val eglHelper = EglHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur)
        image1 = findViewById(R.id.image1)
        image2 = findViewById(R.id.image2)

        image2.post {
            val width = image2.measuredWidth
            val height = image2.measuredHeight

            blur1()
            blur2()
        }
    }

    private val radius = 15F
    private val srcBitmap : Bitmap by lazy {
        loadBitmap()
    }

    private fun loadBitmap() : Bitmap {
        val maxW = 100
        val maxH = 100
        val startTime = System.currentTimeMillis()
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.photo0, option)
        var w = option.outWidth
        var h = option.outHeight
        var inSampleSize = 1
        while (w > maxW || h > maxH) {
            inSampleSize *= 2
            w /= 2
            h /= 2
        }
        option.inJustDecodeBounds = false
        option.inSampleSize = inSampleSize
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.photo0, option)
        log("loadBitmap cost:${System.currentTimeMillis() - startTime}")
        return bitmap
    }

    private fun blur1() {
        Thread {
            val bmp = srcBitmap
            val startTime = System.currentTimeMillis()
            val ret = FastBlur.doBlur(bmp, radius.toInt(), bmp.isMutable)
            log("blur1 $radius, cost:${System.currentTimeMillis() - startTime}")
            image1.post {
                image1.setImageBitmap(ret)
            }
        }.start()

    }

    private fun blur2() {
        Thread {
            val bmp = srcBitmap
            val width = bmp.width
            val height = bmp.height
            eglHelper.initEgl(null, width, height)
            val startTime = System.currentTimeMillis()
            BlurDrawer(true, radius, width, height).let {
                it.addBitmap(bmp)
                it.onSurfaceCreated(this)
                it.glViewPort(width, height)
                it.onDrawFrame()
            }
            val temp = glGetBitmap(width, height)
            BlurDrawer(false, radius, width, height).let {
                it.addBitmap(temp)
                it.onSurfaceCreated(this)
                it.glViewPort(width, height)
                it.onDrawFrame()
            }

            val ret = glGetBitmap(width, height)
            log("blur2 $radius, cost:${System.currentTimeMillis() - startTime}")
            image2.post {
                image2.setImageBitmap(ret)
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        eglHelper.destroyEgl()
    }
}