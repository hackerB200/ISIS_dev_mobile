package com.example.pouet

import com.shirishkoirala.fontawesome.Icons
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import coil.compose.AsyncImage
import com.shirishkoirala.fontawesome.ComposeIconView

@Composable
fun DetailMovieScreen(windowSizeClass: WindowSizeClass, viewModel: MainViewModel) {
    val movie by viewModel.currentMovie.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        item {
            movie?.let { TitleAndImage(it, it.title) }
        }

        item {
            movie?.let { Genres(it) }
        }

        item {
            movie?.let { CountryAndRating(it) }
        }

        item {
            movie?.let { Synopsis(it) }
        }

        movie?.credits?.cast?.take(n = 5)?.forEach {
            item {
                Text(text = it.name)
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w154/" + it.profile_path,
                    contentDescription = it.name,
                    modifier = Modifier.clickable(onClick = {
                        viewModel.selectActor(it.id)
                    })
                )
            }
        }
    }
}

@Composable
fun DetailSerieScreen(windowSizeClass: WindowSizeClass, viewModel: MainViewModel) {
    val serie by viewModel.currentSerie.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        item {
            serie?.let { TitleAndImage(it, it.name) }
        }

        item {
            serie?.let { Genres(it) }
        }

        item {
            serie?.let { CountryAndRating(it) }
        }

        item {
            serie?.let { Synopsis(it) }
        }

        serie?.credits?.cast?.take(n = 5)?.forEach {
            item {
                Text(text = it.name)
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w154/" + it.profile_path,
                    contentDescription = it.name,
                    modifier = Modifier.clickable(onClick = {
                        viewModel.selectActor(it.id)
                    })
                )
            }
        }
    }
}

@Composable
fun DetailActorScreen(windowSizeClass: WindowSizeClass, viewModel: MainViewModel) {
    val actor by viewModel.currentActor.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        item {
            Text(text = actor?.name ?: "", style = MaterialTheme.typography.headlineLarge)
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/" + actor?.profile_path,
                contentDescription = actor?.id.toString(),
            )
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ComposeIconView(
                    Icons.cake_candles,
                    size = 24.dp,
                    modifier = Modifier.padding(2.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = actor?.birthday ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            if (actor?.deathday != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ComposeIconView(
                        Icons.skull,
                        size = 24.dp,
                        modifier = Modifier.padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = actor?.deathday ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        item {
            AssistChip(
                label = { Text(text = actor?.known_for_department ?: "", color = Color.White) },
                onClick = { },
                modifier = Modifier.padding(horizontal = 5.dp),
                colors = AssistChipDefaults.assistChipColors(
                    disabledContainerColor = colorResource(R.color.teal_700)
                ),
                enabled = false
            )
        }

        item {
            LinearProgressIndicator(
                gapSize = 0.dp,
                progress = { (actor?.popularity?.toFloat())?.div(100) ?: 0f },
                color = colorResource(R.color.teal_700)
            )
        }

        item {
            Text(
                text = actor?.biography ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Functions

@Composable
fun CountryAndRating(video: Video) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        ComposeIconView(
            Icons.star,
            size = 24.dp,
            modifier = Modifier.padding(2.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = video.vote_average.toString() + "/10", style = MaterialTheme.typography.bodyMedium)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        ComposeIconView(
            Icons.globe,
            size = 24.dp,
            modifier = Modifier.padding(2.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        video.origin_country.forEach {
            Text(text = "$it ", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TitleAndImage(video: Video, title: String) {
    Text(text = title, style = MaterialTheme.typography.headlineLarge)
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w780/" + video.poster_path,
        contentDescription = video.id.toString(),
    )
}

@Composable
fun Genres(video: Video) {
    Row {
        video.genres.forEach {
            AssistChip(
                label = { Text(text = it.name, color = Color.White) },
                onClick = { },
                modifier = Modifier.padding(horizontal = 5.dp),
                colors = AssistChipDefaults.assistChipColors(
                    disabledContainerColor = colorResource(R.color.teal_700)
                ),
                enabled = false
            )
        }
    }
}

@Composable
fun Synopsis(video: Video) {
    Text(text = "Synopsis", style = MaterialTheme.typography.headlineMedium)
    Text(text = video.overview)
    Text(text = "Top Cast", style = MaterialTheme.typography.headlineMedium)
}