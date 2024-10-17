package com.ar.moviesapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ar.moviesapp.data.local.dao.MovieDao
import com.ar.moviesapp.data.local.dto.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}