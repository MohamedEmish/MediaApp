package com.amosh.pulse.media.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_2_LINES_GRID
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_BIG_SQUARE
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_QUEUE
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_SQUARE
import com.amosh.pulse.core.ui.R
import com.amosh.pulse.media.ui.model.enums.ContentType
import com.amosh.pulse.core.ui.components.EmptyViewScreen
import com.amosh.pulse.core.ui.extension.showToastMessage
import com.amosh.pulse.media.ui.components.CategoryTabs
import com.amosh.pulse.media.ui.components.GridView
import com.amosh.pulse.media.ui.components.QueueView
import com.amosh.pulse.media.ui.components.ShimmerCategoryTabs
import com.amosh.pulse.media.ui.components.ShimmerGridView
import com.amosh.pulse.media.ui.components.ShimmerQueueView
import com.amosh.pulse.media.ui.components.ShimmerSquareView
import com.amosh.pulse.media.ui.components.SquareView
import com.amosh.pulse.media.ui.components.TopGreetingBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val scrollState = rememberScrollState()
    val userDataState = viewModel.userDataState.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HomeContract.Effect.ShowError -> {
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
        TopGreetingBar(
            userName = userDataState.value.userName,
            userImage = userDataState.value.profilePic
        )
        when (val result = uiState.value.state) {
            is HomeContract.HomeState.Loading -> {
                Column {
                    ShimmerCategoryTabs()
                    ShimmerSquareView()
                    ShimmerQueueView()
                    ShimmerGridView()
                }
            }

            is HomeContract.HomeState.Success -> {
                CategoryTabs(
                    categories = result.sections.mapNotNull { it.name }
                ) { }

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState, true),
                ) {
                    result.sections.forEach {
                        when (it.type) {
                            VIEW_TYPE_SQUARE -> {
                                SquareView(
                                    section = it,
                                    selectedType = it.contentType ?: ContentType.PODCAST,
                                    isBig = false
                                ) {
                                    viewModel.setEvent(HomeContract.Event.OnFetchHomeSections(false))
                                }
                            }

                            VIEW_TYPE_QUEUE -> {
                                QueueView(
                                    section = it,
                                    selectedType = it.contentType ?: ContentType.PODCAST,
                                ) {
                                    viewModel.setEvent(HomeContract.Event.OnFetchHomeSections(false))
                                }
                            }


                            VIEW_TYPE_2_LINES_GRID -> {
                                GridView(
                                    section = it,
                                    selectedType = it.contentType ?: ContentType.PODCAST,
                                    lines = 2
                                ) {
                                    viewModel.setEvent(HomeContract.Event.OnFetchHomeSections(false))
                                }
                            }

                            VIEW_TYPE_BIG_SQUARE -> {
                                SquareView(
                                    section = it,
                                    selectedType = it.contentType ?: ContentType.PODCAST,
                                    isBig = true
                                ) {
                                    viewModel.setEvent(HomeContract.Event.OnFetchHomeSections(false))
                                }
                            }

                        }
                    }
                }
            }

            is HomeContract.HomeState.NoInternet -> EmptyViewScreen(
                emptyMessage = stringResource(R.string.no_internet),
                animationRes = R.raw.no_internet
            )

            else -> EmptyViewScreen(
                emptyMessage = stringResource(R.string.no_data_to_show),
                animationRes = com.amosh.pulse.media.ui.R.raw.no_result_found
            )
        }
    }
}