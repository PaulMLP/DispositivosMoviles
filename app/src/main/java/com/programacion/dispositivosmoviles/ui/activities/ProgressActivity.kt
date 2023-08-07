package com.programacion.dispositivosmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.programacion.dispositivosmoviles.databinding.ActivityProgressBinding
import com.programacion.dispositivosmoviles.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressBinding
    private val progressViewmodel by viewModels<ProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //LiveData
        progressViewmodel.progressState.observe(this) {
            binding.progressBar.visibility = it
        }

        progressViewmodel.items.observe(this) {
            Toast.makeText(this, it[0].name, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        // Listeners
        binding.btnProceso.setOnClickListener {
            progressViewmodel.processBackground(3000)
        }
        binding.btnProceso2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                progressViewmodel.getMarvelChars(0, 90)
            }
        }
    }
}