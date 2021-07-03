package com.mayur.chatbottask.di

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
        authApiService: ApiServices
    ): ChatRepository {
        return ChatRepository(authApiService)
    }

}