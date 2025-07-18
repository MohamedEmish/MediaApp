package com.amosh.pulse.ui.screens.homeScreen

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amosh.pulse.R
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_2_LINES_GRID
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_BIG_SQUARE
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_QUEUE
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_SQUARE
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.theme.SimpleLabTitleLarge
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.model.enums.ContentType
import com.amosh.pulse.ui.screens.NoInternetScreen
import com.amosh.pulse.ui.screens.homeScreen.subViews.CategoryTabs
import com.amosh.pulse.ui.screens.homeScreen.subViews.GridView
import com.amosh.pulse.ui.screens.homeScreen.subViews.QueueView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerCategoryTabs
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerGridView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerQueueView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerSquareView
import com.amosh.pulse.ui.screens.homeScreen.subViews.SquareView
import com.amosh.pulse.ui.screens.homeScreen.subViews.TopGreetingBar
import com.amosh.pulse.utils.showToastMessage
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

            is HomeContract.HomeState.NoInternet -> NoInternetScreen()

            else -> HomeScreenEmptyState()
        }
    }
}


@Composable
private fun HomeScreenEmptyState(
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
                    id = R.string.no_data_to_show
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