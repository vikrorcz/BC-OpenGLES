package com.bura.common.shapes

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.util.Constants
import com.bura.common.util.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle(
    override val engine: Engine,
    override var x: Float,
    override var y: Float,
    override var z: Float,
    override var scale: Float,
): Shape(engine, x, y, z, scale) {

    private val triangleVertices = floatArrayOf(
        x, y + 0.2f * scale, z,
        x - 0.2f * scale, y - 0.1f * scale, z,
        x + 0.2f * scale, y - 0.1f * scale, z,
    )

    override var color: FloatArray = floatArrayOf(0f, 0f, 1f, 1f)

    override var vertexCount = triangleVertices.size / Constants.COORDS_PER_VERTEX

    override var vertexData: FloatBuffer? = ByteBuffer
        .allocateDirect(triangleVertices.size * Constants.BYTES_PER_FLOAT)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer().apply {
            put(triangleVertices)
            position(0)
        }

    override fun draw() {
        gles20.glUseProgram(engine.shader.color.program)

        engine.shader.color.aPositionHandle = gles20.glGetAttribLocation(engine.shader.color.program, "a_Position")

        gles20.glEnableVertexAttribArray(engine.shader.color.aPositionHandle)
        vertexData?.let {
            gles20.glVertexAttribPointer(
                engine.shader.color.aPositionHandle, Constants.COORDS_PER_VERTEX,  GLES20.GL_FLOAT,
                false, Constants.VERTEX_STRIDE, it
            )
        }

        engine.shader.color.uColorHandle = gles20.glGetUniformLocation(engine.shader.color.program, "u_Color")
        gles20.glUniform4fv(engine.shader.color.uColorHandle, color)

        engine.shader.color.uMatrixHandle = gles20.glGetUniformLocation(engine.shader.color.program, Constants.U_MATRIX)
        gles20.glUniformMatrix4fv(engine.shader.color.uMatrixHandle, false, engine.vPMatrix)

        gles20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        gles20.glDisableVertexAttribArray(engine.shader.color.aPositionHandle)

    }

    fun clone(): Triangle {
        val clonedTriangle = Triangle(engine, x, y, z, scale)
        clonedTriangle.color = color.copyOf()
        return clonedTriangle
    }
}