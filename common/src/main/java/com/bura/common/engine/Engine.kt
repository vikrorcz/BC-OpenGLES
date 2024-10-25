package com.bura.common.engine

import com.bura.common.model.Camera
import com.bura.common.model.Instance
import com.bura.common.model.Shader
import com.bura.common.scene.ShapeScene
import com.bura.common.util.GLES20
import com.bura.common.util.MatrixUtil
import com.bura.common.util.ShaderUtil

class Engine(
    deviceGles20: GLES20,
) {
    companion object {
        lateinit var gles20: GLES20
    }

    init {
        gles20 = deviceGles20
    }

    val shader = Shader(
        colorProgram = ShaderUtil.createProgram("shaders/color"),
    )

    val camera = Camera()
    val instance = Instance(this, shader)
    val scene = ShapeScene(this, instance)
    val matrixUtil = MatrixUtil(this)

    var screenWidthPixel = 0
    var screenHeightPixel = 0

    val vPMatrix = FloatArray(16)
    var projectionMatrix = FloatArray(16)
    var viewMatrix = FloatArray(16)
}