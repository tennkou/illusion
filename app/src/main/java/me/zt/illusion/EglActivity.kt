package me.zt.illusion

import android.graphics.Bitmap
import android.opengl.GLES20
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import me.zt.illusion.egl.EglHelper
import me.zt.illusion.gl.ColorDrawer
import java.nio.ByteBuffer

class EglActivity : AppCompatActivity() {

    private val eglHelper = EglHelper()

    private lateinit var image2 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egl)

        findViewById<ImageView>(R.id.image1).setImageResource(R.drawable.tank300)
        image2 = findViewById(R.id.image2)

        image2.post {

            Thread {
                egl()
            }.start()
        }


    }

    private fun egl() {
        val width = image2.measuredWidth
        val height = image2.measuredHeight


        eglHelper.initEgl(null, width, height)
        val drawer = ColorDrawer(image2)
        drawer.onSurfaceCreated(this)
        drawer.glViewPort(width, height)
        drawer.onDrawFrame()

        val buf = ByteBuffer.allocate(width * height * 4)
        GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf)
        buf.rewind()
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bmp.copyPixelsFromBuffer(buf)
        log("glReadPixels ${bmp.width}, ${bmp.height}")

        image2.post {
            image2.setImageBitmap(bmp)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eglHelper.destoryEgl()
    }
}