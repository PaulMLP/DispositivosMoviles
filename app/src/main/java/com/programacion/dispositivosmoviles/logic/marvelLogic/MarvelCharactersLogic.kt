package com.programacion.dispositivosmoviles.logic.marvelLogic

import android.util.Log
import com.programacion.dispositivosmoviles.data.connections.ApiConnection
import com.programacion.dispositivosmoviles.data.endpoints.MarvelEndPoint
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars

class MarvelCharactersLogic {
    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndPoint::class.java
        ).getCharactersStartWith(name, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {

                var comic: String = "No available"

                if (it.comics.items.size > 0) {
                    comic = it.comics.items[0].name
                }

                val m = MarvelChars(
                    it.id,
                    it.name,
                    comic,
                    it.description,
                    it.thumbnail.path + "." + it.thumbnail.extension
                )
                Log.d("UCE", response.toString())

                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.toString())
        }

        return itemList
    }
}