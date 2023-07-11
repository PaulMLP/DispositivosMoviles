package com.programacion.dispositivosmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.programacion.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB

@Dao
interface MarvelCharsDAO {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters(): List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id=:id")
    fun getOneCharacter(id: Int): MarvelCharsDB

    @Insert
    fun insertMarvelCharacter(ch: List<MarvelCharsDB>)
}