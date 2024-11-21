package com.example.pouet

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

class Repository(val tmdbapi: TMDBApi) {

    var api_key = "741e74ad116edeaf99ff07418b68a085"

    //MOVIES
    suspend fun trendingMovies() = tmdbapi.getTrendingMovies(api_key)
    suspend fun getMovie(movie_id: Int) = tmdbapi.getMovie(movie_id, api_key, "credits")
    suspend fun searchMovies(query: String) = tmdbapi.searchMovies(api_key, query)

    //SERIES
    suspend fun trendingSeries() = tmdbapi.getTrendingSeries(api_key)
    suspend fun getSeries(serie_id: Int) = tmdbapi.getSeries(serie_id, api_key, "credits")
    suspend fun searchSeries(query: String) = tmdbapi.searchSeries(api_key, query)

    //ACTORS
    suspend fun trendingActors() = tmdbapi.getTrendingActors(api_key)
    suspend fun getActor(person_id: Int) = tmdbapi.getActor(person_id, api_key)
    suspend fun searchActors(query: String) = tmdbapi.searchActors(api_key, query)
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTmdbApi(): TMDBApi =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(TMDBApi::class.java)


    @Singleton
    @Provides
    fun provideRepository(api: TMDBApi) = Repository(api)
}
