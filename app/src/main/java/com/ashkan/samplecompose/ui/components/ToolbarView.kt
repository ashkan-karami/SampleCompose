package com.ashkan.samplecompose.ui.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashkan.samplecompose.ui.theme.SairaFontFamily
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme
import com.ashkan.samplecompose.util.getStatusBarHeight
import com.ashkan.samplecompose.util.toolbarHorizontalSpace
import com.ashkan.samplecompose.util.toolbarVerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdgeToEdgeToolbar(
    title: String,
    modifier: Modifier = Modifier,
    withStatusBarSpace: Boolean = true,
    withBackButton: Boolean = true,
    backButtonIcon: @Composable () -> Unit,
    toolbarIcons: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                top = if (withStatusBarSpace) getStatusBarHeight()
                else 0.dp
            )
            .padding(horizontal = toolbarHorizontalSpace),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            backButtonIcon()
            if (withBackButton) {
                Spacer(modifier = modifier.width(4.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = SairaFontFamily,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier.padding(vertical = toolbarVerticalSpace)
            )
            toolbarIcons()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdgeToEdgeHomeToolbar(
    title: String,
    isSearching: Boolean,
    searchPhrase: String,
    onSearchClicked: () -> Unit,
    onSearchPhraseChanged: (String) -> Unit,
    onSearchClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = getStatusBarHeight())
            .padding(horizontal = toolbarHorizontalSpace),
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            this@Column.AnimatedVisibility(
                visible = !isSearching,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = SairaFontFamily,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier.padding(vertical = toolbarVerticalSpace)
                    )
                    Spacer(modifier = modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "HomeToolbarSearch",
                        modifier = modifier.clickable(
                            onClick = onSearchClicked
                        )
                    )
                }
            }

            this@Column.AnimatedVisibility(
                visible = isSearching,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchPhrase,
                        onValueChange = onSearchPhraseChanged,
                        placeholder = {
                            Text(
                                modifier = modifier.alpha(0.5F),
                                text = "Type here...",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontFamily = SairaFontFamily
                            )
                        },
                        modifier = Modifier.weight(1F)
                            .padding(end = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = SairaFontFamily,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        maxLines = 1
                    )
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = modifier.clickable(
                            onClick = onSearchClosed
                        ).testTag("HomeToolbarSearchClose")
                    )
                }
            }
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
fun ToolbarPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            EdgeToEdgeToolbar(
                "Fake title",
                backButtonIcon = {},
                toolbarIcons = {}
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
fun HomeToolbarPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            EdgeToEdgeHomeToolbar(
                "Home title",
                isSearching = true,
                searchPhrase = "",
                onSearchClicked = {},
                onSearchPhraseChanged = {},
                onSearchClosed = {}
            )
        }
    }
}