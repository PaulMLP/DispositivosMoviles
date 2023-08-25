package com.programacion.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.programacion.dispositivosmoviles.databinding.ActivityMainBinding
import com.programacion.dispositivosmoviles.ui.utilities.DispositivosMoviles
import java.util.UUID

val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(
            name = "settings"
        )

class MainActivity : AppCompatActivity() {

    //interfaz que nos permite acceder a la ubicación del usuario
    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("UCE", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, BiometricActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w("UCE", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        initClass()

        val db = DispositivosMoviles.getDBInstance()
    }

    @SuppressLint("MissingPermission")
    private fun initClass() {

        binding.btnIngreso.setOnClickListener {
            signInWithEmailAndPassword(
                binding.name.text.toString(),
                binding.pass.text.toString()
            )
        }

        binding.labelRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        binding.btnLoginTwitter.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/?lang=es")
            )
            startActivity(intent)
        }


        binding.btnLoginFacebook.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://es-la.facebook.com/")
            )
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

}