package com.ar.moviesapp.di

import androidx.room.RoomDatabase
import com.ar.moviesapp.core.networkUtils.createHttpClient
import com.ar.moviesapp.data.local.db.AppDataBase
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.data.remote.apiImpl.MovieApiImpl
import com.ar.moviesapp.data.remote.pageSource.MoviePagingSource
import com.ar.moviesapp.data.repositoryImpl.MovieRepositoryImpl
import com.ar.moviesapp.domain.repository.MovieRepository
import com.ar.moviesapp.presentation.screens.details.DetailsViewModel
import com.ar.moviesapp.presentation.screens.home.HomeViewModel
import com.ar.moviesapp.presentation.screens.more.MoreMovieViewModel
import com.ar.moviesapp.presentation.screens.search.SearchViewModel
import com.ar.moviesapp.presentation.screens.watchlist.WatchlistViewModel
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

    single {
        MoviePagingSource(get())
    }

    singleOf(::MovieApiImpl).bind(MovieApi::class)
    singleOf(::MovieRepositoryImpl).bind(MovieRepository::class)

    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::WatchlistViewModel)
    viewModelOf(::MoreMovieViewModel)

}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}
