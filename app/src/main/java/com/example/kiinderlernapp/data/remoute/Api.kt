package com.example.kiinderlernapp.data.remoute

import android.os.Message
import com.example.kiinderlernapp.data.datamodels.dog.Dogs
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val DOG_URL = "https://dog.ceo/"
const val CAT_URL = "https://second.api.base.url/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val dogRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(DOG_URL)
    .build()

private val catRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(CAT_URL)
    .build()

interface DogApiService {

    @GET("api/breeds/image/random")
    suspend fun getDogs(): Dogs
}

interface CatApiService {

    @GET("images/search?limit=100")
    suspend fun getCats(): com.example.kiinderlernapp.data.datamodels.cat.Response
}

object DogApi {
    val retrofitService: DogApiService by lazy { dogRetrofit.create(DogApiService::class.java) }
}

object CatApi {
    val retrofitService: CatApiService by lazy { catRetrofit.create(CatApiService::class.java)}
}