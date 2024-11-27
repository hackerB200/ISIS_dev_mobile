package com.example.pouet

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import androidx.room.*
import dagger.hilt.android.qualifiers.ApplicationContext

class Repository(private val tmdbapi: TMDBApi, private val db: TMDBDao) {

    private var api_key = "741e74ad116edeaf99ff07418b68a085"

    //MOVIES
    private suspend fun getMoviesWithFav(movies: ListMovies): ListMovies {
        val copy = ListMovies(0, movies.results.toMutableList())
        for (movie in copy.results) {
            val fav = db.getFavFilms().find { it.id == movie.id.toString() }
            if (fav != null) {
                movie.isFavorite = true
            }
        }
        return copy
    }
    suspend fun trendingMovies(): ListMovies {
        val movies = tmdbapi.getTrendingMovies(api_key)
        return getMoviesWithFav(movies)
    }
    suspend fun getMovie(movie_id: Int) = tmdbapi.getMovie(movie_id, api_key, "credits")
    suspend fun searchMovies(query: String): ListMovies {
        val movies = tmdbapi.searchMovies(api_key, query)
        return getMoviesWithFav(movies)
    }
    suspend fun addMovieToFav(movie_id: Int) = db.insertFilm(FilmEntity(tmdbapi.getMovie(movie_id, api_key, "credits"), movie_id.toString()))
    suspend fun removeMovieFromFav(movie_id: Int) = db.deleteFilm(movie_id.toString())
    suspend fun getFavMovies() = getMoviesWithFav(ListMovies(0, db.getFavFilms().map { it.fiche }.toMutableList()))

    //SERIES
    private suspend fun getSeriesWithFav(series: ListSeries): ListSeries {
        val copy = ListSeries(0, series.results.toMutableList())
        for (serie in copy.results) {
            val fav = db.getFavSeries().find { it.id == serie.id.toString() }
            if (fav != null) {
                serie.isFavorite = true
            }
        }
        return copy
    }
    suspend fun trendingSeries(): ListSeries {
        val series = tmdbapi.getTrendingSeries(api_key)
        return getSeriesWithFav(series)
    }
    suspend fun getSeries(serie_id: Int) = tmdbapi.getSeries(serie_id, api_key, "credits")
    suspend fun searchSeries(query: String): ListSeries {
        val series = tmdbapi.searchSeries(api_key, query)
        return getSeriesWithFav(series)
    }
    suspend fun addSerieToFav(serie_id: Int) = db.insertSerie(SerieEntity(tmdbapi.getSeries(serie_id, api_key, "credits"), serie_id.toString()))
    suspend fun removeSerieFromFav(serie_id: Int) = db.deleteSerie(serie_id.toString())
    suspend fun getFavSeries() = getSeriesWithFav(ListSeries(0, db.getFavSeries().map { it.fiche }.toMutableList()))

    //ACTORS
    private suspend fun getActorsWithFav(actors: ListActors): ListActors {
        val copy = ListActors(0, actors.results.toMutableList())
        for (actor in copy.results) {
            val fav = db.getFavActors().find { it.id == actor.id.toString() }
            if (fav != null) {
                actor.isFavorite = true
            }
        }
        return copy
    }
    suspend fun trendingActors(): ListActors {
        val actors = tmdbapi.getTrendingActors(api_key)
        return getActorsWithFav(actors)
    }
    suspend fun getActor(person_id: Int) = tmdbapi.getActor(person_id, api_key)
    suspend fun searchActors(query: String): ListActors {
        val actors = tmdbapi.searchActors(api_key, query)
        return getActorsWithFav(actors)
    }
    suspend fun addActorToFav(person_id: Int) = db.insertActor(ActeurEntity(tmdbapi.getActor(person_id, api_key), person_id.toString()))
    suspend fun removeActorFromFav(person_id: Int) = db.deleteActor(person_id.toString())
    suspend fun getFavActors() = getActorsWithFav(ListActors(0, db.getFavActors().map { it.fiche }.toMutableList()))
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providerConverters(): Converters {
        val moshi = Moshi.Builder().build()
        return Converters(moshi)
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context, converters: Converters)
            : TMDBDao =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "tmdb_fav"
        )
            .addTypeConverter(converters)
            .fallbackToDestructiveMigration()
            .build().tmdbDao()

    @Singleton
    @Provides
    fun provideTmdbApi(): TMDBApi =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(TMDBApi::class.java)


    @Singleton
    @Provides
    fun provideRepository(api: TMDBApi, db: TMDBDao) = Repository(api, db)
}
