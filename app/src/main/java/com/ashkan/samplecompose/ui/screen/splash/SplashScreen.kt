package com.ashkan.samplecompose.ui.screen.splash

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme

/**
 *   collectAsStateWithLifecycle checks the lifecycle and stops emitting & collecting when OnPause.
 *   collectAsStateWithLifecycle works with StateFlows, so if you collect a SharedFlow, you need to
 *   pass it a default value.
 *   If you collect view model out of LaunchedEffect, it will be collected/called on every recompose.
 */

@Composable
internal fun SplashRoute(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true){

    }
}

@Composable
internal fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

    }
}


@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SplashScreenPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SplashScreen()
        }
    }
}