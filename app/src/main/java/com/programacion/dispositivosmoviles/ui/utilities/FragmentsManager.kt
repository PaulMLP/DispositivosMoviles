package com.programacion.dispositivosmoviles.ui.utilities

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentsManager : AppCompatActivity() {

    fun replaceFragment(
        manager: FragmentManager,
        container: Int,
        fragment: Fragment
    ) {
        with (manager.beginTransaction()){
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun addFragment(
        manager: FragmentManager,
        container: Int,
        fragment: Fragment
    ) {
        with(manager.beginTransaction()) {
            add(container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}