package com.example.pouet

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Destination (index: Int) {
    MOVIES(0),
    SERIES(1),
    ACTORS(2),
    PROFILE(3),
    DETAILS_MOVIES(4),
    DETAILS_SERIES(5),
    DETAILS_ACTORS(6);
}

@HiltViewModel
class MainViewModel  @Inject constructor(private val repo: Repository): ViewModel() {

    var currentDestination by mutableStateOf(Destination.PROFILE)

    var searchText by mutableStateOf("")
    var isSearching by mutableStateOf(false)

    var movies = MutableStateFlow<List<Movie>>(listOf())
    var series = MutableStateFlow<List<Serie>>(listOf())
    var actors = MutableStateFlow<List<Actor>>(listOf())
    var currentMovie = MutableStateFlow<Movie?> (null)
    var currentSerie = MutableStateFlow<Serie?> (null)
    var currentActor = MutableStateFlow<Actor?> (null)

    fun navigateTo(index: Int) {
        currentDestination = Destination.entries.toTypedArray()[index]
    }

    fun getFilmsInitiaux() {
        viewModelScope.launch { movies.value = repo.trendingMovies().results }
    }

    fun getSeriesInitiales() {
        viewModelScope.launch { series.value = repo.trendingSeries().results }
    }

    fun getActeursInitiaux() {
       viewModelScope.launch { actors.value = repo.trendingActors().results }
    }

    fun selectMovie(id: Int) {
        viewModelScope.launch {
            val movie = repo.getMovie(id)
            currentMovie.value = movie
        }
        currentDestination = Destination.DETAILS_MOVIES
    }

    fun selectSerie(id: Int) {
        viewModelScope.launch {
            val serie = repo.getSeries(id)
            currentSerie.value = serie
        }
        currentDestination = Destination.DETAILS_SERIES
    }

    fun selectActor(id: Int) {
        viewModelScope.launch {
            val actor = repo.getActor(id)
            currentActor.value = actor
        }
        currentDestination = Destination.DETAILS_ACTORS
    }

    private fun searchMovies(search: String) {
       viewModelScope.launch {
           val listMovies = repo.searchMovies(search)
           movies.value = listMovies.results
       }
    }

    private fun searchSeries(search: String) {
        viewModelScope.launch {
            val listSeries = repo.searchSeries(search)
            series.value = listSeries.results
        }
    }

    private fun searchActors(search: String) {
        viewModelScope.launch {
            val listActors = repo.searchActors(search)
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

}