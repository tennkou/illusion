package me.zt.illusion.gl

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import java.lang.RuntimeException

abstract class BitmapDrawer : VertexDrawer() {

    private val maxSize = 16
    private val bitmaps = ArrayList<Bitmap>()
    private val names = ArrayList<String>()
    private val units = ArrayList<Int>()

    fun addBitmap(bitmap: Bitmap, name: String = "", textureUnit: Int = bitmaps.size) {
        if (bitmaps.size >= maxSize || textureUnit > maxSize) {
            throw RuntimeException("addBitmap")
        }
        bitmaps.add(bitmap)
        names.add(name)
        units.add(textureUnit)
    }

    private val textures: IntArray = IntArray(maxSize)

    override fun onDraw() {
        super.onDraw()

        val count = bitmaps.size
        if (count <= 0) {
            return
        }
        GLES20.glGenTextures(count, textures, 0)
        bitmaps.forEachIndexed { index, bitmap ->
            val unit = units[index]
            val name = names[index]

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + unit)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[unit])
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)

            if (name.isNotEmpty()) {
                val loc = GLES20.glGetUniformLocation(program, name)
                if (loc >= 0) {
                    GLES20.glUniform1i(loc, unit)
                }
            } else if (unit > 0) {
                throw RuntimeException("unit > 0 but name is empty")
            }
        }
    }

}