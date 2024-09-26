package com.example.pouet

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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

    var searchText by mutableStateOf("")
    var isSearching by mutableStateOf(false)

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

    //Search Bar actions
    fun openSearchBar() {
        isSearching = true
    }

    fun closeSearchBar() {
        isSearching = false
    }

    fun onSearch() {
        isSearching = false
        when(currentDestination) {
            Destination.MOVIES -> searchMovies(searchText)
            Destination.SERIES -> searchSeries(searchText)
            Destination.ACTORS -> searchActors(searchText)
            else -> searchMovies(searchText)
        }

    }

    private fun searchMovies(search: String) {
        viewModelScope.launch {
            val listMovies = api.searchMovies(api_key, search)
            movies.value = listMovies.results
        }
    }

    private fun searchSeries(search: String) {
        viewModelScope.launch {
            val listSeries = api.searchSeries(api_key, search)
            series.value = listSeries.results
        }
    }

    private fun searchActors(search: String) {
        viewModelScope.launch {
            val listActors = api.searchActors(api_key, search)
            actors.value = listActors.results
        }
    }

}