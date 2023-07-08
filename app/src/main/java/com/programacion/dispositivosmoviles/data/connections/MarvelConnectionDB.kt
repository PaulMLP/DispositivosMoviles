package com.programacion.dispositivosmoviles.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.programacion.dispositivosmoviles.data.dao.marvel.MarvelCharsDAO
import com.programacion.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB

@Database(
    entities = [MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConnectionDB : RoomDatabase() {

    abstract fun marvelDao(): MarvelCharsDAO
}