package org.batz.thechucknorris.presentation.categories

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.presentation.composables.CommonLoader
import org.batz.thechucknorris.presentation.routes.JokeRoute
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import thechucknorrisapp.composeapp.generated.resources.Res
import thechucknorrisapp.composeapp.generated.resources.title_categories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.title_categories)) }
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
                                navController.navigate(JokeRoute(it))
                            },
                        )
                    }
                }
            }
            Spacer(modifier = modifier.windowInsetsPadding(WindowInsets.navigationBars))
        }
        CommonLoader(shouldShow = uiState == UiState.Loading)
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
