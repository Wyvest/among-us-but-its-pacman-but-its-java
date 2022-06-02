package net.wyvest.sus

import cc.polyfrost.oneconfig.lwjgl.image.Images
import me.kbrewster.eventbus.Subscribe
import net.wyvest.sus.utils.*
import org.lwjgl.glfw.GLFW
import org.lwjgl.nanovg.NanoVG
import java.awt.Color

object InGameUnderlay {
    var x = 485
    var y = 560
    fun VG.drawUnderlay() {
        if (ScreenManager.currentScreen is InGameFakeScreen || ScreenManager.currentScreen is EscapeMenu) {
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W).let { it == GLFW.GLFW_PRESS || it == GLFW.GLFW_REPEAT }) {
                y -= 1
            }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S).let { it == GLFW.GLFW_PRESS || it == GLFW.GLFW_REPEAT }) {
                y += 1
            }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A).let { it == GLFW.GLFW_PRESS || it == GLFW.GLFW_REPEAT }) {
                x -= 1
            }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D).let { it == GLFW.GLFW_PRESS || it == GLFW.GLFW_REPEAT }) {
                x += 1
            }
            scale(4f, 4f)
            translate(-100F, -70F)
            drawImage(Images.HELTER_SKELTER, 0, 0, width, height)
            NanoVG.nvgReset(instance)
            drawImage(Images.PACMAN_OPEN, x, y, 64, 64, Color.RED.rgb)
        }
    }

    fun setup() {
        ScreenManager.currentScreen = InGameFakeScreen(vg)
    }

    internal class InGameFakeScreen(instance: Long) : Screen(instance) {
        override fun draw() {
            // no-op
        }
    }
}