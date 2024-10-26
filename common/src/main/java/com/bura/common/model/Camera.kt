package com.bura.common.model

class Camera {
    var lookX: Float = 0f
    var lookY: Float = 0f
    var lookZ: Float = 0f
    var eyeX: Float = 0f
    var eyeY: Float = 0f
    var eyeZ: Float = 0f
    var yaw: Float = 180f
    var pitch: Float = 180f
    val fov: Float = 60f

    fun setEye(x: Float, y: Float, z: Float) {
        this.eyeX = x
        this.eyeY = y
        this.eyeZ = z
    }

    fun setLook(x: Float, y: Float, z: Float) {
        this.lookX = x
        this.lookY = y
        this.lookZ = z
    }
}