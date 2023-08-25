package com.programacion.dispositivosmoviles.ui.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.ActivityHomeBinding
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnComics.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        binding.btnNotification.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnLocation.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }

        binding.btnSpeech.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Di algo..."
            )
            speechToText.launch(intentSpeech)
        }
    }

    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            var color: Int = getColor(R.color.black)
            var message = " "
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    color = getColor(R.color.green)
                    message =
                        activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            ?.get(0).toString()
                    if (message.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, message)
                        startActivity(intent)
                    }
                }

                RESULT_CANCELED -> {
                    color = getColor(R.color.red)
                    message = "Proceso Cancelado"
                }

                else -> {
                    message = "Ocurrio un error"
                }

            }
            val sn = Snackbar.make(binding.btnComics, message, Snackbar.LENGTH_LONG)
            sn.setTextColor(getColor(R.color.black))
            sn.setBackgroundTint(color)
            sn.show()
        }
}