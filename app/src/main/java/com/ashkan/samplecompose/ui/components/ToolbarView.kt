package com.ashkan.samplecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashkan.samplecompose.ui.theme.SairaFontFamily
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme
import com.ashkan.samplecompose.util.defaultHorizontalSpace
import com.ashkan.samplecompose.util.getStatusBarHeight
import com.ashkan.samplecompose.util.toolbarHorizontalSpace
import com.ashkan.samplecompose.util.toolbarVerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdgeToEdgeToolbar(
    title: String,
    modifier: Modifier = Modifier,
    withStatusBarSpace: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                top = if (withStatusBarSpace) getStatusBarHeight()
                else 0.dp
            ).padding(horizontal = toolbarHorizontalSpace),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(vertical = toolbarVerticalSpace))
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
fun ToolbarPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            EdgeToEdgeToolbar("Fake title")
        }
    }
}