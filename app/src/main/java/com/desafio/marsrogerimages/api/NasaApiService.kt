package com.desafio.marsrogerimages.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("rovers/curiosity/photos")
    suspend fun getCuriosityPhotos(
        @Query("page")page:Int=1,
        @Query("sol")sol:Int=1000,
        @Query("api_key")apiKey:String="We63CP7gfMgDMANOKloxa4xeDuZSYAov0xlYdxe5"
    ):CuriosityPhotosDTO

}
private fun getNasaClient(): Retrofit {
    return Retrofit.Builder()
        .client(OkHttpClient())
        .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
fun getNasaApiService():NasaApiService{
    return getNasaClient().create(NasaApiService::class.java)
}

