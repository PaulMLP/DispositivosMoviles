package com.programacion.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnResultOk.setOnClickListener {
            val i = Intent()
            i.putExtra("result", "Resultado exitoso")
            setResult(RESULT_OK, i)
            finish()
        }

        binding.btnResultFalse.setOnClickListener {
            val i = Intent()
            i.putExtra("result", "Resultado fallido")
            setResult(RESULT_CANCELED, i)
            finish()
        }
    }
}