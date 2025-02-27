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
        val x = sin(engine.camera.yaw) * cos(engine.camera.pitch)
        val y = sin(engine.camera.pitch)
        val z = cos(engine.camera.yaw) * cos(engine.camera.pitch)

        val eyeX = engine.camera.x
        val eyeY = engine.camera.y
        val eyeZ = engine.camera.z

        Matrix4f.setLookAt(
            engine.viewMatrix,
            eyeX,
            eyeY,
            eyeZ,
            eyeX + x,
            eyeY + y,
            eyeZ + z,
            0.0f,
            1.0f,
            0.0f,
        )
        engine.camera.eyeX = x
        engine.camera.eyeY = y
        engine.camera.eyeZ = z

    }
}