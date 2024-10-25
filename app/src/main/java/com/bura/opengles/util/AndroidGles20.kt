package com.bura.opengles.util

import android.opengl.GLES20
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer
import com.bura.common.util.GLES20 as GlesUtil

// https://github.com/libgdx/libgdx/blob/d52faeaac2be55c4a437f1b58ed5085c5d99e5a5/backends/gdx-backend-android/src/com/badlogic/gdx/backends/android/AndroidGL20.java#L27

class AndroidGles20: GlesUtil {
    override fun glClearColor(red: Float, green: Float, blue: Float, alpha: Float) {
        GLES20.glClearColor(red, green, blue, alpha)
    }

    override fun glViewport(x: Int, y: Int, width: Int, height: Int) {
        GLES20.glViewport(x, y, width, height)
    }

    override fun glShaderSource(shader: Int, string: String) {
        GLES20.glShaderSource(shader, string)
    }

    override fun glCreateShader(type: Int): Int {
        return GLES20.glCreateShader(type)
    }

    override fun glCompileShader(shader: Int) {
        GLES20.glCompileShader(shader)
    }

    override fun glGetShaderiv(shader: Int, pname: Int, params: IntBuffer) {
        GLES20.glGetShaderiv(shader, pname, params)
    }

    override fun glDeleteShader(shader: Int) {
        GLES20.glDeleteShader(shader)
    }

    override fun glAttachShader(program: Int, shader: Int) {
        GLES20.glAttachShader(program, shader)
    }

    override fun glLinkProgram(program: Int) {
        GLES20.glLinkProgram(program)
    }

    override fun glValidateProgram(program: Int) {
        GLES20.glValidateProgram(program)
    }

    override fun glCreateProgram(): Int {
        return GLES20.glCreateProgram()
    }

    override fun glClear(mask: Int) {
        GLES20.glClear(mask)
    }

    override fun glFinish() {
        GLES20.glFinish()
    }

    override fun glUseProgram(program: Int) {
        GLES20.glUseProgram(program)
    }

    override fun glGetAttribLocation(program: Int, name: String): Int {
        return GLES20.glGetAttribLocation(program, name)
    }

    override fun glEnableVertexAttribArray(index: Int) {
        GLES20.glEnableVertexAttribArray(index)
    }

    override fun glVertexAttribPointer(
        index: Int,
        size: Int,
        type: Int,
        normalized: Boolean,
        stride: Int,
        ptr: FloatBuffer
    ) {
        GLES20.glVertexAttribPointer(index, size, type, normalized, stride, ptr)
    }

    override fun glVertexAttribPointer(
        index: Int,
        size: Int,
        type: Int,
        normalized: Boolean,
        stride: Int,
        ptr: Int
    ) {
        GLES20.glVertexAttribPointer(index, size, type, normalized, stride, ptr)
    }

    override fun glGetUniformLocation(program: Int, name: String): Int {
        return GLES20.glGetUniformLocation(program, name)
    }

    override fun glUniform4fv(location: Int, floatArray: FloatArray) {
        GLES20.glUniform4fv(location, 1, floatArray, 0)
    }

    override fun glUniformMatrix3fv(
        location: Int,
        transpose: Boolean,
        value: FloatArray,
    ) {
        GLES20.glUniformMatrix3fv(location, 1, transpose, value, 0)
    }

    override fun glUniformMatrix4fv(
        location: Int,
        transpose: Boolean,
        value: FloatArray
    ) {
        GLES20.glUniformMatrix4fv(location, 1, transpose, value, 0)
    }

    override fun glDrawArrays(mode: Int, first: Int, count: Int) {
        GLES20.glDrawArrays(mode, first, count)
    }

    override fun glDisableVertexAttribArray(index: Int) {
        GLES20.glDisableVertexAttribArray(index)
    }

    override fun glActiveTexture(texture: Int) {
        GLES20.glActiveTexture(texture)
    }

    override fun glBindTexture(target: Int, texture: Int) {
        GLES20.glBindTexture(target, texture)
    }

    override fun glGenTextures(n: Int, textures: IntArray) {
        GLES20.glGenTextures(n, textures, 0)
    }

    override fun glEnable(cap: Int) {
        GLES20.glEnable(cap)
    }

    override fun glBlendFunc(sFactor: Int, dFactor: Int) {
        GLES20.glBlendFunc(sFactor, dFactor)
    }

    override fun glTexParameteri(target: Int, pname: Int, param: Int) {
        GLES20.glTexParameteri(target, pname, param)
    }

    override fun glDrawElements(mode: Int, count: Int, type: Int, indices: Buffer) {
        if (type == GLES20.GL_UNSIGNED_SHORT) {
            //GLES20.glDrawElements(mode, indices)
            if (indices is ShortBuffer) {
                GLES20.glDrawElements(mode, count, type, indices)
            }
            if (indices is ByteBuffer) {
                GLES20.glDrawElements(mode, count, type, indices)
            }
            if (indices is IntBuffer) {
                GLES20.glDrawElements(mode, count, type, indices)
            }
        }
    }

   //override fun glDrawElements(mode: Int, count: Int, type: Int, indices: ShortBuffer) {
   //    GLES20.glDrawElements(mode, count, type, indices)
   //}

    override fun glDrawElements(mode: Int, count: Int, type: Int, indices: Int) {
        GLES20.glDrawElements(mode, count, type, indices)
    }

    override fun glUniform1i(location: Int, x: Int) {
        GLES20.glUniform1i(location, x)
    }

    override fun glUniform1f(location: Int, x: Float) {
        GLES20.glUniform1f(location, x)
    }

    override fun glUniform3fv(location: Int, floatArray: FloatArray) {
        GLES20.glUniform3fv(location, floatArray.size / 3, floatArray, 0)
    }

    override fun glTexImage2D(
        target: Int,
        level: Int,
        internalFormat: Int,
        width: Int,
        height: Int,
        border: Int,
        format: Int,
        type: Int,
        pixels: Buffer
    ) {
        GLES20.glTexImage2D(target, level, internalFormat, width, height, border, format, type, pixels)
    }

    override fun glGenerateMipmap(target: Int) {
        GLES20.glGenerateMipmap(target)
    }

    override fun glPixelStorei(pName: Int, param: Int) {
        GLES20.glPixelStorei(pName, param)
    }

    override fun glCullFace(int: Int) {
        GLES20.glCullFace(int)
    }

    override fun glDisable(int: Int) {
        GLES20.glDisable(int)
    }

    override fun glFramebufferRenderbuffer(
        target: Int,
        attachment: Int,
        renderBufferTarget: Int,
        renderBuffer: Int
    ) {
        GLES20.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer)
    }

    override fun glFramebufferTexture2D(
        target: Int,
        attachment: Int,
        texTarget: Int,
        texture: Int,
        level: Int
    ) {
        GLES20.glFramebufferTexture2D(target, attachment, texTarget, texture, level)
    }

    override fun glBindBuffer(target: Int, buffer: Int) {
        GLES20.glBindBuffer(target, buffer)
    }

    override fun glGenBuffers(n: Int, buffers: IntBuffer) {
        GLES20.glGenBuffers(n, buffers)
    }

    override fun glGenBuffers(n: Int, array: IntArray) {
        GLES20.glGenBuffers(n, array, 0)
    }

    override fun glBufferData(target: Int, size: Int, data: Buffer, usage: Int) {
        GLES20.glBufferData(target, size, data, usage)
    }
}