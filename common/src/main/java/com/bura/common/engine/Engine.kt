package com.bura.common.engine

import com.bura.common.util.GLES20

class Engine(
    deviceGles20: GLES20,
) {
    companion object {
        lateinit var gles20: GLES20
    }

    init {
        gles20 = deviceGles20
    }
}