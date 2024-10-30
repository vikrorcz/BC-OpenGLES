package com.bura.desktop.util

import com.bura.common.engine.Engine
import org.lwjgl.glfw.GLFW
import kotlin.math.cos
import kotlin.math.sin

/**
 * |----------------------------------------------|
 * |                 Key Bindings                 |
 * |----------------------------------------------|
 * | ESC - exit application menu                  |
 * | W : move forward                             |
 * | A : strafe left                              |
 * | S : move backwards                           |
 * | D : strafe right                             |
 * | SPACE : move up                              |
 * | SHIFT : move down                            |
 * |-----------------------------------------------
 */
internal class KeyBindings {

    fun update(engine: Engine, window: Long) {

        if (Input.keys[GLFW.GLFW_KEY_ESCAPE]) {
            GLFW.glfwSetWindowShouldClose(
                window,
                true,
            )
        }

        val lookX = sin(engine.camera.yaw) * cos(engine.camera.pitch)
        val lookZ = cos(engine.camera.yaw) * cos(engine.camera.pitch)

        val strafeX = cos(engine.camera.yaw)
        val strafeZ = sin(engine.camera.yaw)

        val speed = 10f

        if (Input.keys[GLFW.GLFW_KEY_W]) {
            engine.camera.eyeX += lookX * speed
            engine.camera.eyeZ += lookZ * speed
        }

        if (Input.keys[GLFW.GLFW_KEY_S]) {
            engine.camera.eyeX -= lookX * speed
            engine.camera.eyeZ -= lookZ * speed
        }

        if (Input.keys[GLFW.GLFW_KEY_A]) {
            engine.camera.eyeX += strafeX * speed
            engine.camera.eyeZ -= strafeZ * speed
        }

        if (Input.keys[GLFW.GLFW_KEY_D]) {
            engine.camera.eyeX -= strafeX * speed
            engine.camera.eyeZ += strafeZ * speed
        }

        if (Input.keys[GLFW.GLFW_KEY_SPACE]) {
            engine.camera.eyeY += speed
        }

        if (Input.keys[GLFW.GLFW_KEY_LEFT_SHIFT]) {
            engine.camera.eyeY -= speed
        }
    }
}