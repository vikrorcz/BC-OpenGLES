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

    init {
        shapeArray = mutableListOf(triangle)

        // Move the camera up a little so we don't clip with the objects
        engine.camera.eyeZ = 3f
    }

    override fun draw() {
        shapeArray.forEach { shape ->
            shape.draw()
        }
    }

    override fun updateCamera() {

    }

    override fun updateLogic() {

    }
}