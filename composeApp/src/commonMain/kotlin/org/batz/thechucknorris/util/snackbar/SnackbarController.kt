package org.batz.thechucknorris.util.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


enum class SnackbarType {
    SUCCESS, ERROR, INFO
}

data class SnackbarEvent(
    val message: String,
    val snackbarType: SnackbarType,
)

object SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}
