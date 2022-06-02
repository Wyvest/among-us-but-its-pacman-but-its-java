package net.wyvest.sus.components

import cc.polyfrost.oneconfig.lwjgl.font.Fonts
import me.kbrewster.eventbus.Subscribe
import net.wyvest.sus.*
import net.wyvest.sus.utils.*
import java.awt.Color
import java.util.*
import kotlin.properties.Delegates

class Button(vg: VG, val text: String, val x: () -> Float, val y: () -> Float, val width: () -> Float, val height: () -> Float, val textSize: Float, val block: () -> Unit): VG(vg.instance) {
    var screen: Optional<Screen> = Optional.empty()
    init {
        eventbus.register(this)
    }

    fun draw() {
        screen = Optional.of(ScreenManager.currentScreen!!)
        val currentX = x()
        val currentY = y()
        val currentWidth = width()
        val currentHeight = height()
        val hovering = isHovering(cursorX, cursorY, currentX, currentY, currentWidth, currentHeight)
        drawRect(currentX, currentY, currentWidth, currentHeight, (if (hovering) Color.WHITE.darker() else Color.WHITE).rgb)
        drawRect(currentX + 8, currentY + 8, currentWidth - 16, currentHeight - 16, Color.GRAY.darker().let { if (hovering) it.darker() else it }.rgb)
        drawText(text, (currentX + currentWidth / 2) - getTextWidth(text, textSize, Fonts.REGULAR.font) / 2, currentY + currentHeight / 2, (if (hovering) Color.WHITE.darker() else Color.WHITE).rgb, textSize, Fonts.REGULAR.font)
    }

    @Subscribe
    private fun onInput(event: InputEvent) {
        if (screen.isPresent && screen.get() != ScreenManager.currentScreen) {
            eventbus.unregister(this)
            return
        }
        if (event.input.inputType == Input.InputType.MOUSE) {
            if (event.input.pressType == Input.PressType.PRESS) {
                val currentX = x()
                val currentY = y()
                val currentWidth = width()
                val currentHeight = height()
                if (isHovering(cursorX, cursorY, currentX, currentY, currentWidth, currentHeight)) {
                    block()
                }
            }
        }
    }

    private fun isHovering(cursorX: Double, cursorY: Double, x: Float, y: Float, width: Float, height: Float): Boolean {
        return cursorX >= x && cursorX <= x + width && cursorY >= y && cursorY <= y + height
    }
}