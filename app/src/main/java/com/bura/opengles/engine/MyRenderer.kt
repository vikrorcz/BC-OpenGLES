package com.bura.opengles.engine

import android.opengl.GLSurfaceView
import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.engine.MyRenderer
import com.bura.opengles.util.AndroidGles20
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

private lateinit var engine: Engine
private lateinit var myRenderer: MyRenderer

class MyRenderer :  GLSurfaceView.Renderer {
    override fun onSurfaceCreated(unused: GL10?, eglConfig: EGLConfig?) {
        engine = Engine(deviceGles20 = AndroidGles20())
        myRenderer = MyRenderer(engine)
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        gles20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(unused: GL10?) {
        myRenderer.draw()
    }
}