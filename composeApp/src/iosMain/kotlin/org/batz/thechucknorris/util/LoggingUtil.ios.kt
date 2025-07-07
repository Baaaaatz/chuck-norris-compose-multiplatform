package org.batz.thechucknorris.util

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun initLogger(shouldEnableLogger: Boolean) {
    if (shouldEnableLogger) {
        Napier.base(DebugAntilog())
    }
}