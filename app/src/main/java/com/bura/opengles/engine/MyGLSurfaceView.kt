package com.bura.opengles.engine

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Build
import android.util.Log

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private var renderer: MyRenderer? = null

    init {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo

        val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000 ||
                Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86")

        if (supportsEs2) {
            /**
             * Context can be set to OpenGL ES 3.0 with setEGLContextClientVersion(3)
             * In that case shader files must have #version 100 defined on top of the file,
             * so that we can still use old shader codes with new GLSL features from ES 3.0
             */
            setEGLContextClientVersion(2)

            renderer = MyRenderer()
            setRenderer(renderer)
        } else {
            Log.e("Error", "This device does not support OpenGLES 2.0")
        }
    }
}