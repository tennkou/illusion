package me.zt.illusion.gl

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ZTRenderer(val context: Context, generator: ()->BaseDrawer) : GLSurfaceView.Renderer {

    val drawer: BaseDrawer by lazy(generator)

    override fun onDrawFrame(gl: GL10?) {
        drawer.onDrawFrame()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        drawer.glViewPort(width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        drawer.onSurfaceCreated(context)
    }
}