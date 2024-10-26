package com.bura.common.scene

import com.bura.common.engine.Engine
import com.bura.common.model.Instance

class ShapeScene(val engine: Engine, instance: Instance): Scene() {

    val triangle = instance.triangle.clone().apply {
        scale = 1f
        x = 0f
        y = 0f
        z = 0f
        color = floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f)
    }

    init {
        shapeArray = mutableListOf(triangle)
    }

    override fun draw() {
        shapeArray.forEach { shape ->
            engine.matrixUtil.updateMatrix(shape)
            shape.draw()
            engine.matrixUtil.restoreMatrix()
        }
    }

    override fun updateCamera() {

    }

    override fun updateLogic() {
        val time: Long = System.currentTimeMillis() % 16000L
        val angle = 0.090f * (time.toInt())
        triangle.rotationZ = angle
    }
}