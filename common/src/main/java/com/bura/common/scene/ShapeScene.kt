package com.bura.common.scene

import com.bura.common.engine.Engine

class ShapeScene(val engine: Engine): Scene() {

    private val triangle = engine.instance.triangle.clone().apply {
        scale = 1f
        x = -2f
        y = 0f
        z = 0f
        color = floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f)
    }

    private val rectangle = engine.instance.rectangle.clone().apply {
        scale = 0.5f
        x = 0f
        y = 0f
        z = 0f
        color = floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f)
    }

    private val texture = engine.instance.texture.clone().apply {
        scale = 0.5f
        x = 2f
        y = 0f
        z = 0f
    }

    init {
        shapeArray = mutableListOf(triangle, rectangle, texture)

        // Move the camera up a little so we don't clip with the objects
        engine.camera.eyeZ = 3f
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
        rectangle.rotationX = angle
        texture.rotationY = angle
    }
}