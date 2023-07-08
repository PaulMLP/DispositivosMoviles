package com.programacion.dispositivosmoviles.logic.list

import com.programacion.dispositivosmoviles.data.marvel.MarvelChars

class ListItems {
    fun returnMarvelChars(): List<MarvelChars> {
        return listOf(
            MarvelChars(
                1,
                "Colossus",
                "X-men 19",
                "",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11130/111300304/5810917-image.jpeg"
            ),
            MarvelChars(
                2,
                "Bucky Barnes",
                "Captain America",
                "",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8720915-ezgif-5-f0ea08d58a.jpg"
            ),
            MarvelChars(
                3,
                "Doctor Strange",
                "The Defenders",
                "",
                "https://comicvine.gamespot.com/a/uploads/scale_small/6/68065/8768812-8651456135-docto.jpg"
            ),
            MarvelChars(
                4,
                "Storm",
                "Gli Incredibili X-Men",
                "",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8795953-gsh.jpg"
            ),
            MarvelChars(
                5,
                "Jean Grey",
                "Gli Incredibili X-Men",
                "",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8795953-gsh.jpg"
            )
        )
    }
}