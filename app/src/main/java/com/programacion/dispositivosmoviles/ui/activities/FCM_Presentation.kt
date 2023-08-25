package com.programacion.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.programacion.dispositivosmoviles.R

class FCM_Presentation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fcm_presentation)
    }

    override fun onStart() {
        super.onStart()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TOKEN_FB", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            token.toString()
            Log.d("TOKEN_FB", token)
            Toast.makeText(baseContext,"El token es: $token", Toast.LENGTH_SHORT).show()
        })
    }
}