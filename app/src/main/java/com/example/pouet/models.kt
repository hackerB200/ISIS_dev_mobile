package com.example.pouet

class Actor(
    val biography: String = "",
    val birthday: String = "",
    val deathday: String? = null,
    val id: Int = 0,
    val known_for_department: String = "",
    val name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String? = ""
)

class ListActors(
    var page: Int = 0,
    var results: List<Actor> = listOf(),
)

class Genre(
    val id: Int = 0,
    val name: String = ""
)

class Credits(
    val cast: List<Actor> = listOf(),
    val crew: List<Actor> = listOf(),
)

abstract class Video {
    val genres: List<Genre> = listOf()
    val id: Int = 0
    val origin_country: List<String> = listOf()
    val overview: String = ""
    val vote_average: Double = 0.0
    val credits: Credits = Credits()
    val poster_path: String = ""
}

class Movie : Video() {
    val release_date: String = ""
    val title: String = ""
}

class ListMovies(
    var page: Int = 0,
    var results: List<Movie> = listOf(),
)

class Serie : Video() {
    val first_air_date: String = ""
    val name: String = ""
}

class ListSeries (
    var page: Int = 0,
    var results: List<Serie> = listOf(),
)