package com.bura.common.shapes

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.util.Constants
import com.bura.common.util.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Texture(
    override var engine: Engine,
    override var x: Float,
    override var y: Float,
    override var z: Float,
    override var scale: Float = 1.0f,
    private val resourceId: Int,
) : Shape(engine, x, y, z) {

    private val rectangleVertices = floatArrayOf(
        x - 1.0f * scale, y + 1.0f * scale, z,
        x - 1.0f * scale, y - 1.0f * scale, z,
        x + 1.0f * scale, y - 1.0f * scale, z,
        x + 1.0f * scale, y + 1.0f * scale, z,
    )

    private val textureVertices = floatArrayOf(
        0.0f, 1.0f,
        0.0f, 0.0f,
        1.0f, 0.0f,
        1.0f, 1.0f,
    )

    override var color = floatArrayOf(
        Constants.defaultTextureColorRed,
        Constants.defaultTextureColorGreen,
        Constants.defaultTextureColorBlue,
        Constants.defaultTextureColorAlpha,
    )

    override var vertexData: FloatBuffer? =
        ByteBuffer
            .allocateDirect(rectangleVertices.size * Constants.BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer().apply {
                put(rectangleVertices)
                position(0)
            }

    private var textureData: FloatBuffer? =
        ByteBuffer
            .allocateDirect(textureVertices.size * Constants.BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer().apply {
                put(textureVertices)
                position(0)
            }

    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3)

    private val drawListData: ShortBuffer = ByteBuffer
        .allocateDirect(drawOrder.size * 2)
        .order(ByteOrder.nativeOrder())
        .asShortBuffer().apply {
            put(drawOrder)
            position(0)
        }

    override fun draw() {
        gles20.glUseProgram(engine.shader.texture.program)

        gles20.glUniform4fv(engine.shader.texture.uColorHandle, color)
        gles20.glUniform1i(engine.shader.texture.uTextureHandle, resourceId)
        gles20.glUniformMatrix4fv(engine.shader.texture.uMatrixHandle,  false, engine.vPMatrix)


        vertexData?.let {
            gles20.glVertexAttribPointer(
                engine.shader.texture.aPositionHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, 0, it
            )
        }

        textureData?.let {
            gles20.glVertexAttribPointer(
                engine.shader.texture.aTextureHandle, Constants.COORDS_PER_TEXTURE_VERTEX,
                GLES20.GL_FLOAT, false, Constants.TEXTURE_STRIDE, it
            )
        }

        gles20.glEnableVertexAttribArray(engine.shader.texture.aPositionHandle)
        gles20.glEnableVertexAttribArray(engine.shader.texture.aTextureHandle)

        gles20.glDrawElements(
            GLES20.GL_TRIANGLES,
            drawOrder.size,
            GLES20.GL_UNSIGNED_SHORT,
            drawListData,
        )

        gles20.glDisableVertexAttribArray(engine.shader.texture.aPositionHandle)
        gles20.glDisableVertexAttribArray(engine.shader.texture.aTextureHandle)

        gles20.glUseProgram(0)
    }

    fun clone(): Texture {
        val clonedTexture = Texture(engine, x, y, z, scale, resourceId)
        clonedTexture.color = color.copyOf()
        return clonedTexture
    }
}