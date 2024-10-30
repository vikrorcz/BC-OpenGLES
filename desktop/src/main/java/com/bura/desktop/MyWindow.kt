package com.bura.desktop

import com.bura.common.engine.Engine
import com.bura.common.engine.Engine.Companion.gles20
import com.bura.common.engine.MyRenderer
import com.bura.common.util.Constants
import com.bura.common.util.Matrix4f
import com.bura.desktop.util.Input
import com.bura.desktop.util.KeyBindings
import com.bura.desktop.util.LwjglGles20
import com.bura.desktop.util.LwjglTextureUtil
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengles.GLES
import org.lwjgl.system.Configuration
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.nio.DoubleBuffer
import kotlin.math.tan

internal class MyWindow {

    private var window: Long = 0
    private var screenWidthPixel = 0
    private var screenHeightPixel = 0
    private val keyBindings: KeyBindings = KeyBindings()
    private lateinit var engine: Engine
    private lateinit var myRenderer: MyRenderer

    private fun init() {
        initializeGlfwWindow()
        initializeEngine()
        initializeKeyCallbacks()
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
            textureUtil = LwjglTextureUtil(),
        )

        gles20.glViewport(0, 0, screenWidthPixel, screenHeightPixel)

        val ratio = screenWidthPixel.toFloat() / screenHeightPixel
        engine.screenWidthPixel = screenWidthPixel
        engine.screenHeightPixel = screenHeightPixel

        myRenderer = MyRenderer(engine)

        val fov = engine.camera.fov // set the desired FOV angle in degrees
        var top: Float = tan(Math.toRadians((fov / 2.0f).toDouble())).toFloat() * 1f
        var bottom = -top
        var right = top * ratio
        var left = -right

        Matrix4f.frustumM(engine.projectionMatrix, left, right, bottom, top, Constants.CAMERA_NEAR, Constants.CAMERA_FAR)

        GLFW.glfwSetFramebufferSizeCallback(window) { _: Long, width: Int, height: Int ->
            gles20.glViewport(0, 0, width, height)

            val changedRatio: Float = width.toFloat() / height

            top = tan(Math.toRadians((fov / 2.0f).toDouble())).toFloat() * 1f
            bottom = -top
            right = top * changedRatio
            left = -right

            engine.screenWidthPixel = width
            engine.screenHeightPixel = height
            Matrix4f.frustumM(engine.projectionMatrix, left, right, bottom, top, Constants.CAMERA_NEAR, Constants.CAMERA_FAR)
        }
    }

    private fun initializeKeyCallbacks() {
        val input = Input()
        GLFW.glfwSetKeyCallback(window, input)
    }

    private fun loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents()
            inputUpdate()
            myRenderer.draw()
            GLFW.glfwSwapBuffers(window)
        }
    }

    private var cursorX: Double = 0.0
    private var cursorY: Double = 0.0
    private val xBuffer: DoubleBuffer = BufferUtils.createDoubleBuffer(1)
    private val yBuffer: DoubleBuffer = BufferUtils.createDoubleBuffer(1)
    private var prevX: Double = 0.0
    private var prevY: Double = 0.0

    private fun inputUpdate() {
        GLFW.glfwGetCursorPos(window, xBuffer, yBuffer)
        cursorX = xBuffer.get(0)
        cursorY = yBuffer.get(0)

        val deltaX: Double = cursorX - prevX
        val deltaY: Double = cursorY - prevY

        engine.camera.yaw -= (deltaX * 0.001).toFloat()
        engine.camera.pitch -= (deltaY * 0.001f).toFloat()
        engine.camera.pitch = engine.camera.pitch.coerceIn(186.93f, 190f)

        prevX = cursorX
        prevY = cursorY

        keyBindings.update(engine, window)
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