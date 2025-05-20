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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashkan.samplecompose.R
import com.ashkan.samplecompose.data.core.defaultErrorMessage
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme
import com.ashkan.samplecompose.util.getAppVersionName

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
    val context = LocalContext.current
    val versionName = remember { context.getAppVersionName() }

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
        versionName = versionName,
        onDialogDismissed = {
            viewModel.onAction(SplashAction.UpdateDialogDismissed)
        }
    )
}

@Composable
internal fun SplashScreen(
    modifier: Modifier = Modifier,
    uiState: SplashState,
    versionName: String,
    onDialogDismissed: () -> Unit
) {
    Column(modifier = modifier) {
        when {
            uiState.isLoading -> {
                SplashBody(
                    modifier = modifier,
                    versionName = versionName
                )
            }
            uiState.showUpdateDialog -> {
                ShowUpdateDialog(
                    uiState.updateDialogTitle,
                    uiState.updateDialogMessage,
                    onDialogDismissed)
            }
            uiState.appConfigFailureMessage != null -> {
                FailureBody(
                    modifier = modifier,
                    message = uiState.appConfigFailureMessage
                )
            }
        }
    }
}

@Composable
private fun SplashBody(
    modifier: Modifier = Modifier,
    versionName: String
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

@Composable
private fun ShowUpdateDialog(
    title: String,
    message: String,
    onDialogDismissed: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDialogDismissed,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDialogDismissed) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDialogDismissed,
                modifier = Modifier.testTag("CloseDialog")) {
                Text("Close")
            }
        }
    )
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
fun SplashBodyPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SplashScreen(
                uiState = SplashState(
                    isLoading = true
                ),
                versionName = "1.1.1",
                onDialogDismissed = {}
            )
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
            SplashScreen(
                uiState = SplashState(
                    appConfigFailureMessage = "Preview message!"
                ),
                versionName = "1.1.1",
                onDialogDismissed = {}
            )
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
fun UpdateDialogPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SplashScreen(
                uiState = SplashState(
                    showUpdateDialog = true,
                    updateDialogTitle = "Title",
                    updateDialogMessage = "Message"
                ),
                versionName = "1.1.1",
                onDialogDismissed = {}
            )
        }
    }
}