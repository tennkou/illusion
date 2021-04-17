package me.zt.illusion.gl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class ZTGLSurfaceView : GLSurfaceView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        // 创建OpenGL ES 2.0的上下文
        setEGLContextClientVersion(2)
    }
}