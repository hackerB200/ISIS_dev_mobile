package com.example.pouet

class Actor(
    var biography: String = "",
    var birthday: String = "",
    var deathday: String? = null,
    var id: Int = 0,
    var known_for_department: String = "",
    var name: String = "",
    var popularity: Double = 0.0,
    var profile_path: String? = "",
    var isFavorite: Boolean = false
) {
    fun copy() : Actor {
        var copy = Actor()
        copy.biography = biography
        copy.birthday = birthday
        copy.deathday = deathday
        copy.id = id
        copy.known_for_department = known_for_department
        copy.name = name
        copy.popularity = popularity
        copy.profile_path = profile_path
        copy.isFavorite = isFavorite
        return copy
    }
}

class ListActors(
    var page: Int = 0,
    var results: List<Actor> = listOf(),
)

class Genre(
    var id: Int = 0,
    var name: String = ""
)

class Credits(
    var cast: List<Actor> = listOf(),
    var crew: List<Actor> = listOf(),
)

abstract class Video {
    var genres: List<Genre> = listOf()
    var id: Int = 0
    var origin_country: List<String> = listOf()
    var overview: String = ""
    var vote_average: Double = 0.0
    var credits: Credits = Credits()
    var poster_path: String = ""
}

class Movie : Video() {
    var release_date: String = ""
    var title: String = ""
    var isFavorite: Boolean = false
    
    fun copy() : Movie {
        var copy = Movie()
        copy.genres = genres
        copy.id = id
        copy.origin_country = origin_country
        copy.overview = overview
        copy.vote_average = vote_average
        copy.credits = credits
        copy.poster_path = poster_path
        copy.release_date = release_date
        copy.title = title
        copy.isFavorite = isFavorite
        return copy
    }
}

class ListMovies(
    var page: Int = 0,
    var results: List<Movie> = listOf(),
)

class Serie : Video() {
    var first_air_date: String = ""
    var name: String = ""
    var isFavorite: Boolean = false

    fun copy() : Serie {
        var copy = Serie()
        copy.genres = genres
        copy.id = id
        copy.origin_country = origin_country
        copy.overview = overview
        copy.vote_average = vote_average
        copy.credits = credits
        copy.poster_path = poster_path
        copy.first_air_date = first_air_date
        copy.name = name
        copy.isFavorite = isFavorite
        return copy
    }
}

class ListSeries (
    var page: Int = 0,
    var results: List<Serie> = listOf(),
)