package net.wyvest.sus

import cc.polyfrost.oneconfig.lwjgl.font.FontManager
import net.wyvest.sus.InGameUnderlay.drawUnderlay
import net.wyvest.sus.utils.Input
import net.wyvest.sus.utils.nanoVG
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.nanovg.NanoVG.nvgBeginFrame
import org.lwjgl.nanovg.NanoVG.nvgEndFrame
import org.lwjgl.nanovg.NanoVGGL3.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.Platform
import kotlin.math.max
import kotlin.system.exitProcess


var cursorX = 0.0
set(value) {
    field = value / contentScaleX
}
var cursorY = 0.0
    set(value) {
        field = value / contentScaleY
    }

var framebufferWidth = 0
var framebufferHeight = 0

var contentScaleX = 0f
var contentScaleY = 0f

val width
get() = (framebufferWidth / contentScaleX).toInt()

val height
get() = (framebufferHeight / contentScaleY).toInt()

var vg: Long = -1
private set
var window = 0L
private set
private var fullscreen = false

fun main() {
    GLFWErrorCallback.createPrint().set()
    if (!glfwInit()) {
        throw RuntimeException("Failed to init GLFW.")
    }

    if (Platform.get() == Platform.MACOSX) {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    }
    glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE)

    if (false) {
        glfwWindowHint(GLFW_SAMPLES, 8)
    }

    window = glfwCreateWindow(1000, 600, "Among Man", 0, 0)
    if (window == 0L) {
        glfwTerminate()
        throw RuntimeException()
    }
    fun setFullscreen() {
        val monitor = glfwGetPrimaryMonitor()
        val videoMode = glfwGetVideoMode(monitor) ?: throw RuntimeException()
        glfwSetWindowMonitor(window, if (fullscreen) 0 else monitor, if (fullscreen) videoMode.width() / 2 - 500 else 0, if (fullscreen) videoMode.height() / 2 - 300 else 0, if (fullscreen) 1000 else videoMode.width(), if (fullscreen) 600 else videoMode.height(), videoMode.refreshRate())
        fullscreen = !fullscreen
    }
    setFullscreen()
    glfwSetKeyCallback(
        window
    ) { windowHandle: Long, keyCode: Int, _: Int, action: Int, mods: Int ->
        if (action == GLFW_PRESS && keyCode == GLFW_KEY_F11) {
            setFullscreen()
        }
        eventbus.post(InputEvent(Input(
            Input.InputType.KEYBOARD,
            when (action) {
                GLFW_PRESS -> Input.PressType.PRESS
                GLFW_REPEAT -> Input.PressType.HOLD
                GLFW_RELEASE -> Input.PressType.RELEASE
                else -> throw RuntimeException()
            },
            keyCode
        )))
    }

    glfwSetMouseButtonCallback(window) { _: Long, button: Int, action: Int, mods: Int ->
        eventbus.post(InputEvent(Input(
            Input.InputType.MOUSE,
            when (action) {
                GLFW_PRESS -> Input.PressType.PRESS
                GLFW_REPEAT -> Input.PressType.HOLD
                GLFW_RELEASE -> Input.PressType.RELEASE
                else -> throw RuntimeException()
            },
            button
        )))
    }

    glfwSetCursorPosCallback(window) { handle: Long, xpos: Double, ypos: Double ->
        cursorX = xpos
        cursorY = ypos
    }

    glfwSetFramebufferSizeCallback(window) { handle: Long, w: Int, h: Int ->
        framebufferWidth = w
        framebufferHeight = h
    }
    glfwSetWindowContentScaleCallback(window) { handle: Long, xscale: Float, yscale: Float ->
        contentScaleX = xscale
        contentScaleY = yscale
    }

    stackPush().use { stack ->
        val fw = stack.mallocInt(1)
        val fh = stack.mallocInt(1)
        val sx = stack.mallocFloat(1)
        val sy = stack.mallocFloat(1)
        glfwGetFramebufferSize(window, fw, fh)
        framebufferWidth = fw[0]
        framebufferHeight = fh[0]
        glfwGetWindowContentScale(window, sx, sy)
        contentScaleX = sx[0]
        contentScaleY = sy[0]
    }

    glfwMakeContextCurrent(window)
    GL.createCapabilities()
    glfwSwapInterval(0)

    vg = nvgCreate(NVG_ANTIALIAS)
    if (vg == -1L) {
        throw RuntimeException("Could not init nanovg.")
    }

    glfwSetTime(0.0)

    FontManager.INSTANCE.initialize(vg)
    eventbus.register(EscapeMenuManager)

    while (!glfwWindowShouldClose(window)) {
        // Effective dimensions on hi-dpi devices.
        // Update and render
        glViewport(0, 0, framebufferWidth, framebufferHeight)
        glClearColor(0f, 0f, 0f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT or GL_STENCIL_BUFFER_BIT)
        nvgBeginFrame(vg, width.toFloat(), height.toFloat(), max(contentScaleX, contentScaleY))
        nanoVG(vg) {
            drawUnderlay()
            ScreenManager.render(this)
        }
        nvgEndFrame(vg)

        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    nvgDelete(vg)

    GL.setCapabilities(null)

    glfwFreeCallbacks(window)
    glfwTerminate()
    glfwSetErrorCallback(null)?.free()
    exitProcess(0)
}