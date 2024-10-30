package com.bura.common.shapes

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.model.Shader
import com.bura.common.util.Constants
import com.bura.common.util.GLES20
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Obj(
    override var engine: Engine,
    override var x: Float,
    override var y: Float,
    override var z: Float,
    override var scale: Float,
    override var vertexData: FloatBuffer?,
    var indices: ShortArray,
    var drawListData: ShortBuffer,
    var texture: Int,
    val shaderType: Shader.Type,
    var textureData: FloatBuffer,
    var normalData: FloatBuffer,
): Shape(engine, x, y, z) {

    override var color: FloatArray = floatArrayOf(
        Constants.defaultTextureColorRed,
        Constants.defaultTextureColorGreen,
        Constants.defaultTextureColorBlue,
        Constants.defaultTextureColorAlpha,
    )

    override fun draw() {
        gles20.glUseProgram(shaderType.program)

        when (shaderType) {
            is Shader.Texture -> renderWithTextureShader(shaderType)
        }

        gles20.glUseProgram(0)
    }


    private fun renderWithTextureShader(shaderType: Shader.Texture) {
        gles20.glUniform1i(shaderType.uTextureHandle, texture)
        gles20.glUniform4fv(shaderType.uColorHandle, color)
        gles20.glUniformMatrix4fv(shaderType.uMatrixHandle,false, engine.vPMatrix)

        vertexData?.let {
            gles20.glVertexAttribPointer(
                shaderType.aPositionHandle, Constants.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, it
            )
        }

        gles20.glVertexAttribPointer(
            shaderType.aTextureHandle, Constants.COORDS_PER_TEXTURE_VERTEX,
            GLES20.GL_FLOAT, false, 0, textureData
        )

        gles20.glEnableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glEnableVertexAttribArray(shaderType.aTextureHandle)

        gles20.glDrawElements(
            GLES20.GL_TRIANGLES,
            indices.size,
            GLES20.GL_UNSIGNED_SHORT,
            drawListData,
        )

        gles20.glDisableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glDisableVertexAttribArray(shaderType.aTextureHandle)
    }

    fun clone(): Obj {
        val clonedVertexData = vertexData?.let { it.duplicate() as FloatBuffer }
        val clonedIndices = indices.clone()
        val clonedDrawListData = drawListData.duplicate() as ShortBuffer
        val clonedTextureData = textureData.duplicate() as FloatBuffer
        val clonedNormalData = normalData.duplicate() as FloatBuffer

        val clonedObj = Obj(
            engine, x, y, z,
            scale,
            clonedVertexData,
            clonedIndices,
            clonedDrawListData,
            texture,
            shaderType,
            clonedTextureData,
            clonedNormalData,
        )

        clonedObj.color = color.copyOf()

        return clonedObj
    }
}