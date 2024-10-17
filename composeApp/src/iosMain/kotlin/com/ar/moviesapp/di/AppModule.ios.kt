package com.ar.moviesapp.di

import androidx.room.RoomDatabase
import com.ar.moviesapp.core.utils.ScreenSize
import com.ar.moviesapp.data.local.db.AppDataBase
import io.ktor.client.engine.HttpClientEngine

actual fun createHttpClientEngine(): HttpClientEngine {
    TODO("Not yet implemented")
}

actual class RoomBuilder {
    actual fun builder(): RoomDatabase.Builder<AppDataBase> {
        TODO("Not yet implemented")
    }
}

actual fun getScreenSize(): ScreenSize {
    TODO("Not yet implemented")
}