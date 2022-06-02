package net.wyvest.sus

import me.kbrewster.eventbus.eventbus
import me.kbrewster.eventbus.invokers.LMFInvoker
import net.wyvest.sus.utils.Input

val eventbus = eventbus {
    invokerType = LMFInvoker()
}

data class InputEvent(val input: Input)