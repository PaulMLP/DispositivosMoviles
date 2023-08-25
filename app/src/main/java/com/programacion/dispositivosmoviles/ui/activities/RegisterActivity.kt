package com.programacion.dispositivosmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        binding.btnRegistro.setOnClickListener {
            registerFirebaseEmail(
                binding.name.text.toString(),
                binding.pass.text.toString()
            )
        }
    }

    private fun registerFirebaseEmail(email: String, password: String) {
        Log.d("UCE", email)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("UCE", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Snackbar.make(
                        binding.root,
                        "SE REGISTRO EXITOSAMENTE",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(getColor(R.color.greenOk))
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.w("UCE", "createUserWithEmail:failure", task.exception)
                    Snackbar.make(
                        binding.root,
                        "REGISTRO FALLIDO",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(getColor(R.color.red))
                        .show()
                }
            }
    }
}