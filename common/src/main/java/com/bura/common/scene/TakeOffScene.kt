package com.bura.common.scene

import com.bura.common.engine.Engine

class TakeOffScene(val engine: Engine): Scene() {

    private var terrain = engine.instance.terrain.clone().apply { scale = 80f; y = -10.0f }

    init {
        shapeArray = mutableListOf(terrain)
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

    }
}