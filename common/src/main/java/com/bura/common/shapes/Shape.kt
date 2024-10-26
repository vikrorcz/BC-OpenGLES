package com.bura.common.shapes

import com.bura.common.engine.Engine
import java.nio.FloatBuffer

abstract class Shape(
    protected open val engine: Engine,
    open var x: Float,
    open var y: Float,
    open var z: Float,
    open var scale: Float,
) {
    open var color: FloatArray = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f) // Always pass in the color in this number format: 1.0f (not 1f)
    protected open var vertexData: FloatBuffer? = null
    protected open var vertexCount = 0

    abstract fun draw()
}