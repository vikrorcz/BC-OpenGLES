package com.bura.common.util

class Constants {
    companion object {

        // Shader attributes
        const val A_POSITION = "a_Position"
        const val A_TEXTURE = "a_TexCoord"
        const val A_NORMAL = "a_Normal"

        // Shader uniforms
        const val U_COLOR = "u_Color"
        const val U_MATRIX = "u_Matrix"
        const val U_MODEL_MATRIX = "u_ModelMatrix"
        const val U_CAMERA_POSITION = "u_CameraPosition"
        const val U_TEXTURE = "u_TexCoord"
        const val U_LIGHT_POSITION = "u_LightPosition"
        const val U_ENABLE_LIGHTING = "u_EnableLighting"
        const val U_TIME = "u_Time"
        const val U_LEFT = "u_Left"
        const val U_TOP = "u_Top"
        const val U_WIDTH = "u_Width"
        const val U_HEIGHT = "u_Height"
        const val U_SCREEN_HEIGHT = "u_ScreenHeight"
        const val U_SCREEN_WIDTH = "u_ScreenWidth"
        const val U_WAVE_HEIGHT = "u_WaveHeight"

        // Shader constants
        const val COORDS_PER_TEXTURE_VERTEX = 2
        const val COORDS_PER_VERTEX = 3
        const val VERTEX_STRIDE = COORDS_PER_VERTEX * 4
        const val TEXTURE_STRIDE = COORDS_PER_TEXTURE_VERTEX * 4
        const val BYTES_PER_FLOAT = 4

        // Camera settings
        const val CAMERA_NEAR = 1.0f
        const val CAMERA_FAR = 1000.0f
    }
}