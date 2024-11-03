package com.bura.common.scene

import com.bura.common.shapes.Shape
import com.bura.common.util.Constants

abstract class Scene {
    var shapeArray = mutableListOf<Shape>()
    var lightPosition = Constants.lightPosition

    abstract fun draw()
    abstract fun updateCamera()
    abstract fun updateLogic()
}