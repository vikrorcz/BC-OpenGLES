package com.bura.desktop

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.engine.MyRenderer
import com.bura.desktop.util.LwjglGles20
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengles.GLES
import org.lwjgl.system.Configuration
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

internal class MyWindow {

    private var window: Long = 0
    private var screenWidthPixel = 0
    private var screenHeightPixel = 0
    private lateinit var engine: Engine
    private lateinit var myRenderer: MyRenderer

    private fun init() {
        initializeGlfwWindow()
        initializeEngine()
    }

    private fun initializeGlfwWindow() {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW window")
        }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

        // Use OpenGLES 2.0
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 2) // For OpenGLES 3.0 use 3
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0)
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_OPENGL_ES_API)

        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()) ?: return

        // Fullscreen
        //screenWidthPixel = vidmode.width()
        //screenHeightPixel = vidmode.height()
        //window = GLFW.glfwCreateWindow(vidmode.width(), vidmode.height(), "OpenGLES", GLFW.glfwGetPrimaryMonitor(), MemoryUtil.NULL)

        // Optionally use windowed mode
        screenWidthPixel = 1280
        screenHeightPixel = 720
        window = GLFW.glfwCreateWindow(screenWidthPixel, screenHeightPixel, "OpenGLES", MemoryUtil.NULL, MemoryUtil.NULL)
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            // Center the window
            GLFW.glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        // Optionally Hide the cursor
        //GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)


        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(1) // Enable V-Sync, limiting the render loop frequency
        GLFW.glfwShowWindow(window)

        Configuration.OPENGLES_EXPLICIT_INIT.set(true)
        GL.getFunctionProvider()?.let { GLES.create(it) }
        GLES.createCapabilities()
    }

    private fun initializeEngine() {
        engine = Engine(
            deviceGles20 = LwjglGles20(),
        )

        gles20.glViewport(0, 0, screenWidthPixel, screenHeightPixel)

        myRenderer = MyRenderer(engine)

        GLFW.glfwSetFramebufferSizeCallback(window) { _: Long, width: Int, height: Int ->
            gles20.glViewport(0, 0, width, height)
        }
    }

    private fun  loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents()
            myRenderer.draw()
            GLFW.glfwSwapBuffers(window)
        }
    }

    fun setup() {
        init()
        loop()

        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)

        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)?.free()
    }
}