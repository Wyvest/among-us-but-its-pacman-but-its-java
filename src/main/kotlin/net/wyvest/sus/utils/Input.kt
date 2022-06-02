package net.wyvest.sus.utils

data class Input(val inputType: InputType, val pressType: PressType, val key: Int) {
    enum class InputType {
        KEYBOARD,
        MOUSE;
    }
    enum class PressType {
        PRESS,
        HOLD,
        RELEASE;
    }
}
