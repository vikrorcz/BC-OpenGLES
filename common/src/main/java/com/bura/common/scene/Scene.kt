package com.bura.common.scene

import com.bura.common.shapes.Shape

abstract class Scene {
    var shapeArray = mutableListOf<Shape>()

    abstract fun draw()
    abstract fun updateCamera()
    abstract fun updateLogic()
}