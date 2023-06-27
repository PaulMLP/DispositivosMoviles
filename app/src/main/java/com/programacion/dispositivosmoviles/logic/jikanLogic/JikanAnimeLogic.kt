package com.programacion.dispositivosmoviles.logic.jikanLogic

import com.programacion.dispositivosmoviles.data.connections.ApiConnection
import com.programacion.dispositivosmoviles.data.endpoints.JikanEndPoint
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes(): List<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()

        val response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndPoint::class.java
        ).getAllAnimes()

        if (response.isSuccessful) {
            response.body()!!.data.forEach {
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}

