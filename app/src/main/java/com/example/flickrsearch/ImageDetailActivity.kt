package com.example.flickrsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.flickrsearch.ui.theme.FlickrSearchTheme
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

class ImageDetailActivity: ComponentActivity() {

    private lateinit var imageUrl: String
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var author: String
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadImageDataFromExtras()
        setContent {
            FlickrSearchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        AsyncImage(model = imageUrl, contentDescription = description)
                        Text(text = "Title: $title")
                        Text(text = "Description: $description")
                        Text(text = "Author: $author")
                        Text(text = "Date: ${getHumanReadableDate(iso8601Date = date)}")
                    }
                }
            }
        }
    }

    private fun loadImageDataFromExtras() {
        intent?.extras?.let {
            imageUrl = it.getString(EXTRA_IMAGE_URL, "")
            title = it.getString(EXTRA_TITLE, "")
            description = it.getString(EXTRA_DESCRIPTION, "")
            author = it.getString(EXTRA_AUTHOR, "")
            date = it.getString(EXTRA_DATE, "")
        }
    }

    private fun getHumanReadableDate(iso8601Date: String): String {
        val date: Instant = Instant.parse(input = iso8601Date)
        val outputFormat = DateTimeComponents.Format {
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            dayOfMonth()
            chars(", ")
            year()
        }
        return date.format(format = outputFormat)
    }

    companion object {
        const val EXTRA_IMAGE_URL: String = "imageUrl"
        const val EXTRA_TITLE: String = "title"
        const val EXTRA_DESCRIPTION: String = "description"
        const val EXTRA_AUTHOR: String = "author"
        const val EXTRA_DATE: String = "date"
    }

}