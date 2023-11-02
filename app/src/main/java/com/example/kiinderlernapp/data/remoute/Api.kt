package com.example.kiinderlernapp.data.remoute

import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.datamodels.cats.Cat
import com.example.kiinderlernapp.data.datamodels.cats.Cats
import com.example.kiinderlernapp.data.datamodels.dog.Dogs
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Basis-URLs für die Dog- und Cat-APIs
const val DOG_URL = "https://dog.ceo/"
const val CAT_URL = "https://api.thecatapi.com/"

// Erstelle eine Moshi-Instanz für die JSON-Serialisierung und -Deserialisierung
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Erstelle ein Retrofit-Objekt für die Dog-API
private val dogRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(DOG_URL)
    .build()

// Erstelle ein Retrofit-Objekt für die Cat-API
private val catRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(CAT_URL)
    .build()

// Definiere eine Schnittstelle (Interface) für die Dog-API-Endpunkte
interface DogApiService {

    // GET-Anfrage zum Abrufen von zufälligen Hunde-Bildern
    @GET("api/breeds/image/random")
    suspend fun getDogs(): Dogs
}

// Definiere eine Schnittstelle (Interface) für die Cat-API-Endpunkte
interface CatApiService {

    // GET-Anfrage zum Abrufen von Katzenbildern (limitiert auf 50 Bilder)
    @GET("v1/images/search?limit=50")
    suspend fun getCats(): List<Cat>
}

// Erstelle ein Singleton-Objekt für die Dog-API
object DogApi {
    val retrofitService: DogApiService by lazy { dogRetrofit.create(DogApiService::class.java) }
}

// Erstelle ein Singleton-Objekt für die Cat-API
object CatApi {
    val retrofitService: CatApiService by lazy { catRetrofit.create(CatApiService::class.java)}
}
