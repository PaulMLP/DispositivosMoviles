package com.programacion.dispositivosmoviles.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class FragmentViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()

    suspend fun chargingData() {
        isLoading.postValue(true)
        delay(3000)
        isLoading.postValue(false)
    }
}