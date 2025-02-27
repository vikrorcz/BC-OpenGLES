package com.bura.common.shapes

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.model.Shader
import com.bura.common.scene.LandingScene
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

    private val buffers = IntArray(3)
    private var indexBufferHandle = 0

    init {
        initializeVBO()
    }

    // https://www.learnopengles.com/android-lesson-seven-an-introduction-to-vertex-buffer-objects-vbos/
    private fun initializeVBO() {
        gles20.glGenBuffers(3, buffers)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0])
        vertexData?.let {
            gles20.glBufferData(GLES20.GL_ARRAY_BUFFER, it.capacity() * Constants.BYTES_PER_FLOAT, it, GLES20.GL_STATIC_DRAW)
        }

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[1])
        gles20.glBufferData(GLES20.GL_ARRAY_BUFFER, textureData.capacity() * Constants.BYTES_PER_FLOAT, textureData, GLES20.GL_STATIC_DRAW)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[2])
        gles20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalData.capacity() * Constants.BYTES_PER_FLOAT, normalData, GLES20.GL_STATIC_DRAW)

        val intArray = IntArray(1)
        gles20.glGenBuffers(1, intArray)
        indexBufferHandle = intArray[0]
        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferHandle)

        gles20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indices.size * 2, drawListData, GLES20.GL_STATIC_DRAW)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    override fun draw() {
        gles20.glUseProgram(shaderType.program)

        when (shaderType) {
            is Shader.Texture -> renderWithTextureShader(shaderType)
            is Shader.Skybox -> renderWithSkyboxShader(shaderType)
            is Shader.Water -> renderWithWaterShader(shaderType)
        }

        gles20.glUseProgram(0)
    }

    private fun renderWithTextureShader(shaderType: Shader.Texture) {
        gles20.glUniform1i(shaderType.uTextureHandle, texture)
        gles20.glUniform4fv(shaderType.uColorHandle, color)
        gles20.glUniform3fv(shaderType.uLightPositionHandle, engine.scene.lightPosition)
        gles20.glUniformMatrix4fv(shaderType.uMatrixHandle,false, engine.vPMatrix)
        gles20.glUniformMatrix4fv(shaderType.uModelMatrixHandle, false, mModelMatrix)
        gles20.glUniform3fv(shaderType.uCameraPositionHandle, floatArrayOf(engine.camera.eyeX, engine.camera.eyeY, engine.camera.eyeZ))

        vertexData?.let {
            gles20.glVertexAttribPointer(
                shaderType.aPositionHandle, Constants.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, it
            )
        }

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0])
        gles20.glEnableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glVertexAttribPointer(shaderType.aPositionHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[1])
        gles20.glEnableVertexAttribArray(shaderType.aTextureHandle)
        gles20.glVertexAttribPointer(shaderType.aTextureHandle, Constants.COORDS_PER_TEXTURE_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[2])
        gles20.glEnableVertexAttribArray(shaderType.aNormalHandle)
        gles20.glVertexAttribPointer(shaderType.aNormalHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferHandle)
        gles20.glDrawElements(
            GLES20.GL_TRIANGLES,
            indices.size,
            GLES20.GL_UNSIGNED_SHORT,
            0,
        )

        gles20.glDisableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glDisableVertexAttribArray(shaderType.aTextureHandle)
        gles20.glDisableVertexAttribArray(shaderType.aNormalHandle)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0)
        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    private fun renderWithSkyboxShader(shaderType: Shader.Skybox) {
        gles20.glUniform1i(shaderType.uTextureHandle, texture)
        gles20.glUniform1f(shaderType.uScreenWidthHandle, engine.screenWidthPixel.toFloat())
        gles20.glUniform1f(shaderType.uScreenHeightHandle, engine.screenHeightPixel.toFloat())
        gles20.glUniform4fv(shaderType.uColorHandle, color)
        gles20.glUniform3fv(shaderType.uLightPositionHandle, engine.scene.lightPosition)
        gles20.glUniformMatrix4fv(shaderType.uMatrixHandle,false, engine.vPMatrix)
        gles20.glUniformMatrix4fv(shaderType.uModelMatrixHandle, false, mModelMatrix)
        gles20.glUniform3fv(shaderType.uCameraPositionHandle, floatArrayOf(engine.camera.eyeX, engine.camera.eyeY, engine.camera.eyeZ))

        vertexData?.let {
            gles20.glVertexAttribPointer(
                shaderType.aPositionHandle, Constants.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, it
            )
        }

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0])
        gles20.glEnableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glVertexAttribPointer(shaderType.aPositionHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[1])
        gles20.glEnableVertexAttribArray(shaderType.aTextureHandle)
        gles20.glVertexAttribPointer(shaderType.aTextureHandle, Constants.COORDS_PER_TEXTURE_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[2])
        gles20.glEnableVertexAttribArray(shaderType.aNormalHandle)
        gles20.glVertexAttribPointer(shaderType.aNormalHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferHandle)
        gles20.glDrawElements(
            GLES20.GL_TRIANGLES,
            indices.size,
            GLES20.GL_UNSIGNED_SHORT,
            0,
        )

        gles20.glDisableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glDisableVertexAttribArray(shaderType.aTextureHandle)
        gles20.glDisableVertexAttribArray(shaderType.aNormalHandle)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0)
        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    private fun renderWithWaterShader(shaderType: Shader.Water) {
        gles20.glUniform1i(shaderType.uTextureHandle, texture)
        gles20.glUniform4fv(shaderType.uColorHandle, color)
        gles20.glUniform3fv(shaderType.uLightPositionHandle, engine.scene.lightPosition)
        gles20.glUniformMatrix4fv(shaderType.uMatrixHandle,false, engine.vPMatrix)
        gles20.glUniformMatrix4fv(shaderType.uModelMatrixHandle, false, mModelMatrix)
        gles20.glUniform3fv(shaderType.uCameraPositionHandle, floatArrayOf(engine.camera.eyeX, engine.camera.eyeY, engine.camera.eyeZ))
        gles20.glUniform1f(shaderType.uTimeHandle, engine.totalTime)
        gles20.glUniform1f(shaderType.uWaveHeightHandle, (engine.scene as LandingScene).waveHeightUniform)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0])
        gles20.glEnableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glVertexAttribPointer(shaderType.aPositionHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[1])
        gles20.glEnableVertexAttribArray(shaderType.aTextureHandle)
        gles20.glVertexAttribPointer(shaderType.aTextureHandle, Constants.COORDS_PER_TEXTURE_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[2])
        gles20.glEnableVertexAttribArray(shaderType.aNormalHandle)
        gles20.glVertexAttribPointer(shaderType.aNormalHandle, Constants.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, 0)

        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBufferHandle)
        gles20.glDrawElements(
            GLES20.GL_TRIANGLES,
            indices.size,
            GLES20.GL_UNSIGNED_SHORT,
            0,
        )

        gles20.glDisableVertexAttribArray(shaderType.aPositionHandle)
        gles20.glDisableVertexAttribArray(shaderType.aTextureHandle)
        gles20.glDisableVertexAttribArray(shaderType.aNormalHandle)

        gles20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0)
        gles20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
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