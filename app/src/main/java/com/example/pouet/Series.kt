package com.example.pouet

import android.os.Build
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
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage
import java.time.LocalDate
import java.time.format.TextStyle

@Composable
fun SeriesScreen(windowClass: WindowSizeClass, viewModel: MainViewModel) {
    val series by viewModel.series.collectAsStateWithLifecycle()

    var columns = 2
    if (windowClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
        columns = 3
    }

    if (series.isEmpty()) {
        Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = stringResource(R.string.page_series), color = Color.White, style = MaterialTheme.typography.headlineLarge)
        }
        Log.e("series", "series empty")
        viewModel.getSeriesInitiales()
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(columns), horizontalArrangement = Arrangement.spacedBy(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
            items(series.size) { index ->
                Card(
                    modifier = Modifier.defaultMinSize(minHeight = 350.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.teal_700),
                        contentColor = Color.Black,
                    ),
                    onClick = {
                        viewModel.selectSerie(series[index].id)
                    }
                ) {
                    Column (modifier = Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w780/" + series[index].poster_path,
                            contentDescription = series[index].id.toString(),
                            modifier = Modifier.clip(MaterialTheme.shapes.medium)
                        )
                        Text(
                            text = series[index].name,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        val date: String
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            date = LocalDate.parse(series[index].first_air_date).dayOfMonth.toString() + " " + LocalDate.parse(series[index].first_air_date).month.getDisplayName(
                                TextStyle.SHORT, Locale.current.platformLocale)+ " " + LocalDate.parse(series[index].first_air_date).year.toString()

                        } else {
                            date = series[index].first_air_date
                        }
                        Text(
                            text = date,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            if (series[index].isFavorite) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorite",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp).clickable {
                                        viewModel.removeSerieFromFav(series[index].id)
                                    }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "NotFavorite",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp).clickable {
                                        viewModel.addSerieToFav(series[index].id)
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

