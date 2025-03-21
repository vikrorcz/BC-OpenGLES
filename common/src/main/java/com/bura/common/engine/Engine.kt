package com.bura.common.engine

import com.bura.common.model.Camera
import com.bura.common.model.Instance
import com.bura.common.model.Shader
import com.bura.common.scene.LandingScene
import com.bura.common.scene.Scene
import com.bura.common.scene.SpaceScene
import com.bura.common.scene.TakeOffScene
import com.bura.common.util.FPSCounter
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
        skyBoxProgram = ShaderUtil.createProgram("shaders/skybox"),
        waterProgram = ShaderUtil.createProgram("shaders/water"),
    )

    val fpsCounter = FPSCounter()
    val objLoader = ObjLoader(this)
    val instance = Instance(this)
    val camera = Camera()
    var scene: Scene = TakeOffScene(this)
    val matrixUtil = MatrixUtil(this)

    var screenWidthPixel = 0
    var screenHeightPixel = 0
    var deltaTime = 0.0f
    var totalTime = 0.0f
    var speedMultiplier = 0.0f // Ensures the model moves approximately the same speed regardless of fps

    val vPMatrix = FloatArray(16)
    var projectionMatrix = FloatArray(16)
    var viewMatrix = FloatArray(16)
}