package com.programacion.dispositivosmoviles.data.endpoints

import com.programacion.dispositivosmoviles.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndPoint {
    @GET("top/anime")
    suspend fun getAllAnimes(): Response<JikanAnimeEntity> //data class
}