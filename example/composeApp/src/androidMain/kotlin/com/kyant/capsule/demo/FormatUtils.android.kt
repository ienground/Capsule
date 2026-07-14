package com.kyant.capsule.demo

actual fun Double.format(decimals: Int): String {
    return String.format(java.util.Locale.US, "%.${decimals}f", this)
}

actual fun Float.format(decimals: Int): String {
    return String.format(java.util.Locale.US, "%.${decimals}f", this)
}
