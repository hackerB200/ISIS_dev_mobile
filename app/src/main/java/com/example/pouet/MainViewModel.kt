package com.example.pouet

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

enum class Destination (index: Int) {
    MOVIES(0),
    SERIES(1),
    ACTORS(2),
    PROFILE(3)
}

class MainViewModel : ViewModel() {

    var currentDestination by mutableStateOf(Destination.PROFILE)

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();
    val api = retrofit.create(TMDBApi::class.java)
    val api_key = "741e74ad116edeaf99ff07418b68a085"

    var movies = MutableStateFlow<List<Movie>>(listOf())
    var series = MutableStateFlow<List<Serie>>(listOf())
    var actors = MutableStateFlow<List<Actor>>(listOf())

    fun navigateTo(index: Int) {
        currentDestination = Destination.entries.toTypedArray()[index]
    }

    fun getFilmsInitiaux() {
        viewModelScope.launch {
            val listMovies = api.getTrendingMovies(api_key)
            movies.value = listMovies.results
        }
    }

    fun getSeriesInitiales() {
        viewModelScope.launch {
            val listSeries = api.getTrendingSeries(api_key)
            series.value = listSeries.results
        }
    }

    fun getActeursInitiaux() {
        viewModelScope.launch {
            val listActors = api.getTrendingActors(api_key)
            actors.value = listActors.results
        }
    }


}