package com.amosh.pulse.ui.screens.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amosh.pulse.R
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.theme.SimpleLabTitleLarge
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.model.SectionsUiItem
import com.amosh.pulse.model.enums.ContentType
import com.amosh.pulse.ui.ext.filterNotNullItems
import com.amosh.pulse.ui.screens.NoInternetScreen
import com.amosh.pulse.ui.screens.homeScreen.subViews.GridView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerCategoryTabs
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerGridView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerQueueView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerSquareView
import com.amosh.pulse.utils.showToastMessage
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SearchContract.Effect.ShowError -> {
                    showToastMessage(
                        context,
                        effect.messageRes?.let {
                            context.getString(it)
                        } ?: context.getString(
                            R.string.something_went_wrong
                        )
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchInputField(
            modifier = Modifier.padding(MaterialTheme.spacing.medium16),
            query = query,
            placeholder = stringResource(R.string.search),
            onQueryChange = { viewModel.setEvent(SearchContract.Event.OnSearch(it)) }
        )

        when (val result = uiState.value.state) {
            is SearchContract.SearchState.Loading -> {
                Column {
                    ShimmerCategoryTabs()
                    ShimmerSquareView()
                    ShimmerQueueView()
                    ShimmerGridView()
                }
            }

            is SearchContract.SearchState.Success -> {
                Column(
                    modifier = Modifier
                        .padding()
                        .verticalScroll(scrollState, true),
                ) {
                    GridView(
                        section = SectionsUiItem(content = result.content.filterNotNullItems()),
                        selectedType = ContentType.PODCAST,
                        lines = result.content.size
                    ) {

                    }
                }
            }

            is SearchContract.SearchState.Idle -> Unit

            is SearchContract.SearchState.NoInternet -> NoInternetScreen()

            else -> if (query.isNotEmpty())
                SearchScreenEmptyState()
        }
    }
}

@Composable
private fun SearchScreenEmptyState(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_result_found))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                progress = { progress }
            )

            SimpleLabTextViewCompose(
                text = stringResource(
                    id = com.amosh.pulse.core.ui.R.string.no_search_result
                ),
                modifier = Modifier
                    .padding(
                        MaterialTheme.spacing.medium16
                    )
                    .wrapContentWidth(),
                textStyle = SimpleLabTitleLarge,
                textColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun SearchInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.secondary,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    iconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        placeholder = {
            Text(placeholder, color = iconColor)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = iconColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = focusedBorderColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}