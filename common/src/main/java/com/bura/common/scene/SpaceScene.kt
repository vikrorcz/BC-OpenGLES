package com.bura.common.scene

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.shapes.Shape
import com.bura.common.util.GLES20
import com.bura.common.util.Matrix4f
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class SpaceScene(val engine: Engine): Scene() {

    private val ship = engine.instance.ship.clone().apply { scale = 1.5f; x -= 1000f }
    private val saturn = engine.instance.saturn.clone().apply { scale = 80f; z -= 1500f; isAlwaysRendered = true}
    private val skyBox = engine.instance.spaceSkyBox.clone().apply { scale = 8000f; isAlwaysRendered = true }
    private val asteroidsArray = mutableListOf<Shape>()

    init {
        lightPosition = floatArrayOf(-4000.0f, 1000.0f, 4000.0f)
        shapeArray = mutableListOf(skyBox, saturn, ship)
        asteroidsArray.addAsteroids()
        shapeArray.addAll(asteroidsArray)
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
    }

    override fun updateCamera() {
        if (ship.x < 600f) {
            lookAtShip(0f, 10f, 10f)
        }
        if (ship.x in 600f..1400f) {
            rotateAroundShip()
        }
        if (ship.x >= 1400f) {
            lookAtShip(engine.camera.eyeX, engine.camera.eyeY, engine.camera.eyeZ)
        }
    }

    private fun lookAtShip(eyeX: Float, eyeY: Float, eyeZ: Float) {
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

        engine.camera.setEye(eyeX, eyeY, eyeZ)
        engine.camera.setLook(lookAtX, lookAtY, lookAtZ)
    }

    private var angle = 0.0f
    private fun rotateAroundShip() {
        val radius = 60.0f
        val speed = 0.004f

        angle += speed
        if (angle > 2 * Math.PI.toFloat()) {
            angle -= 2 * Math.PI.toFloat()
        }

        val eyeX = ship.x + radius * cos(angle)
        val eyeY = ship.y + radius * sin(angle) * 0.3f
        val eyeZ = ship.z + radius * sin(angle)

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
            0.0f,
        )

        engine.camera.setEye(eyeX, eyeY, eyeZ)
        engine.camera.setLook(lookAtX, lookAtY, lookAtZ)
    }

    override fun updateLogic() {
        skyBox.x = engine.camera.eyeX
        skyBox.y = engine.camera.eyeY
        skyBox.z = engine.camera.eyeZ

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
                renderDistance = 1500.0f
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