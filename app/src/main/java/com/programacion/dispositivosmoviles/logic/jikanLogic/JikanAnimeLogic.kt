package com.programacion.dispositivosmoviles.logic.jikanLogic

import com.programacion.dispositivosmoviles.data.connections.ApiConnection
import com.programacion.dispositivosmoviles.data.endpoints.JikanEndPoint
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes(): List<MarvelChars> {

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndPoint::class.java
        ).getAllAnimes()


        var itemList = arrayListOf<MarvelChars>()


        if (response.isSuccessful) {
            response.body()!!.data.forEach {
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.rating,
                    it.synopsis,
                    it.images.jpg.image_url
                )
                itemList.add(m)
            }
        }

        return itemList
    }
}