package com.programacion.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts.*
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
import java.util.Locale
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
        binding.btnLoginTwitter.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                //Uri.parse("http://google.com.ec")
                //Uri.parse("geo: -0.2006288, -78.5786066")
                //Uri.parse("tel:012345678")
                Uri.parse("https://m.youtube.com/watch?v=5wJUMAU5O-8&pp=ygUWcGFvbG8gbGFkaW5vIHZvbHVtZW4gMg%3D%3D")
            )
            startActivity(intent)
        }

        val appResultLocal = registerForActivityResult(StartActivityForResult()) { resultActivity ->
            var color = R.color.grey
            val message = when (resultActivity.resultCode) {
                RESULT_OK -> {
                    // Snackbar.make(binding.imageView, "Resultado exitoso", Snackbar.LENGTH_LONG)
                    //    .show()
                    //Log.d("UCE", "Resultado exitoso")
                    color = R.color.greenOk
                    resultActivity.data?.getStringExtra("result")
                        .orEmpty()
                }

                RESULT_CANCELED -> {
                    color = R.color.red
                    resultActivity.data?.getStringExtra("result")
                        .orEmpty()
                    //Log.d("UCE", "Resultado fallido")
                }

                else -> {
                    ""
                    //Log.d("UCE", "Resultado dudoso")

                }

            }
            val sn = Snackbar.make(binding.imageView, message, Snackbar.LENGTH_LONG)
            sn.setTextColor(getColor(R.color.black))
            sn.setBackgroundTint(getColor(color))
            sn.show()

        }

        val speechToText = registerForActivityResult(StartActivityForResult()) { resultActivity ->
            var message = ""
            var color = R.color.grey
            when (resultActivity.resultCode) {
                RESULT_OK -> {
                    color = R.color.greenOk
                    message = resultActivity
                        .data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        .toString()
                    if (message.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH,
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
                    color = R.color.orange
                    message = "Proceso Cancelado"
                }

                else -> {
                    message = "Ocurrio un error"
                }
            }
            val sn = Snackbar.make(binding.imageView, message, Snackbar.LENGTH_LONG)
            sn.setTextColor(getColor(R.color.black))
            sn.setBackgroundTint(getColor(color))
            sn.show()
        }
        binding.btnLoginFacebook.setOnClickListener {
//            val intent = Intent(
//                Intent.ACTION_WEB_SEARCH
//            )
//            intent.setClassName(
//                "com.google.android.googlequicksearchbox",
//                "com.google.android.googlequicksearchbox.SearchActivity"
//            )
//            intent.putExtra(SearchManager.QUERY, "UCE")
//            //https://api.whatsapp.com/send?phone=593%20&text=

//            val resIntent = Intent(this, ResultActivity::class.java)
//            appResultLocal.launch(resIntent)

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
                "Di algo ..."
            )
            speechToText.launch(intentSpeech)
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