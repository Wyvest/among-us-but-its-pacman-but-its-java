package net.wyvest.sus.utils.particles

import net.wyvest.sus.height
import net.wyvest.sus.utils.VG
import net.wyvest.sus.width


/**
 * Particle API This Api is free2use But u have to mention me.
 *
 * @author Vitox
 * @version 3.0
 */
object SnowManager {
    private data class Snow(var x: Float, var y: Float)
    private var yOffset = 0
    private var snow = ArrayList<Snow>()
    private var prevWidth = 0
    private var prevHeight = 0

    fun draw(vg: VG) {
        if (prevWidth != width || prevHeight != height) {
            prevHeight = height
            prevWidth = width
            repeat(100) {
                snow.add(Snow((0..width).random().toFloat(), (0..height).random().toFloat()))
            }
        }

    }
}