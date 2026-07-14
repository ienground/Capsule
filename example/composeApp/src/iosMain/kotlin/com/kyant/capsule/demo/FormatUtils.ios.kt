package com.kyant.capsule.demo

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun Double.format(decimals: Int): String {
    return NSString.stringWithFormat("%.${decimals}f", this)
}

actual fun Float.format(decimals: Int): String {
    return NSString.stringWithFormat("%.${decimals}f", this)
}
