package com.ashkan.samplecompose.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashkan.samplecompose.ui.components.EdgeToEdgeToolbar
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        modifier = modifier
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        EdgeToEdgeToolbar("For You")
        ArticleItems(modifier = modifier)
    }
}

@Composable
private fun ArticleItems(
    modifier: Modifier = Modifier
) {
    val state = rememberLazyGridState(
        initialFirstVisibleItemIndex = 25
    )
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(8.dp),
        state = state,
    ){

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
            HomeScreen()
        }
    }
}