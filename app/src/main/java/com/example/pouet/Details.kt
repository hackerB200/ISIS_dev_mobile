package com.example.pouet

import android.widget.GridLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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

@Composable
fun DetailMovieScreen(windowSizeClass: WindowSizeClass, viewModel: MainViewModel) {
    val movie by viewModel.currentMovie.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(15.dp)) {
        item {
            Text(text = movie?.title ?: "", style = MaterialTheme.typography.headlineLarge)

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w780/" + movie?.poster_path,
                contentDescription = movie?.id.toString(),
            )

        }
        item {
            Row {
                movie?.genres?.forEach {
                    AssistChip(
                        label = { Text(text = it.name, color = Color.White) },
                        onClick = { },
                        modifier = Modifier.padding(horizontal = 5.dp),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = colorResource(R.color.teal_700)
                        )
                    )
                }
            }
        }

        item {
            Text(text = "Synopsis", style = MaterialTheme.typography.headlineMedium)
            Text(text = movie?.overview ?: "")
        }

        item {

            movie?.credits?.cast?.take(5)?.forEach {
//                    item {
                Text(text = it.name)
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w154/" + it.profile_path,
                    contentDescription = it.name,
                )
//                    }

            }
        }
    }
}

fun DetailSerieScreen(windowSizeClass: WindowSizeClass, viewModel: MainViewModel) {}
fun DetailActorScreen(windowSizeClass: WindowSizeClass, viewModel: MainViewModel) {}