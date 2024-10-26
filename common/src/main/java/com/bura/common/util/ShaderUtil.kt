package com.bura.common.util

import com.bura.common.engine.Engine.Companion.gles20
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

class ShaderUtil {
    companion object {

        fun createProgram(path: String): Int {
            val program = gles20.glCreateProgram()
            val vertexShader: Int = createShader("$path.vert", GLES20.GL_VERTEX_SHADER)
            val fragmentShader: Int = createShader("$path.frag", GLES20.GL_FRAGMENT_SHADER)
            gles20.glAttachShader(program, vertexShader)
            gles20.glAttachShader(program, fragmentShader)
            gles20.glLinkProgram(program)
            return program
        }

        private fun createShader(resource: String, type: Int): Int {
            val shader: Int = gles20.glCreateShader(type)
            val source: ByteBuffer = IOUtils.ioResourceToByteBuffer(resource, 8192)
            gles20.glShaderSource(shader, source.toUTF8String())
            gles20.glCompileShader(shader)
            return shader
        }

        private fun ByteBuffer.toUTF8String(): String {
            val bytes = ByteArray(remaining())
            get(bytes)
            return String(bytes, StandardCharsets.UTF_8)
        }
    }
}