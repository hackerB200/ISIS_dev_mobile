package com.example.pouet

import androidx.room.Database
import androidx.room.Entity
import androidx.room.ProvidedTypeConverter
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi

@Entity
data class FilmEntity(val fiche: Movie, @PrimaryKey val id: String)

@Entity
data class SerieEntity(val fiche: Serie, @PrimaryKey val id: String)

@Entity
data class ActeurEntity(val fiche: Actor, @PrimaryKey val id: String)

@ProvidedTypeConverter
class Converters(moshi: Moshi) {
    //MOVIES
    private val filmJsonadapter = moshi.adapter(Movie::class.java)

    @TypeConverter
    fun StringToTmdbMovie(value: String): Movie? {
        return filmJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbMovieToString(film: Movie): String {
        return filmJsonadapter.toJson(film)
    }

    //SERIES
    private val serieJsonadapter = moshi.adapter(Serie::class.java)

    @TypeConverter
    fun StringToTmdbSerie(value: String): Serie? {
        return serieJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbSerieToString(serie: Serie): String {
        return serieJsonadapter.toJson(serie)
    }

    //ACTORS
    private val actorJsonadapter = moshi.adapter(Actor::class.java)

    @TypeConverter
    fun StringToTmdbActor(value: String): Actor? {
        return actorJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbActorToString(actor: Actor): String {
        return actorJsonadapter.toJson(actor)
    }
}

@Database(entities = [FilmEntity::class, SerieEntity::class, ActeurEntity::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tmdbDao(): TMDBDao
}