package com.amosh.pulse.media.ui.screens.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amosh.pulse.core.ui.R
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.media.ui.model.SectionsUiItem
import com.amosh.pulse.media.ui.model.enums.ContentType
import com.amosh.pulse.media.ui.mapper.filterNotNullItems
import com.amosh.pulse.core.ui.components.EmptyViewScreen
import com.amosh.pulse.core.ui.extension.showToastMessage
import com.amosh.pulse.media.ui.components.GridView
import com.amosh.pulse.media.ui.components.ShimmerCategoryTabs
import com.amosh.pulse.media.ui.components.ShimmerGridView
import com.amosh.pulse.media.ui.components.ShimmerQueueView
import com.amosh.pulse.media.ui.components.ShimmerSquareView
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

            is SearchContract.SearchState.NoInternet -> EmptyViewScreen(
                emptyMessage = stringResource(R.string.no_internet),
                animationRes = R.raw.no_internet
            )

            else -> if (query.isNotEmpty()) {
                EmptyViewScreen(
                    emptyMessage = stringResource(R.string.no_search_result),
                    animationRes = com.amosh.pulse.media.ui.R.raw.no_result_found
                )
            }
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
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    iconColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
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