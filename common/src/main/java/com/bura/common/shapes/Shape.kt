package com.bura.common.shapes

import com.bura.common.engine.Engine
import com.bura.common.util.Constants
import java.nio.FloatBuffer
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

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
    open var renderDistance = Constants.MAX_RENDER_DISTANCE
    open var isAlwaysRendered: Boolean = false

    fun isOnScreen(): Boolean {
        val camX = engine.camera.x
        val camY = engine.camera.y
        val camZ = engine.camera.z
        val shapeX = x
        val shapeY = y
        val shapeZ = z
        val cameraDirX = engine.camera.eyeX.toDouble()
        val cameraDirY = engine.camera.eyeY.toDouble()
        val cameraDirZ = engine.camera.eyeZ.toDouble()

        val deltaX = shapeX - camX.toDouble()
        val deltaY = shapeY - camY.toDouble()
        val deltaZ = shapeZ - camZ.toDouble()
        val dotProduct = deltaX * cameraDirX + Constants.DISTANCE_THRESHOLD +
                deltaY * cameraDirY + Constants.DISTANCE_THRESHOLD +
                deltaZ * cameraDirZ + Constants.DISTANCE_THRESHOLD

        val shapeDistanceFromCamera = getDistance(deltaX, deltaY, deltaZ)
        val cameraDirectionDistance = getDistance(cameraDirX, cameraDirY, cameraDirZ)
        val angle = Math.toDegrees(acos(dotProduct / (shapeDistanceFromCamera * cameraDirectionDistance)))

        val fov = engine.camera.fov + Constants.FOV_THRESHOLD

        // Out of render distance condition
        if (isObjectOutOfRenderDistance(deltaX, deltaY, deltaZ) && !isAlwaysRendered) return false

        // Out of field of view condition
        if (angle >= fov && !isAlwaysRendered) {
            return false
        }

        return true
    }

    private fun isObjectOutOfRenderDistance(dirX: Double, dirY: Double, dirZ: Double): Boolean = getDistance(
        deltaX = dirX, deltaY = dirY, deltaZ = dirZ,
    ) >= renderDistance

    private fun getDistance(deltaX: Double, deltaY: Double, deltaZ: Double): Double = sqrt(
        deltaX.pow(2) + deltaY.pow(2) + deltaZ.pow(2)
    )

    abstract fun draw()
}