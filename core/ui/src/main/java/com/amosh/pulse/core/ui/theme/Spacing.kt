package com.amosh.pulse.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FontSizes(
    val headingXLarge28: TextUnit = 28.sp,
    val headingLarge24: TextUnit = 24.sp,
    val headingMedium20: TextUnit = 20.sp,
    val xLarge18: TextUnit = 18.sp,
    val large16: TextUnit = 16.sp,
    val normal14: TextUnit = 14.sp,
    val small12: TextUnit = 12.sp,
    val xSmall10: TextUnit = 10.sp,
    val xXSmall8: TextUnit = 8.sp,
    val xXXSmall4: TextUnit = 4.sp,
    val xXXXSmall2: TextUnit = 2.sp,
)

data class Spacing(
    val xXXLarge48: Dp = 48.dp,
    val xXLarge40: Dp = 40.dp,
    val xLarge32: Dp = 32.dp,
    val large24: Dp = 24.dp,
    val medium16: Dp = 16.dp,
    val small8: Dp = 8.dp,

    val special343: Dp = 343.dp,
    val special336: Dp = 336.dp,
    val special304: Dp = 304.dp,
    val special294: Dp = 294.dp,
    val special274: Dp = 274.dp,
    val special248: Dp = 248.dp,
    val special204: Dp = 204.dp,
    val special224: Dp = 224.dp,
    val special194: Dp = 194.dp,
    val special188: Dp = 188.dp,
    val special168: Dp = 168.dp,
    val special164: Dp = 164.dp,
    val special156: Dp = 156.dp,
    val special140: Dp = 140.dp,
    val special128: Dp = 128.dp,
    val special124: Dp = 124.dp,
    val special112: Dp = 112.dp,
    val special108: Dp = 108.dp,
    val special104: Dp = 104.dp,
    val special100: Dp = 100.dp,
    val special96: Dp = 96.dp,
    val special94: Dp = 94.dp,
    val special88: Dp = 88.dp,
    val special84: Dp = 84.dp,
    val special80: Dp = 80.dp,
    val special76: Dp = 76.dp,
    val special72: Dp = 72.dp,
    val special64: Dp = 64.dp,
    val special60: Dp = 60.dp,
    val special56: Dp = 56.dp,
    val special52: Dp = 52.dp,
    val special50: Dp = 50.dp,
    val special44: Dp = 44.dp,
    val special42: Dp = 42.dp,
    val special37: Dp = 37.dp,
    val special36: Dp = 36.dp,
    val special34: Dp = 34.dp,
    val special30: Dp = 30.dp,
    val special28: Dp = 28.dp,
    val special26: Dp = 26.dp,
    val special25: Dp = 25.dp,
    val special22: Dp = 22.dp,
    val special20: Dp = 20.dp,
    val special15: Dp = 15.dp,
    val special14: Dp = 14.dp,
    val special12: Dp = 12.dp,
    val special10: Dp = 10.dp,
    val special9: Dp = 9.dp,
    val special7: Dp = 7.dp,
    val special6: Dp = 6.dp,
    val special4: Dp = 4.dp,
    val special2: Dp = 2.dp,
    val special1: Dp = 1.dp,
    val special0: Dp = 0.dp,

    val specialMinus4: Dp = (-4).dp,
    val specialMinus8: Dp = (-8).dp,
    val specialMinus10: Dp = (-10).dp,
    val specialMinus12: Dp = (-12).dp,
    val specialMinus15: Dp = (-15).dp,
    val specialMinus16: Dp = (-16).dp,
    val specialMinus24: Dp = (-24).dp,
)

// local composition value to be added in the theme
val LocalSpacing = compositionLocalOf { Spacing() }
val LocalFontSize = compositionLocalOf { FontSizes() }

// extension of usage
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

val MaterialTheme.fontSizes: FontSizes
    @Composable
    @ReadOnlyComposable
    get() = LocalFontSize.current