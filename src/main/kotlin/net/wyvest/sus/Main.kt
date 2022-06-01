package net.wyvest.sus

import net.wyvest.sus.utils.drawRect
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
import java.awt.Color
import kotlin.math.max


var blowup = false
var screenshot = false

var cursorX = 0.0
var cursorY = 0.0

var framebufferWidth = 0
var framebufferHeight = 0

var contentScaleX = 0f
var contentScaleY = 0f

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

    val window = glfwCreateWindow(1000, 600, "NanoVG (OpenGL 3)", 0, 0)
    if (window == 0L) {
        glfwTerminate()
        throw RuntimeException()
    }

    glfwSetKeyCallback(
        window
    ) { windowHandle: Long, keyCode: Int, scancode: Int, action: Int, mods: Int ->
        if (keyCode == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
            glfwSetWindowShouldClose(windowHandle, true)
        }
        if (keyCode == GLFW_KEY_SPACE && action == GLFW_PRESS) {
            blowup = !blowup
        }
        if (keyCode == GLFW_KEY_S && action == GLFW_PRESS) {
            screenshot = true
        }
    }

    glfwSetCursorPosCallback(window) { handle: Long, xpos: Double, ypos: Double ->
        cursorX = xpos.toInt().toDouble()
        cursorY = ypos.toInt().toDouble()
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

    val vg = nvgCreate(NVG_ANTIALIAS)
    if (vg == -1L) {
        throw RuntimeException("Could not init nanovg.")
    }

    glfwSetTime(0.0)

    while (!glfwWindowShouldClose(window)) {
        // Effective dimensions on hi-dpi devices.
        val width = (framebufferWidth / contentScaleX).toInt()
        val height = (framebufferHeight / contentScaleY).toInt()

        // Update and render
        glViewport(0, 0, framebufferWidth, framebufferHeight)
        glClearColor(1f, 1f, 1f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT or GL_STENCIL_BUFFER_BIT)
        nvgBeginFrame(vg, width.toFloat(), height.toFloat(), max(contentScaleX, contentScaleY))
        nanoVG(vg) {
            ScreenManager.render(this, width, height)
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
}