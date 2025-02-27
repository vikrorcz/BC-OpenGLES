package com.bura.common.model

import com.bura.common.engine.Engine
import com.bura.common.util.Constants

class Shader(
    colorProgram: Int,
    textureProgram: Int,
    skyBoxProgram: Int,
    waterProgram: Int,
) {
    val color = Color(
        program = colorProgram,
        aPositionHandle = Engine.gles20.glGetAttribLocation(colorProgram, Constants.A_POSITION),
        uMatrixHandle = Engine.gles20.glGetUniformLocation(colorProgram, Constants.U_MATRIX),
        uColorHandle = Engine.gles20.glGetUniformLocation(colorProgram, Constants.U_COLOR),
    )

    val texture = Texture(
        program = textureProgram,
        aPositionHandle = Engine.gles20.glGetAttribLocation(textureProgram, Constants.A_POSITION),
        uMatrixHandle = Engine.gles20.glGetUniformLocation(textureProgram, Constants.U_MATRIX),
        uColorHandle = Engine.gles20.glGetUniformLocation(textureProgram, Constants.U_COLOR),
        uTextureHandle = Engine.gles20.glGetUniformLocation(textureProgram, Constants.U_TEXTURE),
        aTextureHandle = Engine.gles20.glGetAttribLocation(textureProgram, Constants.A_TEXTURE),
        aNormalHandle = Engine.gles20.glGetAttribLocation(textureProgram, Constants.A_NORMAL),
        uModelMatrixHandle = Engine.gles20.glGetUniformLocation(textureProgram, Constants.U_MODEL_MATRIX),
        uCameraPositionHandle = Engine.gles20.glGetUniformLocation(textureProgram, Constants.U_CAMERA_POSITION),
        uLightPositionHandle = Engine.gles20.glGetUniformLocation(textureProgram, Constants.U_LIGHT_POSITION),
    )

    val skybox = Skybox(
        program = skyBoxProgram,
        aPositionHandle = Engine.gles20.glGetAttribLocation(skyBoxProgram, Constants.A_POSITION),
        uMatrixHandle = Engine.gles20.glGetUniformLocation(skyBoxProgram, Constants.U_MATRIX),
        uColorHandle = Engine.gles20.glGetUniformLocation(skyBoxProgram, Constants.U_COLOR),
        uTextureHandle = Engine.gles20.glGetUniformLocation(skyBoxProgram, Constants.U_TEXTURE),
        aTextureHandle = Engine.gles20.glGetAttribLocation(skyBoxProgram, Constants.A_TEXTURE),
    )

    val water = Water(
        program = waterProgram,
        uTimeHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_TIME),
        uTextureHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_TEXTURE),
        aPositionHandle = Engine.gles20.glGetAttribLocation(waterProgram, Constants.A_POSITION),
        aTextureHandle = Engine.gles20.glGetAttribLocation(waterProgram, Constants.A_TEXTURE),
        aNormalHandle = Engine.gles20.glGetAttribLocation(waterProgram, Constants.A_NORMAL),
        uMatrixHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_MATRIX),
        uModelMatrixHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_MODEL_MATRIX),
        uCameraPositionHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_CAMERA_POSITION),
        uColorHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_COLOR),
        uLightPositionHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_LIGHT_POSITION),
        uWaveHeightHandle = Engine.gles20.glGetUniformLocation(waterProgram, Constants.U_WAVE_HEIGHT),
    )

    interface Type {
        var program: Int
    }

    data class Color(
        override var program: Int,
        var aPositionHandle: Int,
        var uMatrixHandle: Int,
        var uColorHandle: Int,
    ): Type

    data class Texture(
        override var program: Int,
        var uTextureHandle: Int,
        var aPositionHandle: Int,
        var aTextureHandle: Int,
        var aNormalHandle: Int,
        var uMatrixHandle: Int,
        var uModelMatrixHandle: Int,
        var uCameraPositionHandle: Int,
        var uColorHandle: Int,
        var uLightPositionHandle: Int,
    ): Type

    data class Skybox(
        override var program: Int,
        var uTextureHandle: Int,
        var aPositionHandle: Int,
        var aTextureHandle: Int,
        var uMatrixHandle: Int,
        var uColorHandle: Int,
    ): Type

    data class Water(
        override var program: Int,
        var uTimeHandle: Int,
        var uTextureHandle: Int,
        var aPositionHandle: Int,
        var aTextureHandle: Int,
        var aNormalHandle: Int,
        var uMatrixHandle: Int,
        var uModelMatrixHandle: Int,
        var uCameraPositionHandle: Int,
        var uColorHandle: Int,
        var uLightPositionHandle: Int,
        var uWaveHeightHandle: Int,
    ): Type
}