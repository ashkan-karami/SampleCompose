package com.ashkan.samplecompose.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val defaultHorizontalSpace = 16.dp
val defaultVerticalSpace = 16.dp
val toolbarVerticalSpace = 12.dp // Vertical padding in Toolbar-Title

@Composable
fun getStatusBarHeight(): Dp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()