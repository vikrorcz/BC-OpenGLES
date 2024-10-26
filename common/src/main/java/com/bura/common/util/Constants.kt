package com.bura.common.util

class Constants {
    companion object {

        // Shader attributes
        const val A_POSITION = "a_Position"
        const val A_TEXTURE = "a_TexCoord"

        // Shader uniforms
        const val U_COLOR = "u_Color"
        const val U_MATRIX = "u_Matrix"
        const val U_TEXTURE = "u_TexCoord"

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