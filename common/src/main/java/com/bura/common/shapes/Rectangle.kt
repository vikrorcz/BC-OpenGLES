package com.bura.common.shapes


import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.util.Constants
import com.bura.common.util.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Rectangle(
    override val engine: Engine,
    override var x: Float,
    override var y: Float,
    override var z: Float,
    override var scale: Float,
    ) : Shape(engine, x, y, z, scale) {

    //square
    private val rectangleVertices = floatArrayOf(
        x - 1.0f * scale, y + 1.0f * scale, z,
        x - 1.0f * scale, y - 1.0f * scale, z,
        x + 1.0f * scale, y - 1.0f * scale, z,
        x + 1.0f * scale, y + 1.0f * scale, z,
    )

    override var vertexCount = rectangleVertices.size / Constants.COORDS_PER_VERTEX
    override var vertexData: FloatBuffer? = ByteBuffer
        .allocateDirect(rectangleVertices.size * Constants.BYTES_PER_FLOAT)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer().apply {
            put(rectangleVertices)
            position(0)
        }

    override var color: FloatArray = floatArrayOf(130.9f / 255.0f, 126.0f / 255.0f, 115.2f / 255.0f, 1f)

    private var drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3)

    private var drawListData: ShortBuffer = ByteBuffer
        .allocateDirect(drawOrder.size * 2)
        .order(ByteOrder.nativeOrder())
        .asShortBuffer().apply {
            put(drawOrder)
            position(0)
        }

    override fun draw() {
        gles20.glUseProgram(engine.shader.color.program)

        vertexData?.let {
            gles20.glVertexAttribPointer(
                engine.shader.color.aPositionHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, 0, it
            )
        }

        gles20.glUniform4fv(engine.shader.color.uColorHandle, color)
        gles20.glUniformMatrix4fv(engine.shader.color.uMatrixHandle,  false, engine.vPMatrix)

        gles20.glEnableVertexAttribArray(engine.shader.color.aPositionHandle)

        gles20.glDrawElements(
            GLES20.GL_TRIANGLES,
            drawOrder.size,
            GLES20.GL_UNSIGNED_SHORT,
            drawListData,
        )

        gles20.glDisableVertexAttribArray(engine.shader.color.aPositionHandle)

        gles20.glUseProgram(0)
    }

    fun clone(): Rectangle {
        val clonedRectangle = Rectangle(engine, x, y, z, scale)
        clonedRectangle.color = color.copyOf()
        return clonedRectangle
    }
}