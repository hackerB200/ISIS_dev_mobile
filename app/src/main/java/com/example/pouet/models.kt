package com.example.pouet

data class Playlist(
    val checksum: String,
    val collaborative: Boolean,
    val cover: String,
    val creation_date: String,
    val creator: Creator,
    val description: String,
    val duration: Int,
    val fans: Int,
    val id: Int,
    val is_loved_track: Boolean,
    val link: String,
    val md5_image: String,
    val nb_tracks: Int,
    val picture_type: String,
    val `public`: Boolean,
    val share: String,
    val title: String,
    val tracklist: String,
    val tracks: Tracks,
    val type: String
)

data class Creator(
    val id: Int,
    val name: String,
    val tracklist: String,
    val type: String
)

data class Tracks(
    val checksum: String,
    val `data`: List<Data>
)

data class Data(
    val album: Album,
    val artist: Artist,
    val duration: Int,
    val explicit_content_cover: Int,
    val explicit_content_lyrics: Int,
    val explicit_lyrics: Boolean,
    val id: Long,
    val isrc: String,
    val link: String,
    val md5_image: String,
    val preview: String,
    val rank: Int,
    val readable: Boolean,
    val time_add: Int,
    val title: String,
    val title_short: String,
    val title_version: String,
    val type: String
)

data class Album(
    val cover: String,
    val id: Int,
    val md5_image: String,
    val title: String,
    val tracklist: String,
    val type: String,
    val upc: String
)

data class Artist(
    val id: Int,
    val link: String,
    val name: String,
    val tracklist: String,
    val type: String
)

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