package com.bura.common.model

import com.bura.common.engine.Engine
import com.bura.common.util.Constants

class Shader(
    colorProgram: Int,
) {
    val color = Color(
        program = colorProgram,
        aPositionHandle = Engine.gles20.glGetAttribLocation(colorProgram, Constants.A_POSITION),
        uMatrixHandle = Engine.gles20.glGetUniformLocation(colorProgram, Constants.U_MATRIX),
        uColorHandle = Engine.gles20.glGetUniformLocation(colorProgram, Constants.U_COLOR),
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
}