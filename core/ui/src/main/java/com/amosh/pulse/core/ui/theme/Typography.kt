package com.amosh.pulse.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle

// Set of Material typography styles to start with
val fontSizes = FontSizes()

// Heading
val SimpleLabHeadingXLarge = TextStyle(
    fontFamily = IbmPlexSemiBold,
    fontSize = fontSizes.headingXLarge28,
    color = Black,
)

val SimpleLabHeadingLarge = TextStyle(
    fontFamily = IbmPlexSemiBold,
    fontSize = fontSizes.headingLarge24,
    color = Black,
)

val SimpleLabHeadingMedium = TextStyle(
    fontFamily = IbmPlexMedium,
    fontSize = fontSizes.headingMedium20,
    color = Black,
)

val SimpleLabHeadingSmall = TextStyle(
    fontFamily = IbmPlexMedium,
    fontSize = fontSizes.xLarge18,
    color = Black,
)

// Title
val SimpleLabTitleXLarge = TextStyle(
    fontFamily = IbmPlexBold,
    fontSize = fontSizes.headingLarge24,
    color = Black,
)

val SimpleLabTitleLarge = TextStyle(
    fontFamily = IbmPlexSemiBold,
    fontSize = fontSizes.headingMedium20,
    color = Black,
)

val SimpleLabTitleMedium = TextStyle(
    fontFamily = IbmPlexMedium,
    fontSize = fontSizes.xLarge18,
    color = Black,
)

val SimpleLabTitleSmall = TextStyle(
    fontFamily = IbmPlexMedium,
    fontSize = fontSizes.large16,
    color = Black,
)

// Label
val SimpleLabLabelXLarge = TextStyle(
    fontFamily = IbmPlexRegular,
    fontSize = fontSizes.xLarge18,
    color = Black,
)

val SimpleLabLabelLarge = TextStyle(
    fontFamily = IbmPlexMedium,
    fontSize = fontSizes.large16,
    color = Black,
)

val SimpleLabLabelMedium = TextStyle(
    fontFamily = IbmPlexMedium,
    fontSize = fontSizes.normal14,
    color = Black,
)

val SimpleLabLabelSmall = TextStyle(
    fontFamily = IbmPlexMedium,
    fontSize = fontSizes.small12,
    color = Black,
)

// Body
val SimpleLabBodyLarge = TextStyle(
    fontFamily = IbmPlexRegular,
    fontSize = fontSizes.normal14,
    color = Black,
)

val SimpleLabBodyMedium = TextStyle(
    fontFamily = IbmPlexRegular,
    fontSize = fontSizes.small12,
    color = Black,
)

val SimpleLabBodySmall = TextStyle(
    fontFamily = IbmPlexRegular,
    fontSize = fontSizes.xSmall10,
    color = Black,
)

val Typography = Typography(
    displayLarge = SimpleLabHeadingXLarge,
    displayMedium = SimpleLabTitleXLarge,
    displaySmall = SimpleLabLabelXLarge,
    headlineLarge = SimpleLabHeadingLarge,
    headlineMedium = SimpleLabHeadingMedium,
    headlineSmall = SimpleLabHeadingSmall,
    titleLarge = SimpleLabTitleLarge,
    titleMedium = SimpleLabTitleMedium,
    titleSmall = SimpleLabTitleSmall,
    bodyLarge = SimpleLabBodyLarge,
    bodyMedium = SimpleLabBodyMedium,
    bodySmall = SimpleLabBodySmall,
    labelLarge = SimpleLabLabelLarge,
    labelMedium = SimpleLabLabelMedium,
    labelSmall = SimpleLabLabelSmall,
)