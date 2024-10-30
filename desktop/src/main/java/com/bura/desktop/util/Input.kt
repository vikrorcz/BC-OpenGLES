package com.bura.desktop.util

import org.lwjgl.glfw.GLFW

import org.lwjgl.glfw.GLFWKeyCallback


class Input : GLFWKeyCallback() {
    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        keys[key] = action != GLFW.GLFW_RELEASE
    }

    companion object {
        // Keycode sate list, size set according to available number of keys
        var keys = BooleanArray(65536)

        // Key down
        fun isKeyDown(keycode: Int): Boolean {
            return keys[keycode]
        }

        fun setKeyDown(keycode: Int, boolean: Boolean) {
            keys[keycode] = boolean
        }
    }
}