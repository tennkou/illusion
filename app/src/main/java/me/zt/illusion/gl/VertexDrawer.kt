package me.zt.illusion.gl

import android.opengl.GLES20.*

abstract class VertexDrawer : BaseDrawer() {

    override fun getVertexShader(): String =
        """attribute vec4 vertex_position;
            attribute vec2 texture_position;
//            attribute vec3 a_color;
            
            varying vec2 textureCoordinate;
            void main() {
              textureCoordinate = texture_position;
              gl_Position = vertex_position;
              
              }"""

    override fun afterCreateProgram() {
        super.afterCreateProgram()

        val vertexCoords = floatArrayOf(
            -1f, -1f,
            1f, -1f,
            1f, 1f,
            -1f, 1f
        )
        val vertexHandle = glGetAttribLocation(program, "vertex_position")
        val vertexBuffer = getBufferFromArray(vertexCoords)
        glEnableVertexAttribArray(vertexHandle)
        glVertexAttribPointer(vertexHandle, 2, GL_FLOAT,
            false, 0, vertexBuffer)
        checkGlError("glVertexAttribPointer")

        val textureCoords = floatArrayOf(
            0f, 1f,
            1f, 1f,
            1f, 0f,
            0f, 0f
        )
        val textureHandle = glGetAttribLocation(program, "texture_position")
        val textureBuffer = getBufferFromArray(textureCoords)
        glEnableVertexAttribArray(textureHandle)
        glVertexAttribPointer(textureHandle, 2, GL_FLOAT,
            false, 0, textureBuffer)
        checkGlError("glVertexAttribPointer")

    }

    protected fun drawVertex() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4)
    }


}