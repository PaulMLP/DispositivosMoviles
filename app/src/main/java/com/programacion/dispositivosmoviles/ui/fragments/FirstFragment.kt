package com.programacion.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.databinding.FragmentFirstBinding
import com.programacion.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic
import com.programacion.dispositivosmoviles.logic.list.ListItems
import com.programacion.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.programacion.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(
            layoutInflater, container,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val names = arrayListOf<String>(
            "Andres", "Fabio", "Mario", "Bob", "Penelope"
        )
        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_layout,
            names
        )

        chargeDataRV()

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
        }
        //binding.listwiew1.adapter = adapter

    }

    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun chargeDataRV() {
        lifecycleScope.launch(Dispatchers.IO) {
            val rvAdapter = MarvelAdapter(
                JikanAnimeLogic().getAllAnimes()
            ) { sendMarvelItem(it) }
            withContext(Dispatchers.Main) {
                with(binding.rvMarvelChars) {
                    this.adapter = rvAdapter
                    this.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                }
            }
        }
//        val rvAdapter = MarvelAdapter(ListItems().returnMarvelChars()) { sendMarvelItem(it) }
//        val rvMarvel = binding.rvMarvelChars
//        //(JikanAnimeLogic().getAllAnimes())
//        rvMarvel.adapter = rvAdapter
//        rvMarvel.layoutManager = LinearLayoutManager(
//            requireActivity(),
//            LinearLayoutManager.VERTICAL,
//            false
//        )
//    }
    }
}