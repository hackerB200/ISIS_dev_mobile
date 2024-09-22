package com.example.pouet

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    //MVOIES
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(@Query("api_key") api_key: String): ListMovies

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Query("api_key") api_key: String, @Path("movie_id") movie_id: Int): Movie
    //TODO: append_to_response=credits

    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") api_key: String, @Query("query") query: String): ListMovies

    //SERIES
    @GET("trending/tv/week")
    suspend fun getTrendingSeries(@Query("api_key") api_key: String): ListSeries

    @GET("tv/{tv_id}")
    suspend fun getSeries(@Query("api_key") api_key: String, @Path("tv_id") tv_id: Int): Serie

    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") api_key: String, @Query("query") query: String): ListSeries

    //ACTORS
    @GET("trending/person/week")
    suspend fun getTrendingActors(@Query("api_key") api_key: String): ListActors

    @GET("person/{person_id}")
    suspend fun getActor(@Query("api_key") api_key: String, @Path("person_id") person_id: Int): Actor

    @GET("search/person")
    suspend fun searchActors(@Query("api_key") api_key: String, @Query("query") query: String): ListActors
}