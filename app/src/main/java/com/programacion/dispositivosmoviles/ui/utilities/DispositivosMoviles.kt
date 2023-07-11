package com.programacion.dispositivosmoviles.ui.utilities

import android.app.Application
import androidx.room.Room
import com.programacion.dispositivosmoviles.data.connections.MarvelConnectionDB
import com.programacion.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB

class DispositivosMoviles : Application() {

    val nama_class: String = "Admin"

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB" //nombre base de datos internamente
        ).build()
    }

    companion object {
        private var db: MarvelConnectionDB? = null
        fun getDBInstance(): MarvelConnectionDB {
            return db!!
        }
    }
}