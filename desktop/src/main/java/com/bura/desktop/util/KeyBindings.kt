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

        if (Input.keys[GLFW.GLFW_KEY_W]) {
            engine.camera.x += cos(engine.camera.yaw - Math.PI.toFloat() / 2.0f) * 10f
            engine.camera.z -= sin(engine.camera.yaw - Math.PI.toFloat() / 2.0f) * 10f
        }

        if (Input.keys[GLFW.GLFW_KEY_S]) {
            engine.camera.x += cos(engine.camera.yaw + Math.PI.toFloat() / 2.0f) * 10f
            engine.camera.z -= sin(engine.camera.yaw + Math.PI.toFloat() / 2.0f) * 10f
        }

        if (Input.keys[GLFW.GLFW_KEY_A]) {
            engine.camera.x += cos(engine.camera.yaw) * 10f
            engine.camera.z -= sin(engine.camera.yaw) * 10f
        }

        if (Input.keys[GLFW.GLFW_KEY_D]) {
            engine.camera.x += cos(engine.camera.yaw + Math.PI.toFloat()) * 10f
            engine.camera.z -= sin(engine.camera.yaw + Math.PI.toFloat()) * 10f
        }

        if (Input.keys[GLFW.GLFW_KEY_SPACE]) {
            engine.camera.y += 10f
        }

        if (Input.keys[GLFW.GLFW_KEY_LEFT_SHIFT]) {
            engine.camera.y -= 10f
        }
    }
}