package net.wyvest.sus

import net.wyvest.sus.utils.VG

abstract class Screen(instance: Long) : VG(instance) {
    abstract fun draw(width: Int, height: Int)
}