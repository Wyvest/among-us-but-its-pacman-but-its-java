package net.wyvest.sus

import cc.polyfrost.oneconfig.lwjgl.font.Fonts
import cc.polyfrost.oneconfig.lwjgl.image.Images
import net.wyvest.sus.components.Button
import net.wyvest.sus.utils.*
import java.awt.Color
import kotlin.system.exitProcess

class TitleScreen(instance: Long) : Screen(instance) {
    val play = Button(this, "Play", { width / 2 - 100F }, { height / 2 - 30F }, { 200F }, { 60F }, 20F) {
        InGameUnderlay.setup()
    }
    val settings = Button(this, "Settings", { width / 2 - 100F }, { height / 2 + 40F }, { 200F }, { 60F }, 20F) {
        println("SETTINGS")
    }
    val quit = Button(this, "Quit Game", { width / 2 - 100F }, { height / 2 + 110F }, { 200F }, { 60F }, 20F) {
        exitProcess(0)
    }

    override fun draw() {
        drawRect(0, 0, width, height, Color.BLACK.rgb)
        drawImage(Images.PACMAN_OPEN, width / 2 - 100, height / 5 - 100, 200, 200)
        drawText("Among Man", width / 2 - getTextWidth("Among Man", 30, Fonts.BOLD.font) / 2, (height / 5) + 100 + 20, Color.WHITE.rgb, 30, Fonts.BOLD.font)
        play.draw()
        settings.draw()
        quit.draw()
    }
}