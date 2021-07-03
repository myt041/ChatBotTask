package com.mayur.chatbottask.di


import com.mayur.chatbottask.BuildConfig
import com.mayur.chatbottask.data.network.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

//    @Provides
//    @Singleton
//    fun providesSaleInstance(
//        gsonConverterFactory: Converter.Factory,
//        okHttpClient: OkHttpClient
//    ): SaleApiService {
//        return Retrofit.Builder()
//            .baseUrl(BuildConfig.SALES_SERVICE)
//            .client(okHttpClient)
//            .addConverterFactory(gsonConverterFactory)
//            .build().create(SaleApiService::class.java)
//    }

//    @Provides
//    @Singleton
//    fun providesSmsInstance(
//        gsonConverterFactory: Converter.Factory,
//        okHttpClient: OkHttpClient
//    ): SmsApiService {
//        return Retrofit.Builder()
//            .baseUrl(BuildConfig.MESSAGE_SERVICE)
//            .client(okHttpClient)
//            .addConverterFactory(gsonConverterFactory)
//            .build().create(SmsApiService::class.java)
//    }


}