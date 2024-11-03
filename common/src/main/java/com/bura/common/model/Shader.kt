package com.bura.common.model

import com.bura.common.engine.Engine
import com.bura.common.util.Constants

class Shader(
    colorProgram: Int,
    textureProgram: Int,
    skyBoxProgram: Int,
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
    )

    val skybox = Skybox(
        program = skyBoxProgram,
        aPositionHandle = Engine.gles20.glGetAttribLocation(skyBoxProgram, Constants.A_POSITION),
        uMatrixHandle = Engine.gles20.glGetUniformLocation(skyBoxProgram, Constants.U_MATRIX),
        uColorHandle = Engine.gles20.glGetUniformLocation(skyBoxProgram, Constants.U_COLOR),
        uTextureHandle = Engine.gles20.glGetUniformLocation(skyBoxProgram, Constants.U_TEXTURE),
        aTextureHandle = Engine.gles20.glGetAttribLocation(skyBoxProgram, Constants.A_TEXTURE),
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
        var uMatrixHandle: Int,
        var uColorHandle: Int,
    ): Type

    data class Skybox(
        override var program: Int,
        var uTextureHandle: Int,
        var aPositionHandle: Int,
        var aTextureHandle: Int,
        var uMatrixHandle: Int,
        var uColorHandle: Int,
    ): Type
}