package net.wyvest.sus

import cc.polyfrost.oneconfig.lwjgl.font.Fonts
import net.wyvest.sus.utils.drawCircle
import net.wyvest.sus.utils.drawRect
import net.wyvest.sus.utils.drawText
import net.wyvest.sus.utils.getTextWidth
import net.wyvest.sus.utils.particles.SnowManager
import java.awt.Color

class TitleScreen(instance: Long) : Screen(instance) {
    override fun draw() {
        drawRect(0, 0, width, height, Color.BLACK.rgb)
        SnowManager.draw(this)
        drawCircle(width / 2, height / 5, 100, Color.WHITE.rgb)
        drawText("Among Man", width / 2 - getTextWidth("Among Man", 30, Fonts.BOLD.font) / 2, (height / 5) + 100 + 20, Color.WHITE.rgb, 30, Fonts.BOLD.font)

    }
}