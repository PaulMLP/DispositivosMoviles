package com.programacion.dispositivosmoviles.data.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    enum class typeApi {
        Jikan, Marvel
    }

    private val API_JIKAN = "https://api.jikan.moe/v4/"
    private val API_MARVEL = "https://gateway.marvel.com/v1/public/"

    //haciendo la conexion con la API
    private fun getConnection(base: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun <T, E : Enum<E>> getService(api: E, service: Class<T>): T{
        var base = ""
        when (api.name) {
            typeApi.Jikan.name -> {
                base = API_JIKAN
            }

            typeApi.Marvel.name -> {
                base = API_MARVEL
            }
        }
        return getConnection(base).create(service)
    }
}