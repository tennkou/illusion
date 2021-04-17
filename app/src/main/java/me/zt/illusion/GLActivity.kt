package me.zt.illusion

import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY
import android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import me.zt.illusion.gl.ColorDrawer
import me.zt.illusion.gl.ZTRenderer

class GLActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gl)

        val imageView = findViewById<ImageView>(R.id.image)
        findViewById<GLSurfaceView>(R.id.gl_view).run {
            setRenderer(ZTRenderer(context) {
                ColorDrawer(imageView)
            })
            renderMode = RENDERMODE_WHEN_DIRTY
        }
    }

}