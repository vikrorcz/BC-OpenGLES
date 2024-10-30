package com.bura.common.engine

import com.bura.common.model.Camera
import com.bura.common.model.Instance
import com.bura.common.model.Shader
import com.bura.common.scene.ShapeScene
import com.bura.common.scene.TakeOffScene
import com.bura.common.util.GLES20
import com.bura.common.util.MatrixUtil
import com.bura.common.util.ObjLoader
import com.bura.common.util.ShaderUtil
import com.bura.common.util.TextureUtil

class Engine(
    deviceGles20: GLES20,
    val textureUtil: TextureUtil,
) {
    companion object {
        lateinit var gles20: GLES20
    }

    init {
        gles20 = deviceGles20
        textureUtil.createTextures()
    }

    val shader = Shader(
        colorProgram = ShaderUtil.createProgram("shaders/color"),
        textureProgram = ShaderUtil.createProgram("shaders/texture"),
    )

    val objLoader = ObjLoader(this)
    val instance = Instance(this)
    val camera = Camera()
    val scene = ShapeScene(this)
    val matrixUtil = MatrixUtil(this)

    var screenWidthPixel = 0
    var screenHeightPixel = 0

    val vPMatrix = FloatArray(16)
    var projectionMatrix = FloatArray(16)
    var viewMatrix = FloatArray(16)
}