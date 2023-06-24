package com.programacion.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.data.entities.MarvelChars
import com.programacion.dispositivosmoviles.databinding.ActivityDetailsMarvelItemBinding
import com.programacion.dispositivosmoviles.databinding.ActivitySecondBinding
import com.programacion.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
//        var name: String? = ""
//        intent.extras?.let {
//            name = it.getString("name")
//        }
//        if (!name.isNullOrEmpty()){
//            binding.txtName.text =name
//        }
        val item = intent.getParcelableExtra<MarvelChars>("name")
        if (item !== null){
            binding.txtName.text=item.name
            Picasso.get().load(item.image).into(binding.imgMarvel)
        }
    }
}