package me.zt.illusion.gl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils
import android.widget.ImageView
import me.zt.illusion.R
import me.zt.illusion.log
import java.lang.RuntimeException
import java.nio.ByteBuffer

class ColorDrawer constructor(private val imageView: ImageView) : VertexDrawer()  {

    override fun getFragmentShader(): String =
        """precision mediump float;
            uniform sampler2D tex1;
            varying vec2 textureCoordinate;
            void main() {
              vec2 st = textureCoordinate;
              vec4 color = texture2D(tex1, st);
              gl_FragColor = color;
              }"""

    private val textures = IntArray(2)
    private val frameBuffers = IntArray(1)

    override fun onDraw() {
        super.onDraw()

        // create bitmap texture
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.tank300)
        glGenTextures(2, textures, 0)
        glBindTexture(GL_TEXTURE_2D, textures[0])
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        glBindTexture(GL_TEXTURE_2D, GL_NONE)

        // bind framebuffer
//        glGenFramebuffers(1, frameBuffers, 0)
//        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffers[0])
//
//        glBindTexture(GL_TEXTURE_2D, textures[1])
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
//            0, GL_RGBA, GL_UNSIGNED_BYTE, null)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
//        glBindTexture(GL_TEXTURE_2D, GL_NONE)
//
//        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textures[1], 0)
//        glCheckFramebufferStatus(GL_FRAMEBUFFER).let {
//            if (it != GL_FRAMEBUFFER_COMPLETE) {
//                throw RuntimeException("glCheckFramebufferStatus $it, ${Integer.toHexString(it)}")
//            }
//        }

        // bind texture
        glBindTexture(GL_TEXTURE_2D, textures[0])

        drawVertex()

//        val buf = ByteBuffer.allocate(width * height * 4)
//        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buf)
//        buf.rewind()
//        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        bmp.copyPixelsFromBuffer(buf)
//        log("glReadPixels ${bmp.width}, ${bmp.height}")
//
//        imageView.post {
//            imageView.setImageBitmap(bmp)
//        }
    }
}