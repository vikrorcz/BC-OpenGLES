package com.bura.common.engine

import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.util.GLES20
import com.bura.common.util.Matrix4f

class MyRenderer(
    private val engine: Engine,
) {
    /**
     * Main draw loop, that is shared between all platforms
     */

    fun draw() {
        gles20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        Matrix4f.setLookAt(
            engine.viewMatrix,
            engine.camera.eyeX,
            engine.camera.eyeY,
            engine.camera.eyeZ,
            0f,
            0f,
            0f,
            0.0f,
            1.0f,
            0.0f,
        )

        Matrix4f.multiplyMM(engine.vPMatrix, engine.projectionMatrix, engine.viewMatrix)
        engine.scene.draw()
        engine.scene.updateLogic()

        gles20.glFinish()
    }
}