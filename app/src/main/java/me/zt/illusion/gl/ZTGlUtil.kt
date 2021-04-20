package me.zt.illusion.gl

import android.graphics.Bitmap
import android.opengl.GLES20
import me.zt.illusion.log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

fun getBufferFromArray(array: FloatArray) : FloatBuffer {
    return  ByteBuffer.allocateDirect(array.size * 4).run {
        // use the device hardware's native byte order
        order(ByteOrder.nativeOrder())

        // create a floating point buffer from the ByteBuffer
        asFloatBuffer().apply {
            // add the coordinates to the FloatBuffer
            put(array)
            // set the buffer to read the first coordinate
            position(0)
        }
    }
}

fun glGetBitmap(width: Int, height: Int) : Bitmap {
    val buf = ByteBuffer.allocate(width * height * 4)
    GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf)
    buf.rewind()
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    bmp.copyPixelsFromBuffer(buf)
    return bmp
}