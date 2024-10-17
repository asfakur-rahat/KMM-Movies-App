package com.ar.moviesapp.base

import android.app.Application
import com.ar.moviesapp.core.utils.ScreenSize
import com.ar.moviesapp.di.RoomBuilder
import com.ar.moviesapp.di.appModule
import com.ar.moviesapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class MovieApplication: Application() {
    private val androidModule = module {
        single{
            RoomBuilder(get()).builder()
        }
        singleOf(::ScreenSize)
    }
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MovieApplication)
            modules(appModule, androidModule)
        }
    }
}