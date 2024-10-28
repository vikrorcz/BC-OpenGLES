package com.bura.common.shapes

import com.bura.common.engine.Engine
import java.nio.FloatBuffer

abstract class Shape(
    protected open val engine: Engine,
    open var x: Float,
    open var y: Float,
    open var z: Float,
    open var scale: Float = 1.0f,
) {
    open var initialX = 0f
    open var initialY = 0f
    open var initialZ = 0f
    open var rotationX = 0f // In degrees
    open var rotationY = 0f // In degrees
    open var rotationZ = 0f // In degrees
    open var pivotX = 0f
    open var pivotY = 0f
    open var pivotZ = 0f
    open var rotationMatrix = FloatArray(16)
    open var mTempRotationMatrix = FloatArray(16)
    open var mModelMatrix = FloatArray(16)
    open var mTempMatrix = FloatArray(16)
    open var color: FloatArray = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f) // Always pass in the color in this number format: 1.0f (not 1f)
    protected open var vertexData: FloatBuffer? = null
    protected open var vertexCount = 0

    abstract fun draw()
}