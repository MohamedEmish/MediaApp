package com.amosh.pulse.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.model.enums.ContentType

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
            .padding(bottom = MaterialTheme.spacing.special72)
    ) {
        TopGreetingBar(userName = userDataState.value.userName)
        when (val result = uiState.value.state) {
            is HomeContract.HomeState.Loading -> {
                Column {
                    ShimmerCategoryTabs()
                    ShimmerPeekingCardsCarousel()
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
                        .padding()
                        .verticalScroll(scrollState, true),
                ) {
                    TopItemsCarousel(
                        sections = result.sections,
                        selectedType = ContentType.PODCAST,
                    ) {

                    }
//            NowPlayingBanner()
//            Section(title = "اسمع قبل الناس", items = sampleItems)
//            Section(title = "الحلقات الجديدة", items = sampleItems)
//            Section(title = "توصيات الفريق", items = sampleItems)
//            Section(title = "من الصالة", items = sampleItems)
//            Section(title = "حلقات خاصة", items = sampleItems)
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
