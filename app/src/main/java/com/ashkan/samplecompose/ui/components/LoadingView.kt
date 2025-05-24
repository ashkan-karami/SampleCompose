package com.ashkan.samplecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme

@Composable
fun ApiCallLoading(modifier: Modifier = Modifier) {

    CircularProgressIndicator(
        modifier = modifier
            .size(24.dp)
            .testTag("ApiCallProgress"),
        strokeWidth = 3.dp,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Preview(name = "Light")
@Preview(name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ApiCallLoadingPreview(){
    SampleComposeTheme {
        ApiCallLoading()
    }
}