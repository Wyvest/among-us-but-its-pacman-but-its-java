package net.wyvest.sus

import me.kbrewster.eventbus.Subscribe
import net.wyvest.sus.components.Button
import net.wyvest.sus.utils.Input
import net.wyvest.sus.utils.drawRect
import org.lwjgl.glfw.GLFW
import java.awt.Color

object EscapeMenuManager {
    @Subscribe
    private fun onInput(event: InputEvent) {
        if (event.input.inputType == Input.InputType.KEYBOARD) {
            if (event.input.pressType == Input.PressType.PRESS) {
                if (event.input.key == GLFW.GLFW_KEY_ESCAPE) {
                    if (ScreenManager.currentScreen is InGameUnderlay.InGameFakeScreen) {
                        ScreenManager.currentScreen = EscapeMenu(vg)
                    } else if (ScreenManager.currentScreen is EscapeMenu) {
                        ScreenManager.currentScreen = InGameUnderlay.InGameFakeScreen(vg)
                    }
                }
            }
        }
    }
}
class EscapeMenu(instance: Long) : Screen(instance) {
    val play = Button(this, "Back to Main Menu", { width / 2 - 100F }, { height / 2 - 30F }, { 200F }, { 60F }, 20F) {
        ScreenManager.currentScreen = null
    }

    override fun draw() {
        drawRect(0, 0, width, height, Color(0, 0, 0, 128).rgb)
        play.draw()
    }
}