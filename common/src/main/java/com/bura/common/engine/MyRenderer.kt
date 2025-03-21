package com.bura.common.engine

import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.util.GLES20
import com.bura.common.util.Matrix4f
import kotlin.math.cos
import kotlin.math.sin

class MyRenderer(
    private val engine: Engine,
) {
    /**
     * Main draw loop, that is shared between all platforms
     */

    private var time = System.nanoTime()
    private val targetFrameTime = 1000.0f / 60.0f  // 1 second / 60 frames = 16.67 ms per frame
    private var lastTime = System.nanoTime()

    fun draw() {
        time = System.nanoTime()
        engine.deltaTime = (time - lastTime).toFloat() / 1000000.0f
        lastTime = time
        engine.speedMultiplier = engine.deltaTime / targetFrameTime
        engine.totalTime += engine.deltaTime / 1000.0f

        engine.fpsCounter.logFrame()

        gles20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        gles20.glEnable(GLES20.GL_DEPTH_TEST)
        gles20.glEnable(GLES20.GL_CULL_FACE)

        //freeCamera()
        engine.scene.updateCamera()

        Matrix4f.multiplyMM(engine.vPMatrix, engine.projectionMatrix, engine.viewMatrix)
        engine.scene.updateLogic()
        engine.scene.draw()

        gles20.glFinish()
    }

    private fun freeCamera() {
        val directionX = sin(engine.camera.yaw) * cos(engine.camera.pitch)
        val directionY = sin(engine.camera.pitch)
        val directionZ = cos(engine.camera.yaw) * cos(engine.camera.pitch)

        var eyeX = engine.camera.eyeX
        var eyeY = engine.camera.eyeY
        var eyeZ = engine.camera.eyeZ

        val lookX = eyeX + directionX
        val lookY = eyeY + directionY
        val lookZ = eyeZ + directionZ

        Matrix4f.setLookAt(
            engine.viewMatrix,
            eyeX,
            eyeY,
            eyeZ,
            lookX,
            lookY,
            lookZ,
            0.0f,
            1.0f,
            0.0f,
        )

        // Eye position is setup in KeyBindings.kt
        engine.camera.setLook(lookX, lookY, lookZ)
    }
}