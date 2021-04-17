package me.zt.illusion.gl

import android.content.Context
import android.opengl.GLES20.*
import me.zt.illusion.log

abstract class BaseDrawer {

    protected lateinit var context: Context
    protected var width = 0
    protected var height = 0
    protected var program = 0


    fun onSurfaceCreated(context: Context) {
        this.context = context
        createInternal()
    }

    fun glViewPort(width: Int, height: Int) {
        log("onDrawFrame")
        this.width = width
        this.height = height
        glViewport(0, 0, width, height)
    }

    fun onDrawFrame() {
        log("onDrawFrame")
        onDraw()
    }

    abstract fun getVertexShader() : String
    abstract fun getFragmentShader() : String
    open fun afterCreateProgram(){}
    open fun onDraw(){ }


    /**********\
     *
     */
    private fun createInternal() {

        val vertexShader = loadShader(GL_VERTEX_SHADER, getVertexShader())
        val fragmentShader = loadShader(GL_FRAGMENT_SHADER, getFragmentShader())
        if (vertexShader == 0 || fragmentShader == 0) {
            throw RuntimeException("loadShader")
        }

        program = glCreateProgram()
        checkGlError("glCreateProgram")
        glAttachShader(program, vertexShader)
        checkGlError("glAttachShader")
        glAttachShader(program, fragmentShader)
        checkGlError("glAttachShader")
        glLinkProgram(program)
        val linkStatus = IntArray(1)
        glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] != GL_TRUE) {
            throw java.lang.RuntimeException("failed to link program: " + glGetProgramInfoLog(program)
            )
        }

        glUseProgram(program)

        afterCreateProgram()
    }

    private fun loadShader(shaderType: Int, source: String): Int {
        val shader = glCreateShader(shaderType)
        checkGlError("glCreateShader type=$shaderType")
        glShaderSource(shader, source)
        glCompileShader(shader)
        val compiled = IntArray(1)
        glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0)
        if (compiled[0] == 0) {
            throw RuntimeException("compile $shaderType shader failed\n $source")
        }
        return shader
    }
    protected fun checkGlError(op: String) {
        var error: Int
        while (glGetError().also { error = it } != GL_NO_ERROR) {
            log("$op, glError $error")
            throw java.lang.RuntimeException("$op: glError $error")
        }
    }

}