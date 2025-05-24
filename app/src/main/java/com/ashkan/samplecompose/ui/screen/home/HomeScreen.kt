package com.ashkan.samplecompose.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashkan.samplecompose.data.model.post.PostModel
import com.ashkan.samplecompose.ui.components.ApiCallLoading
import com.ashkan.samplecompose.ui.components.EdgeToEdgeToolbar
import com.ashkan.samplecompose.ui.theme.SairaFontFamily
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme
import com.ashkan.samplecompose.util.defaultHorizontalSpace
import com.ashkan.samplecompose.util.defaultVerticalSpace
import com.ashkan.samplecompose.util.getNavigationBarHeight
import java.util.ArrayList

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state: HomeState by viewModel.stateValue.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onReloadCLicked = {
            viewModel.onAction(HomeAction.GetPosts)
        },
        modifier = modifier
    )
}

@Composable
internal fun HomeScreen(
    state: HomeState,
    onReloadCLicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        EdgeToEdgeToolbar("For You")
        Spacer(modifier = modifier.height(1.dp))
        when {
            state.isLoading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ApiCallLoading()
                }
            }

            state.content.isNotEmpty() -> {
                PostItems(
                    posts = state.content,
                    modifier = modifier
                )
            }

            state.postApiFailureMessage != null -> {
                FailureBody(
                    message = state.postApiFailureMessage,
                    onReloadCLicked = onReloadCLicked
                )
            }
        }
    }
}

@Composable
private fun PostItems(
    posts: List<PostModel>,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState(
        initialFirstVisibleItemIndex = 0
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = getNavigationBarHeight()
        ),
        state = state,
    ) {
        items(posts.size) { i ->
            val post = posts[i]
            PostItem(
                post = post,
                modifier = modifier
            )
        }
    }
}

@Composable
fun PostItem(
    post: PostModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
            .padding(
                horizontal = defaultHorizontalSpace,
                vertical = defaultVerticalSpace
            )
    ) {
        Text(
            text = post.title ?: "Unknown",
            style = MaterialTheme.typography.titleMedium,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = post.body ?: "Unknown",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
                .alpha(0.6F)
                .padding(top = 4.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = modifier.fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "See details",
                style = MaterialTheme.typography.labelMedium,
                fontFamily = SairaFontFamily,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier
                    .padding(end = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "postArrowIcon",
                modifier = modifier
                    .size(14.dp)
                    .rotate(180f)
            )
        }
    }
}

@Composable
private fun FailureBody(
    message: String,
    onReloadCLicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = defaultHorizontalSpace),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(defaultVerticalSpace))
        Button(onClick = onReloadCLicked) {
            Text(
                text = "Reload",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = SairaFontFamily,
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
fun HomeScreenPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                state = HomeState(content = ArrayList(20)),
                onReloadCLicked = {}
            )
        }
    }
}

@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeScreenLoadingPreview() {
    SampleComposeTheme {
        HomeScreen(
            state = HomeState(isLoading = true),
            onReloadCLicked = {}
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
fun HomeScreenFailurePreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                state = HomeState(postApiFailureMessage = "Api server failed!"),
                onReloadCLicked = {}
            )
        }
    }
}