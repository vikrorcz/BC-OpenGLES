package com.bura.opengles.util

import android.graphics.BitmapFactory
import android.opengl.GLUtils
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.util.GLES20
import com.bura.common.util.TextureUtil
import java.io.IOException
import java.io.InputStream

class AndroidTextureUtil() : TextureUtil {

    override fun loadTexture(resourceLocation: String): Int {
        val textureHandle = IntArray(1)
        gles20.glGenTextures(1, textureHandle)
        if (textureHandle[0] != 0) {
            val options = BitmapFactory.Options()
            options.inScaled = false

            val inputStream: InputStream = this.javaClass.classLoader?.getResourceAsStream(resourceLocation) ?: throw IOException("Classpath resource not found")
            val bitmap = BitmapFactory.decodeStream(inputStream)

            gles20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE,
            )

            gles20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE ,
            )

            gles20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR_MIPMAP_LINEAR,
            )
            gles20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR,
            )

            gles20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            gles20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
            bitmap.recycle()
        }
        if (textureHandle[0] == 0) {
            throw RuntimeException("Error loading texture.")
        }
        return textureHandle[0]
    }
}