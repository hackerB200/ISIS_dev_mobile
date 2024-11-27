package com.example.pouet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TMDBDao {

    //MOVIES
    @Query("SELECT * FROM filmentity")
    suspend fun getFavFilms(): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Query("DELETE FROM filmentity WHERE id = :id")
    suspend fun deleteFilm(id: String)

    //SERIES
    @Query("SELECT * FROM serieentity")
    suspend fun getFavSeries(): List<SerieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: SerieEntity)

    @Query("DELETE FROM serieentity WHERE id = :id")
    suspend fun deleteSerie(id: String)

    //ACTORS
    @Query("SELECT * FROM acteurentity")
    suspend fun getFavActors(): List<ActeurEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActeurEntity)

    @Query("DELETE FROM acteurentity WHERE id = :id")
    suspend fun deleteActor(id: String)
}