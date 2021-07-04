package com.mayur.chatbottask.di

import com.mayur.chatbottask.data.cache.AppDatabase
import com.mayur.chatbottask.data.network.ApiServices
import com.mayur.chatbottask.data.repositories.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesChatRepository(
        appDatabase: AppDatabase,
        authApiService: ApiServices
    ): ChatRepository {
        return ChatRepository(appDatabase, authApiService)
    }

}