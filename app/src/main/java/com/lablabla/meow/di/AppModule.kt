package com.lablabla.meow.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.lablabla.meow.data.local.MeowDatabase
import com.lablabla.meow.data.remote.MeowApi
import com.lablabla.meow.data.repository.MeowApiImpl
import com.lablabla.meow.data.repository.MeowRepositoryImpl
import com.lablabla.meow.data.util.GsonParser
import com.lablabla.meow.data.util.JsonParser
import com.lablabla.meow.domain.repository.MeowRepository
import com.lablabla.meow.domain.use_case.GetUsers
import com.lablabla.meow.domain.use_case.SendMeow
import com.lablabla.meow.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJsonParser(): JsonParser {
        return GsonParser(Gson())
    }

    @Provides
    @Singleton
    fun provideMeowDatabase(
        app: Application
    ): MeowDatabase {
        return Room.databaseBuilder(
            app, MeowDatabase::class.java, "meow_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideMeowApi(): MeowApi {
        return MeowApiImpl()
    }

    @Provides
    @Singleton
    fun provideMeowRepository(
        api: MeowApi,
        db: MeowDatabase
    ): MeowRepository {
        return MeowRepositoryImpl(
            api, db.dao
        )
    }

    // Use cases
    @Provides
    @Singleton
    fun provideUseCases(repository: MeowRepository) : UseCases {
        return UseCases(
            getUsers = GetUsers(repository),
            sendMeow = SendMeow()
        )
    }
}