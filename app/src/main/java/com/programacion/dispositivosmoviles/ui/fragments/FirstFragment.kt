package com.programacion.dispositivosmoviles.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val opciones = arrayListOf<String>("Opcion 1", "Opcion 2", "Opcion 3", "Opcion 4", "Opcion 5")
        val adapter =
            ArrayAdapter<String>(requireActivity(), R.layout.simple_layout, opciones)

        binding.spinner.adapter = adapter
        binding.list.adapter = adapter
    }
}