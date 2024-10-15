package com.ar.moviesapp.di

import androidx.room.RoomDatabase
import com.ar.moviesapp.core.networkUtils.createHttpClient
import com.ar.moviesapp.data.local.db.AppDataBase
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.data.remote.apiImpl.MovieApiImpl
import com.ar.moviesapp.data.repositoryImpl.MovieRepositoryImpl
import com.ar.moviesapp.domain.repository.MovieRepository
import com.ar.moviesapp.presentation.screens.home.HomeViewModel
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

class DataModule {
    fun httpClientEngine(): HttpClientEngine = createHttpClientEngine()
}

expect fun createHttpClientEngine(): HttpClientEngine

expect class RoomBuilder {
    fun builder(): RoomDatabase.Builder<AppDataBase>
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDataBase>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): AppDataBase {
    return builder
        .setQueryCoroutineContext(dispatcher)
        .build()
}

val appModule = module{
    single {
        DataModule().httpClientEngine()
    }
    single {
        getRoomDatabase(get())
    }
    //HttpClient
    single { createHttpClient(get()) }

    singleOf(::MovieApiImpl).bind(MovieApi::class)
    singleOf(::MovieRepositoryImpl).bind(MovieRepository::class)

    viewModelOf(::HomeViewModel)

}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}
