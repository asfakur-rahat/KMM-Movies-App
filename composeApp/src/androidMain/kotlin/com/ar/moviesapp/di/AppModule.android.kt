package com.ar.moviesapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ar.moviesapp.data.local.db.AppDataBase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun createHttpClientEngine(): HttpClientEngine  = OkHttp.create()

actual class RoomBuilder(
    private val context: Context
) {
    actual fun builder(): RoomDatabase.Builder<AppDataBase> {
        val context = context.applicationContext
        val dbFile = context.getDatabasePath("todos.db")

        return Room.databaseBuilder<AppDataBase>(
            context = context,
            name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver())
    }
}