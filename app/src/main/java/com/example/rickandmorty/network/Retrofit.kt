package com.example.rickandmorty.network

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.time.Duration

object Retrofit {

    private val timeout = Duration.ofSeconds(10)
    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .callTimeout(timeout)
        .connectTimeout(timeout)
        .readTimeout(timeout)
        .writeTimeout(timeout)
        .addInterceptor { chain: Interceptor.Chain ->
            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }
        .build()

    private fun <T> createService(service: Class<T>): T =
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(client)
            .addConverterFactory(Json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()))
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
            .create(service)

    val rickAndMortyApi = createService(RickAndMortyApi::class.java)
}