package com.programacion.dispositivosmoviles.logic.marvelLogic

import android.util.Log
import com.programacion.dispositivosmoviles.data.connections.ApiConnection
import com.programacion.dispositivosmoviles.data.endpoints.MarvelEndPoint
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars

class MarvelCharactersLogic {

    suspend fun getCharactersMarvel(name: String, limit: Int): List<MarvelChars> {

        val response =
            ApiConnection.getService(
                ApiConnection.typeApi.Marvel,
                MarvelEndPoint::class.java
            ).getCharactersStarWith(name, limit)

        var itemList = arrayListOf<MarvelChars>()

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                var commic: String = "No available"
                if (it.comics.items.isNotEmpty()) {
                    commic = it.comics.items[0].name
                }
                val m = MarvelChars(
                    it.id,
                    it.name,
                    commic,
                    it.thumbnail.path + "." + it.thumbnail.extension
                )
                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.message())
        }
        return itemList
    }
}