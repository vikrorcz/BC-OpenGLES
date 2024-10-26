package com.bura.opengles.engine

import android.opengl.GLSurfaceView
import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.engine.MyRenderer
import com.bura.common.util.Constants
import com.bura.common.util.Matrix4f
import com.bura.opengles.util.AndroidGles20
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.tan

private lateinit var engine: Engine
private lateinit var myRenderer: MyRenderer

class MyRenderer :  GLSurfaceView.Renderer {
    override fun onSurfaceCreated(unused: GL10?, eglConfig: EGLConfig?) {
        engine = Engine(deviceGles20 = AndroidGles20())
        myRenderer = MyRenderer(engine)
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        gles20.glViewport(0, 0, width, height)
        val ratio: Float = width.toFloat() / height
        engine.screenWidthPixel = width
        engine.screenHeightPixel = height

        val fov = engine.camera.fov
        val top: Float = tan(Math.toRadians((fov / 2.0f).toDouble())).toFloat() * 1f
        val bottom = -top
        val right = top * ratio
        val left = -right

        Matrix4f.frustumM(engine?.projectionMatrix, left, right, bottom, top, Constants.CAMERA_NEAR, Constants.CAMERA_FAR)
    }

    override fun onDrawFrame(unused: GL10?) {
        myRenderer.draw()
    }
}