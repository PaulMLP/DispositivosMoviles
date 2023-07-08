package com.programacion.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.databinding.ActivityDetailsMarvelItemBinding
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
//        var name : String? = ""
//        intent.extras?.let {
//            name = it.getString("name")
//
//
//        }
//        if(!name.isNullOrEmpty()){
//            binding.txtName.text= name
//        }

        //aqui recibimos los items de MarvelChars, pero ahora los tomamos como si fueran metadata Jikan
        val item = intent.getParcelableExtra<MarvelChars>("item")

        if (item !== null){
            binding.txtName.text = item.name
            binding.marveltitle.text = item.comic
            Picasso.get().load(item.image).into(binding.imgImage)
            binding.txtDescription.text = item.synopsis

        }

    }
}