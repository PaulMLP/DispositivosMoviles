package com.programacion.dispositivosmoviles.data.entities.marvel.characters.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val comic: String,
    val synopsis: String,
    val image: String
) : Parcelable

fun MarvelCharsDB.getMarvelChar(): MarvelChars {
    return MarvelChars(
        id,
        name,
        comic,
        synopsis,
        image
    )
}