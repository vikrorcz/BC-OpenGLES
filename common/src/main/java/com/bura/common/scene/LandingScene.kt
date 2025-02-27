package com.bura.common.scene

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.shapes.Rectangle
import com.bura.common.shapes.Shape
import com.bura.common.util.Constants
import com.bura.common.util.GLES20
import com.bura.common.util.Matrix4f
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class LandingScene(val engine: Engine): Scene() {

    private val tileSize = 4800f
    private val ship = engine.instance.ship.clone().apply { scale = 25f; y = 700f }
    private val terrain = engine.instance.island.clone().apply { y = -200f; x = 260000f; scale = 300f }
    private val decorations = addDecorations()
    private val waterTileArray = mutableListOf<Shape>()
    private val skyBox = engine.instance.landingSkyBox.clone().apply { scale = 100000f; isAlwaysRendered = true }
    private val rectangle = Rectangle(engine, 0f,-400f, 0f, 500f).apply { rotationX = 270f }
    private var sequence: Sequence = Sequence.PART_1
    private enum class Sequence { PART_1, PART_2, PART_3, PART_4 }

    init {
        shapeArray = mutableListOf(ship, terrain, skyBox, rectangle)
        shapeArray.addAll(decorations)
        lightPosition = floatArrayOf(0.0f, 400.0f, 0.0f)
        waterTileArray.addWaterTiles(-tileSize)
        waterTileArray.addWaterTiles(0f)
        waterTileArray.addWaterTiles(tileSize)
    }

    private fun MutableList<Shape>.addWaterTiles(posX: Float, posZ: Float = tileSize / 2f) {
        val water = engine.instance.water.clone().apply {
            scale = 80f
            x = posX
            z = posZ
        }
        this.add(water)
    }

    override fun draw() {
        shapeArray.forEach { shape ->
            if (shape.isOnScreen()) {
                engine.matrixUtil.updateMatrix(shape)

                gles20.glEnable(GLES20.GL_BLEND)
                gles20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

                shape.draw()

                gles20.glDisable(GLES20.GL_BLEND)

                engine.matrixUtil.restoreMatrix()
            }
        }

        waterTileArray.forEach { shape ->
            if (shape.isOnScreen()) {
                engine.matrixUtil.updateMatrix(shape)

                shape.draw()

                engine.matrixUtil.restoreMatrix()
            }
        }
    }

    private var shipXMultiplier = 1.0f
    private var shipSpeed = 25.0f

    override fun updateCamera() {
        when (sequence) {
            Sequence.PART_1 -> lookAtShip()
            Sequence.PART_2 -> lookAtShipAndMoveBehind()
            Sequence.PART_3 -> rotateAroundShip()
            Sequence.PART_4 -> lookAtShipAndFlyAway()
        }
    }

    private fun lookAtShip() {
        val lookAtX = ship.x
        val lookAtY = ship.y
        val lookAtZ = ship.z

        Matrix4f.setLookAt(
            engine.viewMatrix,
            eyeX,
            eyeY,
            eyeZ,
            lookAtX,
            lookAtY,
            lookAtZ,
            0.0f,
            1.0f,
            0.0f
        )

        // This is a hack so the frustum culler renders water that is slightly behind the eye
        val directionX = lookAtX - eyeX
        val directionY = lookAtY - eyeY
        val directionZ = lookAtZ - eyeZ

        val length = sqrt((directionX * directionX + directionY * directionY + directionZ * directionZ).toDouble()).toFloat()
        val normalizedDirX = directionX / length
        val normalizedDirY = directionY / length
        val normalizedDirZ = directionZ / length
        val modifiedEyeX = eyeX - normalizedDirX * Constants.DISTANCE_THRESHOLD
        val modifiedEyeY = eyeY - normalizedDirY * Constants.DISTANCE_THRESHOLD
        val modifiedEyeZ = eyeZ - normalizedDirZ * Constants.DISTANCE_THRESHOLD

        engine.camera.setEye(modifiedEyeX, modifiedEyeY, modifiedEyeZ)
        engine.camera.setLook(lookAtX, lookAtY, lookAtZ)
    }

    private var flyAway = 0f
    private fun lookAtShipAndFlyAway() {
        val radius = 600.0f
        val speed = 0.004f

        cameraAngle += speed
        if (cameraAngle > 2 * Math.PI.toFloat()) {
            cameraAngle -= 2 * Math.PI.toFloat()
        }

        if (flyAway < 3000f) {
            flyAway += 1.35f
        }

        val eyeX = ship.x + 1000f - (flyAway * 2.5).toFloat()
        val eyeY = ship.y + radius * sin(cameraAngle) * 0.3f + 200f + flyAway
        val eyeZ = ship.z + radius * sin(cameraAngle)

        val lookAtX = ship.x
        val lookAtY = ship.y
        val lookAtZ = ship.z

        Matrix4f.setLookAt(
            engine.viewMatrix,
            eyeX,
            eyeY,
            eyeZ,
            lookAtX,
            lookAtY,
            lookAtZ,
            0.0f,
            1.0f,
            0.0f
        )

        // This is a hack so the frustum culler renders water that is slightly behind the eye
        val directionX = lookAtX - eyeX
        val directionY = lookAtY - eyeY
        val directionZ = lookAtZ - eyeZ

        val length = sqrt((directionX * directionX + directionY * directionY + directionZ * directionZ).toDouble()).toFloat()
        val normalizedDirX = directionX / length
        val normalizedDirY = directionY / length
        val normalizedDirZ = directionZ / length
        val modifiedEyeX = eyeX - normalizedDirX * Constants.DISTANCE_THRESHOLD
        val modifiedEyeY = eyeY - normalizedDirY * Constants.DISTANCE_THRESHOLD
        val modifiedEyeZ = eyeZ - normalizedDirZ * Constants.DISTANCE_THRESHOLD

        engine.camera.setEye(modifiedEyeX, modifiedEyeY, modifiedEyeZ)
        engine.camera.setLook(lookAtX, lookAtY, lookAtZ)
    }


    private var eyeX: Float = tileSize - (tileSize / 3)
    private var eyeY: Float = 400.0f
    private var eyeZ: Float = 400.0f

    private fun lookAtShipAndMoveBehind() {
        val lookAtX = ship.x
        val lookAtY = ship.y
        val lookAtZ = ship.z

        when (ship.x) {
            in 0f..50000f -> {
                // Slowly move towards ship
                val targetX = ship.x + 800f
                val targetY = ship.y + 200f
                val targetZ = ship.z

                eyeX += (targetX - eyeX) * 0.01f
                eyeY += (targetY - eyeY) * 0.01f
                eyeZ += (targetZ - eyeZ) * 0.01f
            }
            in 50000f..120000f -> {
                // Move to the left side of the ship
                val targetX = ship.x + 3500f
                val targetY = ship.y + 150f
                val targetZ = ship.z - 400f

                eyeX += (targetX - eyeX) * 0.01f
                eyeY += (targetY - eyeY) * 0.01f
                eyeZ += (targetZ - eyeZ) * 0.01f
            }
            in 120000f..150000f -> {
                // Move slightly ahead of the ship
                val targetX = ship.x + 4500f
                val targetY = ship.y + 150f
                val targetZ = ship.z - 300f

                eyeX += (targetX - eyeX) * 0.01f
                eyeY += (targetY - eyeY) * 0.01f
                eyeZ += (targetZ - eyeZ) * 0.01f

            }
            in 150000f..200000f -> {
                eyeX = ship.x - 500f
            }
        }

        Matrix4f.setLookAt(
            engine.viewMatrix,
            eyeX,
            eyeY,
            eyeZ,
            lookAtX,
            lookAtY,
            lookAtZ,
            0.0f,
            1.0f,
            0.0f
        )

        // This is a hack so the frustum culler renders water that is slightly behind the eye
        val directionX = lookAtX - eyeX
        val directionY = lookAtY - eyeY
        val directionZ = lookAtZ - eyeZ

        val length = sqrt((directionX * directionX + directionY * directionY + directionZ * directionZ).toDouble()).toFloat()
        val normalizedDirX = directionX / length
        val normalizedDirY = directionY / length
        val normalizedDirZ = directionZ / length
        val modifiedEyeX = eyeX - normalizedDirX * Constants.DISTANCE_THRESHOLD
        val modifiedEyeY = eyeY - normalizedDirY * Constants.DISTANCE_THRESHOLD
        val modifiedEyeZ = eyeZ - normalizedDirZ * Constants.DISTANCE_THRESHOLD

        engine.camera.setEye(modifiedEyeX, modifiedEyeY, modifiedEyeZ)
        engine.camera.setLook(lookAtX, lookAtY, lookAtZ)
    }

    private var cameraAngle = 0.0f
    private fun rotateAroundShip() {
        val radius = 600.0f
        val speed = 0.004f

        cameraAngle += speed
        if (cameraAngle > 2 * Math.PI.toFloat()) {
            cameraAngle -= 2 * Math.PI.toFloat()
        }

        var eyeX = ship.x + 1000f
        var eyeY = ship.y + radius * sin(cameraAngle) * 0.3f + 200f
        var eyeZ = ship.z + radius * sin(cameraAngle)

        val lookAtX = ship.x
        val lookAtY = ship.y
        val lookAtZ = ship.z

        val targetX = ship.x + 4500f
        val targetY = ship.y + 150f
        val targetZ = ship.z - 300f

        eyeX += (targetX - eyeX) * 0.01f
        eyeY += (targetY - eyeY) * 0.01f
        eyeZ += (targetZ - eyeZ) * 0.01f

        Matrix4f.setLookAt(
            engine.viewMatrix,
            eyeX,
            eyeY,
            eyeZ,
            lookAtX,
            lookAtY,
            lookAtZ,
            0.0f,
            1.0f,
            0.0f,
        )

        // This is a hack so the frustum culler renders water that is slightly behind the eye
        val directionX = lookAtX - eyeX
        val directionY = lookAtY - eyeY
        val directionZ = lookAtZ - eyeZ

        val length = sqrt((directionX * directionX + directionY * directionY + directionZ * directionZ).toDouble()).toFloat()
        val normalizedDirX = directionX / length
        val normalizedDirY = directionY / length
        val normalizedDirZ = directionZ / length
        val modifiedEyeX = eyeX - normalizedDirX * Constants.DISTANCE_THRESHOLD
        val modifiedEyeY = eyeY - normalizedDirY * Constants.DISTANCE_THRESHOLD
        val modifiedEyeZ = eyeZ - normalizedDirZ * Constants.DISTANCE_THRESHOLD

        engine.camera.setEye(modifiedEyeX, modifiedEyeY, modifiedEyeZ)
        engine.camera.setLook(lookAtX, lookAtY, lookAtZ)
    }

    private var lightPosY = 0f
    override fun updateLogic() {
        skyBox.x = engine.camera.eyeX
        skyBox.y = engine.camera.eyeY + 2000f
        skyBox.z = engine.camera.eyeZ

        rectangle.x = engine.camera.eyeX
        rectangle.z = engine.camera.eyeZ

        if (ship.x > 0f) { // A hack so the sun does not appear on the other side
            lightPosY = 0f - engine.camera.lookY
            lightPosition = floatArrayOf(
                engine.camera.eyeX + 100000f,
                lightPosY,
                lightPosition[2],
            )
        }

        when (sequence) {
            Sequence.PART_1 -> {
                landingMovement()
                createAdditionalWaterTiles()
            }
            Sequence.PART_2 -> {
                shipMovement()
                createAdditionalWaterTiles()
            }
            Sequence.PART_3 -> {
                islandMovement()
                createAdditionalWaterTiles()
            }

            else -> Unit // No part 4 logic
        }
    }


    private fun createAdditionalWaterTiles() {
        val centerX = (engine.camera.eyeX / tileSize).toInt() * tileSize
        val centerZ = (engine.camera.eyeZ / tileSize).toInt() * tileSize
        val radius = 7

        // Create a 2D map of tiles
        for (i in -radius..radius) {
            for (j in -radius..radius) {
                val tileX = centerX + (i * tileSize)
                val tileZ = centerZ + (j * tileSize)

                if (!waterTileExistsAt(tileX, tileZ)) {
                    waterTileArray.addWaterTiles(tileX, tileZ)
                }
            }
        }

        // Remove tiles outside of range
        val waterTileIterator = waterTileArray.iterator()
        while (waterTileIterator.hasNext()) {
            val waterTile = waterTileIterator.next()
            val distanceX = abs(waterTile.x - centerX)
            val distanceZ = abs(waterTile.z - centerZ)

            if (distanceX > tileSize * (radius + 1) || distanceZ > tileSize * (radius + 1)) {
                waterTileIterator.remove()
            }
        }
    }

    private fun waterTileExistsAt(x: Float, z: Float): Boolean {
        return waterTileArray.any { it.x == x && it.z == z }
    }

    private var angle = 380.0f
    private val radiusX = 8500.0f
    private val radiusZ = 5000.0f
    private val centerX = -2000.0f
    private val centerZ = 1500.0f
    private var speed = 0.005f

    private var shipY = 4000f
    private var shipYMultiplier = 1.0f
    private var shipX = centerX + radiusX * cos(angle)
    private var shipZ = centerZ + radiusZ * sin(angle)

    private fun landingMovement() {
        shipX += 25.0f * engine.speedMultiplier
        ship.rotationX = accumulatedRotation

        when (shipX) {
            in Float.NEGATIVE_INFINITY..-1500f -> {
                bankLeft()
            }
            in -1500f..4000f -> {
                bankRight()
            }
            in  4000f..9000f -> {
                bankLeft()
            }
            in 9000f..Float.POSITIVE_INFINITY -> {
                bankRight()
            }
        }

        if (shipX <= 5000f) {
            angle += speed * engine.speedMultiplier
            shipZ = centerZ + radiusZ * sin(angle)
        }

        if (shipY > 400f) {
            val distanceToTarget = shipY - 400f

            shipYMultiplier = distanceToTarget / 4000f
            shipY -= 12.0f * shipYMultiplier * engine.speedMultiplier

            if (shipY < 400f) {
                shipY = 400f
            }
        }

        if (ship.x >= 5000f) {
            if (speed > -0.005f) {
                speed -= 0.0002f * engine.speedMultiplier
            }
            angle += speed * engine.speedMultiplier
            shipZ = centerZ + radiusZ * sin(angle)
        }

        if (ship.x >= tileSize * 4) {
            shipY = 400f
            shipZ = 0f
            sequence = Sequence.PART_2
        }

        ship.x = shipX
        ship.z = shipZ
        ship.y = shipY
    }

    var waveHeightUniform = 5.0f
    private var turnAngle = 0.0f
    private val radius = 30f

    private fun islandMovement() {
        ship.y = 1000f
        if (waveHeightUniform > 1.0f) {
            waveHeightUniform -= 0.005f * engine.speedMultiplier
        }
        ship.rotationX = accumulatedRotation

        when (ship.x) {
            in Float.NEGATIVE_INFINITY..220000f  -> {
                ship.x += shipSpeed * shipXMultiplier * engine.speedMultiplier
            }
            in 220000f..245000f -> {
                val newAngle = 0.001f * engine.speedMultiplier
                turnAngle += newAngle
                ship.x += 20f * shipXMultiplier * engine.speedMultiplier

                if (ship.x > 240000f) {
                    bankRight()
                } else {
                    bankLeft()
                }

                val progress = if (ship.x > 240000f) (250000f - ship.x) / 25000f else 1.0f

                ship.z -= radius * sin(turnAngle) * progress.coerceIn(0.2f, 1.0f) * 2.5f

            }
            in 245000f..257000f -> {
                if (accumulatedRotation > 0) {
                    accumulatedRotation -= 1f * engine.speedMultiplier
                }

                ship.x += 20f * shipXMultiplier * engine.speedMultiplier
            }
            in 257000f..275000f -> {
                if (ship.rotationY > -90f) {
                    ship.rotationY -= 0.1f
                    val radians = Math.toRadians(ship.rotationY.toDouble()).toFloat()
                    ship.x += cos(radians) * engine.speedMultiplier * 25f
                    ship.z -= sin(radians) * engine.speedMultiplier * 25f
                } else if (ship.rotationY < -90f) {
                    if (ship.z < -1000f) {
                        ship.z += 12.5f * engine.speedMultiplier
                    } else {
                        // Ending the scene
                        sequence = Sequence.PART_4
                    }
                }
            }
        }
    }

    private var accumulatedRotation = 0f
    private val rotationSpeed = 1.5f
    private var rotatingRight = true
    private var rotatingLeft = false
    private var resetRotation = false
    private var maxRotation = 50f
    private var rotationThreshold = 30f

    private fun shipMovement() {
        if (shipXMultiplier < 3.1f) {
            shipXMultiplier += 0.002f * engine.speedMultiplier
        }

        ship.x += shipSpeed * shipXMultiplier * engine.speedMultiplier
        ship.z += accumulatedRotation * 0.1f * engine.speedMultiplier
        ship.rotationX = accumulatedRotation

        if (resetRotation) {
            resetRotation()
        }

        if (ship.x > 200000f) {
            ship.x = 200000f
            sequence = Sequence.PART_3
        }
    }

    private fun bankRight() {
        if (accumulatedRotation < maxRotation) {
            val remainingRotation = maxRotation - accumulatedRotation
            val speedMultiplier = if (remainingRotation < rotationThreshold) {
                remainingRotation / rotationThreshold
            } else {
                1.0f
            }
            accumulatedRotation += rotationSpeed * speedMultiplier * engine.speedMultiplier
        }
        if (accumulatedRotation > maxRotation - 0.1f) {
            resetRotation = true
        }
    }

    private fun bankLeft() {
        if (accumulatedRotation > -maxRotation) {
            val remainingRotation = -maxRotation - accumulatedRotation
            val speedMultiplier = if (remainingRotation > -rotationThreshold) {
                remainingRotation / -rotationThreshold
            } else {
                1.0f
            }
            accumulatedRotation -= rotationSpeed * speedMultiplier * engine.speedMultiplier
        }
        if (accumulatedRotation < -maxRotation + 0.1f) {
            resetRotation = true
        }
    }

    private fun resetRotation() {
        val tolerance = 0.1f
        val slowDownThreshold = 10f

        if (rotatingRight) {
            if (accumulatedRotation > 0f) {
                val remainingRotation = accumulatedRotation
                val speedMultiplier = if (remainingRotation < slowDownThreshold) {
                    remainingRotation / slowDownThreshold
                } else {
                    1.0f
                }
                accumulatedRotation -= rotationSpeed * speedMultiplier * engine.speedMultiplier
                if (accumulatedRotation <= tolerance) {
                    accumulatedRotation = 0f
                    resetRotation = false
                    rotatingRight = false
                    rotatingLeft = true
                }
            }

        } else if (rotatingLeft) {
            if (accumulatedRotation < 0f) {
                val remainingRotation = -accumulatedRotation
                val speedMultiplier = if (remainingRotation < slowDownThreshold) {
                    remainingRotation / slowDownThreshold
                } else {
                    1.0f
                }
                accumulatedRotation += rotationSpeed * speedMultiplier * engine.speedMultiplier

                if (accumulatedRotation >= -tolerance) {
                    accumulatedRotation = 0f
                    resetRotation = false
                    rotatingLeft = false
                    rotatingRight = true
                }
            }
        }
    }


    private fun addDecorations(): MutableList<Shape> {
        return mutableListOf(
            engine.instance.palm.clone().apply { x = 265498.38f; y = 297.09097f; z = 2602.009f; scale = 22.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 265222.97f; y = 408.38556f; z = 2445.8816f; scale = 22.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 265764.44f; y = 189.59576f; z = 1931.9264f; scale = 22.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 264979.75f; y = 506.6405f; z = 2293.497f; scale = 22.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 264505.44f; y = 698.28467f; z = 2370.1938f; scale = 22.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 264439.38f; y = 669.5269f; z = -2141.1023f; scale = 16.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 264615.66f; y = 654.09814f; z = -1431.604f; scale = 16.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 264904.88f; y = 628.8011f; z = -2087.5498f; scale = 16.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 265190.8f; y = 603.78107f; z = -1616.2571f; scale = 16.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
            engine.instance.palm.clone().apply { x = 264219.16f; y = 688.79175f; z = -1335.485f; scale = 16.0f; rotationX = 0.0f; rotationY = 0.0f; rotationZ = 0.0f },
        )
    }
}