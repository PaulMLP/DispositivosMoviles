package com.programacion.dispositivosmoviles.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.programacion.dispositivosmoviles.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCapture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResult.launch(intent)
        }

        binding.imgCapture.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hola como tas")
            shareIntent.setType("text/plain")

            startActivity(
                Intent.createChooser(shareIntent, "Compartir")
            )
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private val cameraResult = registerForActivityResult(StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val image = result.data?.extras?.get("data") as Bitmap
                binding.imgCapture.setImageBitmap(image)
            }

            Activity.RESULT_CANCELED -> {}
        }
    }
}