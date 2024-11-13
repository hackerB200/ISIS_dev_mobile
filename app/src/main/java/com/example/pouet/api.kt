package com.example.pouet

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    //MVOIES
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(@Query("api_key") api_key: String): ListMovies

    @GET("movie/{movie_id}")
    suspend fun getMovie( @Path("movie_id") movie_id: Int, @Query("api_key") api_key: String, @Query ("append_to_response") append_to_response:String ): Movie

    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") api_key: String, @Query("query") query: String): ListMovies

    //SERIES
    @GET("trending/tv/week")
    suspend fun getTrendingSeries(@Query("api_key") api_key: String): ListSeries

    @GET("tv/{tv_id}")
    suspend fun getSeries(@Path("tv_id") tv_id: Int, @Query("api_key") api_key: String, @Query ("append_to_response") append_to_response:String): Serie

    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") api_key: String, @Query("query") query: String): ListSeries

    //ACTORS
    @GET("trending/person/week")
    suspend fun getTrendingActors(@Query("api_key") api_key: String): ListActors

    @GET("person/{person_id}")
    suspend fun getActor(@Path("person_id") person_id: Int, @Query("api_key") api_key: String): Actor

    @GET("search/person")
    suspend fun searchActors(@Query("api_key") api_key: String, @Query("query") query: String): ListActors
}