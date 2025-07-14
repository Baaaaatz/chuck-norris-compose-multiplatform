package org.batz.thechucknorris.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CommonLoader(
    shouldShow: Boolean,
    modifier: Modifier = Modifier,
) {
    if (shouldShow) {
        Box(modifier = modifier.fillMaxSize()) {
            Box(modifier = modifier.align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
    }
}
