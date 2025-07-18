package com.amosh.pulse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amosh.pulse.R
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.theme.SimpleLabTitleLarge
import com.amosh.pulse.core.ui.theme.spacing

@Composable
fun NoInternetScreen(
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
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
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
                    id = R.string.no_internet
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