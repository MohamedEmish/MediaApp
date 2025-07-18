package com.amosh.pulse.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_2_LINES_GRID
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_BIG_SQUARE
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_QUEUE
import com.amosh.pulse.core.domain.constants.Constants.VIEW_TYPE_SQUARE
import com.amosh.pulse.model.enums.ContentType
import com.amosh.pulse.ui.screens.homeScreen.subViews.CategoryTabs
import com.amosh.pulse.ui.screens.homeScreen.subViews.GridView
import com.amosh.pulse.ui.screens.homeScreen.subViews.QueueView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerCategoryTabs
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerGridView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerQueueView
import com.amosh.pulse.ui.screens.homeScreen.subViews.ShimmerSquareView
import com.amosh.pulse.ui.screens.homeScreen.subViews.SquareView
import com.amosh.pulse.ui.screens.homeScreen.subViews.TopGreetingBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val scrollState = rememberScrollState()
    val userDataState = viewModel.userDataState.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopGreetingBar(userName = userDataState.value.userName)
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
                ) {
                    // TODO :: change results in below sections
                }

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
                                )
                            }

                            VIEW_TYPE_QUEUE -> {
                                QueueView(
                                    section = it,
                                    selectedType = it.contentType ?: ContentType.PODCAST,
                                )
                            }


                            VIEW_TYPE_2_LINES_GRID -> {
                                GridView(
                                    section = it,
                                    selectedType = it.contentType ?: ContentType.PODCAST,
                                    lines = 2
                                )
                            }

                            VIEW_TYPE_BIG_SQUARE -> {
                                SquareView(
                                    section = it,
                                    selectedType = it.contentType ?: ContentType.PODCAST,
                                    isBig = true
                                )
                            }

                        }
                    }
                }
            }

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
//            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_no_due_zakat))
//            val progress by animateLottieCompositionAsState(
//                composition = composition,
//                iterations = LottieConstants.IterateForever
//            )
//            LottieAnimation(
//                composition = composition,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.4f),
//                progress = { progress }
//            )
//
//            SimpleLabTextViewCompose(
//                text = stringResource(id = R.string.no_due_zakat_recorded),
//                modifier = Modifier
//                    .padding(
//                        MaterialTheme.spacing.medium16
//                    )
//                    .wrapContentWidth(),
//                textStyle = SimpleLabTitleLarge,
//                textColor = MaterialTheme.colorScheme.primary,
//            )
//
//            SimpleLabTextViewCompose(
//                text = stringResource(id = R.string.no_due_zakat_recorded_sub),
//                modifier = Modifier
//                    .padding(horizontal = MaterialTheme.spacing.large24)
//                    .wrapContentWidth(),
//                textStyle = SimpleLabLabelMedium,
//                textColor = MaterialTheme.colorScheme.secondary,
//            )
        }
    }
}
