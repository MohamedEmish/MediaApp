package com.amosh.pulse.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle

// Set of Material typography styles to start with
val fontSizes = FontSizes()

// Heading
val SimpleLabHeadingXLarge = TextStyle(
    fontFamily = InterSemiBold,
    fontSize = fontSizes.headingXLarge28,
    color = Black,
)

val SimpleLabHeadingLarge = TextStyle(
    fontFamily = InterSemiBold,
    fontSize = fontSizes.headingLarge24,
    color = Black,
)

val SimpleLabHeadingMedium = TextStyle(
    fontFamily = InterMedium,
    fontSize = fontSizes.headingMedium20,
    color = Black,
)

val SimpleLabHeadingSmall = TextStyle(
    fontFamily = InterMedium,
    fontSize = fontSizes.xLarge18,
    color = Black,
)

// Title
val SimpleLabTitleXLarge = TextStyle(
    fontFamily = InterBold,
    fontSize = fontSizes.headingLarge24,
    color = Black,
)

val SimpleLabTitleLarge = TextStyle(
    fontFamily = InterSemiBold,
    fontSize = fontSizes.headingMedium20,
    color = Black,
)

val SimpleLabTitleMedium = TextStyle(
    fontFamily = InterMedium,
    fontSize = fontSizes.xLarge18,
    color = Black,
)

val SimpleLabTitleSmall = TextStyle(
    fontFamily = InterMedium,
    fontSize = fontSizes.large16,
    color = Black,
)

// Label
val SimpleLabLabelXLarge = TextStyle(
    fontFamily = InterRegular,
    fontSize = fontSizes.xLarge18,
    color = Black,
)

val SimpleLabLabelLarge = TextStyle(
    fontFamily = InterMedium,
    fontSize = fontSizes.large16,
    color = Black,
)

val SimpleLabLabelMedium = TextStyle(
    fontFamily = InterMedium,
    fontSize = fontSizes.normal14,
    color = Black,
)

val SimpleLabLabelSmall = TextStyle(
    fontFamily = InterMedium,
    fontSize = fontSizes.small12,
    color = Black,
)

// Body
val SimpleLabBodyLarge = TextStyle(
    fontFamily = InterRegular,
    fontSize = fontSizes.normal14,
    color = Black,
)

val SimpleLabBodyMedium = TextStyle(
    fontFamily = InterRegular,
    fontSize = fontSizes.small12,
    color = Black,
)

val SimpleLabBodySmall = TextStyle(
    fontFamily = InterRegular,
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