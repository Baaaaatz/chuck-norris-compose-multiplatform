package org.batz.thechucknorris.presentation.categories

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.batz.thechucknorris.util.snackbar.*
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") }
            )
        }
    ) { paddingContent ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = modifier.height(paddingContent.calculateTopPadding() + 12.dp))
            FlowRow(
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier.fillMaxWidth()
            ) {
                categories.forEach {
                    Box(
                        modifier = modifier
                            .padding(16.dp)
                            .weight(1F)
                    ) {
                        CategoryWidget(
                            category = it,
                            onClick = {
                                coroutineScope.launch {
                                    SnackbarController.sendEvent(
                                        SnackbarEvent(
                                            "$it is clicked, should navigate to next screen.",
                                            snackbarType = SnackbarType.INFO,
                                        )
                                    )
                                }
                            },
                        )
                    }
                }
            }
            Spacer(modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars))
        }
    }
}


@Composable
private fun CategoryWidget(
    modifier: Modifier = Modifier,
    category: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2.5f)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.size(32.dp)
            )

            Spacer(modifier = modifier.width(12.dp))

            Text(
                text = category,
                color = Color.White,
            )
        }
    }
}
