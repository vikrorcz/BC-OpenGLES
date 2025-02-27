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
        val eyeX = engine.camera.eyeX.toDouble()
        val eyeY = engine.camera.eyeY.toDouble()
        val eyeZ = engine.camera.eyeZ.toDouble()
        val shapeX = x
        val shapeY = y
        val shapeZ = z

        val cameraDirectionX = engine.camera.lookX - eyeX
        val cameraDirectionY = engine.camera.lookY - eyeY
        val cameraDirectionZ = engine.camera.lookZ - eyeZ

        val cameraDirectionDistance = getDistance(cameraDirectionX, cameraDirectionY, cameraDirectionZ)
        val normCameraDirX = cameraDirectionX / cameraDirectionDistance
        val normCameraDirY = cameraDirectionY / cameraDirectionDistance
        val normCameraDirZ = cameraDirectionZ / cameraDirectionDistance

        val deltaX = shapeX - eyeX
        val deltaY = shapeY - eyeY
        val deltaZ = shapeZ - eyeZ

        val dotProduct = deltaX * normCameraDirX + deltaY * normCameraDirY + deltaZ * normCameraDirZ

        val shapeDistanceFromCamera = getDistance(deltaX, deltaY, deltaZ)

        val angle = Math.toDegrees(acos((dotProduct / shapeDistanceFromCamera).coerceIn(-1.0, 1.0)))

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