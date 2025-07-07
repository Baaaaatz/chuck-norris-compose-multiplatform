package org.batz.thechucknorris.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = koinViewModel(),
) {

    val categories by viewModel.categories.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") }
            )
        }
    ) { paddingContent ->
        Column(modifier = modifier.fillMaxSize()) {
            Spacer(modifier = modifier.height(paddingContent.calculateTopPadding()))
            FlowRow(
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier.fillMaxWidth()
            ) {
                categories.map {
                    Box(
                        modifier = modifier
                            .weight(1f)
                            .background(color = Color.Yellow)
                    ) {
                        Box(modifier = modifier.fillMaxWidth()) {
                            Text(it)
                        }
                    }
                }
            }
        }
    }
}
