package com.bura.common.scene

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.shapes.Shape
import com.bura.common.util.GLES20
import com.bura.common.util.Matrix4f
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class SpaceScene(val engine: Engine): Scene() {

    private val ship = engine.instance.ship.clone().apply { scale = 1.5f; x -= 1000f }
    private val saturn = engine.instance.saturn.clone().apply { scale = 80f; z -= 1500f }
    private val skyBox = engine.instance.spaceSkyBox.clone().apply { scale = 8000f }
    private val asteroidsArray = mutableListOf<Shape>()

    init {
        engine.camera.apply {
            x = 0.0f
            y = ship.y + 5.0f
            z = ship.z + 20.0f
        }
        lightPosition = floatArrayOf(-4000.0f, 1000.0f, 4000.0f)
        shapeArray = mutableListOf(skyBox, saturn, ship)
        asteroidsArray.addAsteroids()
        shapeArray.addAll(asteroidsArray)
    }

    override fun draw() {
        shapeArray.forEach { shape ->
            engine.matrixUtil.updateMatrix(shape)

            gles20.glEnable(GLES20.GL_BLEND)
            gles20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

            shape.draw()

            gles20.glDisable(GLES20.GL_BLEND)

            engine.matrixUtil.restoreMatrix()
        }
    }

    override fun updateCamera() {
        if (ship.x < 600f) {
            lookAtShip()
        }
        if (ship.x in 600f..1400f) {
            rotateAroundShip()
        }
        if (ship.x >= 1400f) {
            lookAtShip()
        }
    }

    private fun lookAtShip() {
        val directionX = ship.x - engine.camera.x
        val directionY = ship.y - engine.camera.y
        val directionZ = ship.z - engine.camera.z
        val length = sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ.toDouble())

        val x = directionX / length.toFloat()
        val y = directionY / length.toFloat()
        val z = directionZ / length.toFloat()

        engine.camera.eyeX = x
        engine.camera.eyeY = y
        engine.camera.eyeZ = z
        Matrix4f.setLookAt(
            engine.viewMatrix,
            0f,
            0f,
            0f,
            x,
            y,
            z,
            0.0f,
            1.0f,
            0.0f,
        )

        Matrix4f.translateM(engine.viewMatrix, 0, -engine.camera.x, -engine.camera.y, -engine.camera.z)
    }

    private fun rotateAroundShip() {
        val directionX = ship.x - engine.camera.x
        val directionY = ship.y - engine.camera.y
        val directionZ = ship.z - engine.camera.z
        val length = sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ.toDouble())

        val x = directionX / length.toFloat()
        val y = directionY / length.toFloat()
        val z = directionZ / length.toFloat()

        engine.camera.eyeX = x
        engine.camera.eyeY = y
        engine.camera.eyeZ = z
        Matrix4f.setLookAt(
            engine.viewMatrix,
            0f,
            0f,
            0f,
            x,
            y,
            z,
            0.0f,
            1.0f,
            0.0f,
        )

        val targetX = ship.x
        val targetY = ship.y
        val targetZ = ship.z

        val smoothness = 0.05f
        val verticalAmplitude = 5.0f
        val oscillationPeriod = 10.0
        val rotationalSpeed = 0.3f
        val timeCam = System.currentTimeMillis() / 1000.0
        val verticalOffset = verticalAmplitude * sin(2 * Math.PI * timeCam / oscillationPeriod).toFloat()
        val adjustedTargetY = targetY + verticalOffset

        // Make camera follow the model
        engine.camera.x += (targetX - engine.camera.x) * smoothness
        engine.camera.y += (adjustedTargetY - engine.camera.y) * smoothness
        engine.camera.z += (targetZ - engine.camera.z) * smoothness

        // Make camera rotate around the model
        val angle = rotationalSpeed * timeCam
        val cameraDistance = 60f
        engine.camera.x = targetX + cameraDistance * cos(angle).toFloat()
        engine.camera.z = targetZ + cameraDistance * sin(angle).toFloat()

        Matrix4f.translateM(engine.viewMatrix, 0, -engine.camera.x, -engine.camera.y, -engine.camera.z)
    }

    override fun updateLogic() {
        asteroidsArray.forEachIndexed { index, shape ->
            shape.rotationX += (index * 0.01f).coerceIn(0.01f, 0.3f) * engine.speedMultiplier
            shape.rotationY += (index * 0.01f).coerceIn(0.01f, 0.3f) * engine.speedMultiplier
            shape.rotationZ += (index * 0.01f).coerceIn(0.01f, 0.3f) * engine.speedMultiplier
        }

        if (ship.x < 600f) {
            ship.x += 1.0f * engine.speedMultiplier
            shipMovement()
        }
        if (ship.x in 600f..1400f) {
            ship.x += 0.6f * engine.speedMultiplier
            shipMovement()
        }
        if (ship.x >= 1400f) {
            ship.x += 1.5f * engine.speedMultiplier
        }
        if (ship.x >= 2100f) {
            engine.scene = LandingScene(engine)
        }
    }

    private fun MutableList<Shape>.addAsteroids() {
        val random = Random(69108591651)
        val numAsteroids = 500
        val radius = 1500f
        val centerX = saturn.x
        val centerY = saturn.y
        val centerZ = saturn.z

        for (i in 1..numAsteroids) {
            val angle = (i.toFloat() / numAsteroids) * 360f
            val radians = Math.toRadians(angle.toDouble())

            val asteroid = engine.instance.asteroid.clone().apply {
                scale = random.nextFloat() * 1.32f

                val margin = 1.2f
                val randomRadiusOffset = ((random.nextFloat() - 0.2f) * radius * margin)
                val xPosition = centerX + (radius + randomRadiusOffset) * cos(radians)
                val zPosition = centerZ + (radius + randomRadiusOffset) * sin(radians)

                x = xPosition.toFloat()
                y = centerY + (random.nextFloat() * 150f) - 75f
                z = zPosition.toFloat()

                rotationX = random.nextFloat() * 360f
                rotationY = random.nextFloat() * 360f
                rotationZ = random.nextFloat() * 360f
            }
            this.add(asteroid)
        }
    }

    private var accumulatedRotation = 0f
    private val rotationSpeed = 0.75f
    private var rotatingRight = true
    private var rotatingLeft = false
    private var resetRotation = false
    private var maxRotation = 50f
    private val rotationThreshold = 30f

    private fun shipMovement() {
        ship.z += accumulatedRotation * 0.015f * engine.speedMultiplier
        if (rotatingRight && !resetRotation) {
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
        } else if (rotatingLeft && !resetRotation) {
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

        if (resetRotation) {
            if (rotatingRight) {
                if (accumulatedRotation > 0f) {
                    val remainingRotation = 0f - accumulatedRotation
                    val speedMultiplier = if (remainingRotation > -rotationThreshold) {
                        remainingRotation / -rotationThreshold
                    } else {
                        1.0f
                    }
                    accumulatedRotation -= rotationSpeed * speedMultiplier * engine.speedMultiplier
                }
                if (accumulatedRotation < 0f + 0.1f) {
                    accumulatedRotation = 0f
                    resetRotation = false
                    rotatingRight = false
                    rotatingLeft = true
                }
            } else if (rotatingLeft) {
                if (accumulatedRotation < 0f) {
                    val remainingRotation = 0f - accumulatedRotation
                    val speedMultiplier = if (remainingRotation < rotationThreshold) {
                        remainingRotation / rotationThreshold
                    } else {
                        1.0f
                    }
                    accumulatedRotation += rotationSpeed * speedMultiplier  * engine.speedMultiplier
                }
                if (accumulatedRotation > 0f - 0.1f) {
                    accumulatedRotation = 0f
                    resetRotation = false
                    rotatingRight = true
                    rotatingLeft = false
                }
            }
        }
        ship.rotationX = accumulatedRotation
    }
}