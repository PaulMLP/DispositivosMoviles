package com.programacion.dispositivosmoviles.logic.marvelLogic

import android.util.Log
import com.programacion.dispositivosmoviles.data.connections.ApiConnection
import com.programacion.dispositivosmoviles.data.endpoints.MarvelEndPoint
import com.programacion.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import com.programacion.dispositivosmoviles.data.entities.marvel.characters.database.getMarvelChar
import com.programacion.dispositivosmoviles.data.entities.marvel.characters.getMarvelChars
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.data.marvel.getMarvelCharsDB
import com.programacion.dispositivosmoviles.ui.utilities.DispositivosMoviles
import java.lang.Exception
import java.lang.RuntimeException

class MarvelLogic {

    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {

        val itemList = arrayListOf<MarvelChars>()
        val response = ApiConnection.getService(
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

    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {

        val itemList = arrayListOf<MarvelChars>()

        val response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndPoint::class.java
        ).getAllCharacters(offset, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.toString())
        }

        return itemList
    }

    suspend fun getAllMarvelCharDB(): List<MarvelChars> {
        val items: ArrayList<MarvelChars> = arrayListOf()
        val items_aux = DispositivosMoviles.getDBInstance().marvelDao().getAllCharacters()
        items_aux.forEach {
            items.add(
                it.getMarvelChar()
            )
        }
        return items
    }

    suspend fun getInitChars(offset: Int, limit: Int): MutableList<MarvelChars> {
        var items = mutableListOf<MarvelChars>()
        try {
            items = getAllMarvelCharDB()
                .toMutableList()
            if (items.isEmpty()) {
                items = (getAllMarvelChars(
                    offset, limit
                ))
                insertMarvelCharstoDB(items)
            }
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
        return items
    }

    suspend fun insertMarvelCharstoDB(items: List<MarvelChars>) {
        val itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }
        DispositivosMoviles
            .getDBInstance()
            .marvelDao()
            .insertMarvelCharacter(itemsDB)
    }
}