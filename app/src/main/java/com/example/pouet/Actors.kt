package com.example.pouet

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage

@Composable
fun ActorsScreen(windowClass: WindowSizeClass, viewModel: MainViewModel) {
    val actors by viewModel.actors.collectAsStateWithLifecycle()

    var columns = 2
    if (windowClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
        columns = 3
    }

    if (actors.isEmpty()) {
        Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = stringResource(R.string.page_actors), color = Color.White, style = MaterialTheme.typography.headlineLarge)
        }
        Log.e("actors", "actors empty")
        viewModel.getActeursInitiaux()
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(columns), horizontalArrangement = Arrangement.spacedBy(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
            items(actors.size) { index ->
                Card(
                    modifier = Modifier.defaultMinSize(minHeight = 350.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.teal_700),
                        contentColor = Color.Black,
                    ),
                    onClick = {
                        viewModel.selectActor(actors[index].id)
                    }
                ) {
                    Column (modifier = Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        if (actors[index].profile_path != null) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w780/" + actors[index].profile_path,
                                contentDescription = actors[index].id.toString(),
                                modifier = Modifier.clip(MaterialTheme.shapes.medium)
                            )
                        }
                        Text(
                            text = actors[index].name,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = actors[index].popularity.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            if (actors[index].isFavorite) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorite",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp).clickable {
                                        viewModel.removeActorFromFav(actors[index].id)
                                    }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "NotFavorite",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp).clickable {
                                        viewModel.addActorToFav(actors[index].id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

