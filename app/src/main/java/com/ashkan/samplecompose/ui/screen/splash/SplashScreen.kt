package com.ashkan.samplecompose.ui.screen.splash

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashkan.samplecompose.R
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
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state: SplashState by viewModel.stateValue.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state.navigateToLogin) {
            onNavigateToLogin()
            viewModel.onResetState()
        }
        if (state.navigateToHome) {
            onNavigateToHome()
            viewModel.onResetState()
        }
    }

    SplashScreen(
        modifier = modifier,
        uiState = state,
        versionName = "1.1.0" // sample
    )
}

@Composable
internal fun SplashScreen(
    modifier: Modifier = Modifier,
    versionName: String = "",
    uiState: SplashState,
) {
    Column(modifier = modifier) {
        when {
            uiState.isLoading -> {
                SplashBody(
                    modifier = modifier,
                    versionName = versionName
                )
            }
            uiState.tokenError != null -> {
                FailureBody(
                    modifier = modifier,
                    message = uiState.tokenError.errorMessage
                )
            }
        }
    }
}

@Composable
private fun SplashBody(
    modifier: Modifier = Modifier,
    versionName: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.weight(1F))
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "SplashIcon",
            modifier = modifier.size(180.dp),
        )
        Spacer(modifier = modifier.weight(1F))
        Text(
            modifier = modifier.padding(bottom = 16.dp),
            text = "Version: $versionName",
            fontSize = 14.sp,
        )
        CircularProgressIndicator(
            modifier = modifier
                .size(24.dp)
                .testTag("SplashProgress"),
            strokeWidth = 3.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = modifier.height(32.dp))
    }
}

@Composable
private fun FailureBody(
    modifier: Modifier = Modifier,
    message: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            fontSize = 25.sp,
        )
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
            SplashBody()
        }
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
fun FailureBodyPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FailureBody(message = "Preview message!")
        }
    }
}