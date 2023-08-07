package com.programacion.dispositivosmoviles.ui.viewmodels

import android.view.View
import androidx.appcompat.widget.ViewUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel : ViewModel() {
    val progressState = MutableLiveData<Int>()
    val items = MutableLiveData<List<MarvelChars>>()
    fun processBackground(value: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val state = View.VISIBLE
            progressState.postValue(state)
            delay(value)
            val sate1 = View.GONE
            progressState.postValue(sate1)
        }
    }

    suspend fun getMarvelChars(offet: Int, limit: Int) {
        progressState.postValue(View.VISIBLE)
        val newItems = MarvelLogic()
            .getAllMarvelChars(offet, limit)
        items.postValue(newItems)
        progressState.postValue(View.GONE)
    }
}