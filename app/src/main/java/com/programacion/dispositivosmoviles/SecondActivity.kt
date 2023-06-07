package com.programacion.dispositivosmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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

        var name = "USUARIO"
//        intent.extras.let {
//            name = it?.getString("var1")!!
//        }
//        Log.d("UCE", "Hola ${name}")
        binding.txtWelcome.text = "Bienvenido " + name
        initClass()
        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    var suma = 0
                    for (i in listOf(8,10,14)){
                        suma+=i
                    }
                    Snackbar.make(
                        binding.txtWelcome,
                        "La suma es ${suma}",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(getColor(R.color.snackbarColor))
                        .show()
                    true
                }
                R.id.item_2 -> {
                    var suma = 0
                    for (i in listOf(10,16,23)){
                        suma+=i
                    }
                    Snackbar.make(
                        binding.txtWelcome,
                        "La suma es ${suma}",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(getColor(R.color.snackbarColor))
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    private fun initClass() {
        binding.button4.setOnClickListener {
            var intent = Intent(
                this,
                MainActivity::class.java
            )
//            startActivity(intent)
        }
    }

}