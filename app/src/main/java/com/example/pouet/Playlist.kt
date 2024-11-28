package com.example.pouet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage

@Composable
fun Playlist(windowSizeClass: WindowSizeClass, viewModel: MainViewModel) {

    val playlist by viewModel.playlist.collectAsStateWithLifecycle()

    var columns = 2
    if (windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
        columns = 3
    }

    if (playlist == null) {
        viewModel.getPlaylist()

    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                AsyncImage(
                    model = "file:///android_asset/images/" + playlist!!.cover,
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                )
                Text(text = playlist?.title ?: "", style = MaterialTheme.typography.headlineSmall)
            }
            Row {
                Text(text = "Créée par " + playlist!!.creator.name)
            }
            HorizontalDivider(modifier = Modifier.padding(top = 10.dp))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(5.dp)
        ) {
            val listTracks = playlist!!.tracks.data
            items(listTracks.size) { index ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.teal_700),
                        contentColor = Color.Black,
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = "file:///android_asset/images/" + listTracks[index].album.cover,
                            contentDescription = listTracks[index].id.toString(),
                            modifier = Modifier.clip(CircleShape)
                        )
                        Text(text = listTracks[index].title, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }


}