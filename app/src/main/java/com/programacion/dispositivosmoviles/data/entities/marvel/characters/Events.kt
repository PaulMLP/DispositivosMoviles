package com.programacion.dispositivosmoviles.data.entities.marvel.characters

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)