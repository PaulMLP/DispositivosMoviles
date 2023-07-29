package com.programacion.dispositivosmoviles.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient

class MyLocationManager(var context: Context) {

    private lateinit var client: SettingsClient

    //Inyeccion de dependencias
    //var context : Context? = null

    private fun initVars() {
        if (context != null) {
            client = LocationServices.getSettingsClient(context!!)
        }
    }

    fun getUserLocation() {
        initVars()
    }
}