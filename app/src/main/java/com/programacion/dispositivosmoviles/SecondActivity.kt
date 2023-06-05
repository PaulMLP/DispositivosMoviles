package com.programacion.dispositivosmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.programacion.dispositivosmoviles.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        Log.d("UCE", "Loading...")

        var name = ""
        intent.extras.let {
            name = it?.getString("var1")!!
        }
        Log.d("UCE", "Hola ${name}")
        binding.txtWelcome.text = "Bienvenido " + name
        initClass()
    }

    private fun initClass() {
        binding.button4.setOnClickListener {
            var intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }
    }
}