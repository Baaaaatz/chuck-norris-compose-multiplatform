package org.batz.thechucknorris.presentation.joke

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.presentation.composables.CommonLoader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import thechucknorrisapp.composeapp.generated.resources.Res
import thechucknorrisapp.composeapp.generated.resources.title_joke

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(
    category: String,
    modifier: Modifier = Modifier,
    viewModel: JokeViewModel = koinViewModel(parameters = {
        parametersOf(category)
    }),
) {
    val joke by viewModel.joke.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.title_joke, category)) }
            )
        }
    ) { paddingContent ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = modifier.height(paddingContent.calculateTopPadding() + 12.dp))
            if (joke != null) {
                Box(modifier = modifier.padding(24.dp)) {
                    Text(joke ?: "I have a joke about nothing but there’s nothing to laugh at.")
                }
            }
            Spacer(modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars))
        }
        CommonLoader(shouldShow = uiState == UiState.Loading)
    }
}
