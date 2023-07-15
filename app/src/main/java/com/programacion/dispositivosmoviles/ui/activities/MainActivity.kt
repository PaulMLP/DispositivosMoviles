package com.programacion.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.ActivityMainBinding
import com.programacion.dispositivosmoviles.logic.validator.LoginValidator
import com.programacion.dispositivosmoviles.ui.utilities.DispositivosMoviles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(
            name = "settings"
        )

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()

        val db = DispositivosMoviles.getDBInstance()
    }

    private fun initClass() {

        binding.btnIngreso.setOnClickListener {
            val check = LoginValidator().checkLogin(
                binding.name.text.toString(),
                binding.pass.text.toString()
            )

            if (check) {

                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.name.text.toString())
                }

                val intent = Intent(
                    this,
                    SecondActivity::class.java
                )
                intent.putExtra(
                    "var1",
                    binding.name.text.toString()
                )
                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.labelRegistro,
                    "Usuario o contraseña invalida",
                    Snackbar.LENGTH_LONG
                )
                    .setTextColor(getColor(R.color.red))
                    .show()
            }
        }
    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("email")] = "uce@uce.edu.ec"
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}