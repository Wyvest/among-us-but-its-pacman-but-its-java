package net.wyvest.sus

import net.wyvest.sus.utils.VG

object ScreenManager {
    var currentScreen: Screen? = null
    fun render(vg: VG) {
        val screen = currentScreen ?: run { currentScreen = TitleScreen(vg.instance); currentScreen!! }
        screen.draw()
    }
}