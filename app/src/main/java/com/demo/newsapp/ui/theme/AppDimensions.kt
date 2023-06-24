package com.demo.newsapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AppDimensions(

    val zero_dp: Dp = 0.dp,
    val two_dp: Dp = 2.dp,
    val eight_dp: Dp = 2.dp,
    val sixteen_dp: Dp = 16.dp,
    val twenty_two_dp: Dp = 22.dp,

    val image_min_height: Dp = 180.dp,
    val line_height: Dp = 1.dp,

    // Text size
    val text_size_default: TextUnit = 14.sp,
    val text_size_tag: TextUnit = 10.sp,
    val text_size_description: TextUnit = 12.sp,
    val text_size_body_medium: TextUnit = 16.sp,
    val text_size_subtitle_small: TextUnit = 18.sp,
)

internal val LocalDimensions = staticCompositionLocalOf { AppDimensions() }
