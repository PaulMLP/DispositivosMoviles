package com.programacion.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.snackbar.Snackbar
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationBinding

    private lateinit var client: SettingsClient
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGaranted ->
            when (isGaranted) {
                true -> {
                    client.checkLocationSettings(locationSettingsRequest).apply {
                        addOnSuccessListener {
                            val task = fusedLocationProviderClient.lastLocation
                            task.addOnSuccessListener { location ->

                                //Actualizando la ultima ubicación
                                fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    Looper.getMainLooper()
                                )
                            }
                        }
                        addOnFailureListener { ex ->
                            if (ex is ResolvableApiException) {
                                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                                ex.startResolutionForResult(
                                    this@LocationActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }
                        }
                    }

                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                    Snackbar.make(
                        binding.root,
                        "Ayude con el permiso",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }

                false -> {
                    Snackbar.make(binding.root, "Denegado", Snackbar.LENGTH_LONG).show()
                }
            }
        }

    private val appResultLocal =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultActivity ->
            var color: Int = getColor(R.color.black)
            var message = when (resultActivity.resultCode) {
                RESULT_OK -> {
                    color = getColor(R.color.green)
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                RESULT_CANCELED -> {
                    color = getColor(R.color.red)
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                else -> {
                    "NO TENGO IDEA"
                }
            }
            val sn = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            sn.setTextColor(getColor(R.color.black))
            sn.setBackgroundTint(color)
            sn.show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000
        )
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.locations.forEach { location ->
                    currentLocation = location
                    Log.d("UCE", "Ubicacion: ${location.latitude}, " + "${location.longitude}")

                    Snackbar.make(
                        binding.root,
                        "Ubicacion: ${location.latitude}, " + "${location.longitude}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        client = LocationServices.getSettingsClient(this)

        locationSettingsRequest = LocationSettingsRequest
            .Builder()
            .addLocationRequest(locationRequest)
            .build()

        binding.btnLocation.setOnClickListener {
            locationContract.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}