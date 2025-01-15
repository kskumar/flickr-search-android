package com.example.flickrsearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.example.flickrsearch.model.FlickrImage
import com.example.flickrsearch.ui.theme.FlickrSearchTheme
import com.example.flickrsearch.vm.ImageSearchViewModel
import com.example.flickrsearch.vm.ViewAction
import com.example.flickrsearch.vm.ViewEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ImageSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setUpEventFlowCollection()
        setContent {
            val state = viewModel.state.collectAsState()
            FlickrSearchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Row {
                            OutlinedTextField(
                                value = state.value.userQueryTextValue,
                                onValueChange = {
                                    viewModel.updateState(newState = state.value.copy(userQueryTextValue = it))
                                    if (it.length > 2) {
                                        viewModel.updateState(newState = state.value.copy(isLoading = true))
                                        viewModel.search(userQuery = it)
                                    }
                                    else if (state.value.results.isNotEmpty()) {
                                        viewModel.updateState(newState = state.value.copy(results = emptyList()))
                                    }
                                },
                                label = { Text("Flickr Image Search") },
                                modifier = Modifier
                                    .weight(weight = 1f)
                                    .padding(vertical = 16.dp)
                            )
                            if (state.value.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.width(32.dp),
                                    color = MaterialTheme.colorScheme.secondary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Adaptive(minSize = 72.dp),
                            verticalItemSpacing = 4.dp,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(state.value.results) { it ->
                                FlickrImageUiContent(
                                    image = it,
                                    onClick = {
                                        viewModel.sendAction(
                                            action = ViewAction.ImageClick(
                                                imageUrl = it.media.mediumImageUrl,
                                                title = it.title,
                                                description = it.description,
                                                authorName = it.authorName,
                                                publishedDate = it.publishedTimestamp,
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun setUpEventFlowCollection() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is ViewEvent.NavigateToImageDetails -> navigateToImageDetails(
                            imageUrl = event.imageUrl,
                            title = event.title,
                            description = event.description,
                            author = event.author,
                            date = event.date,
                        )
                    }
                }
            }
        }
    }

    fun navigateToImageDetails(
        imageUrl: String,
        title: String,
        description: String,
        author: String,
        date: String
    ) {
        startActivity(Intent(this, ImageDetailActivity::class.java).apply {
            putExtra(ImageDetailActivity.EXTRA_IMAGE_URL, imageUrl)
            putExtra(ImageDetailActivity.EXTRA_TITLE, title)
            putExtra(ImageDetailActivity.EXTRA_DESCRIPTION, description)
            putExtra(ImageDetailActivity.EXTRA_AUTHOR, author)
            putExtra(ImageDetailActivity.EXTRA_DATE, date)
        })
    }

}

@Composable
fun FlickrImageUiContent(image: FlickrImage, onClick: () -> Unit) {
    AsyncImage(
        model = image.media.mediumImageUrl,
        contentDescription = image.description,
        modifier = Modifier.clickable(onClick = onClick)
    )
}