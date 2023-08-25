package com.programacion.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.data.marvel.getMarvelCharsDB
import com.programacion.dispositivosmoviles.databinding.FragmentFirstBinding
import com.programacion.dispositivosmoviles.databinding.FragmentSecondBinding
import com.programacion.dispositivosmoviles.databinding.FragmentThirdBinding
import com.programacion.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic
import com.programacion.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.programacion.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.programacion.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.programacion.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.programacion.dispositivosmoviles.ui.viewmodels.FragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    private var rvAdapter: MarvelAdapter = MarvelAdapter(
        { item -> sendMarvelItem(item) }
    ) { item -> saveMarvelItem(item) }
    private lateinit var lmanager: LinearLayoutManager
    private var page = 1
    private lateinit var gManager: GridLayoutManager

    private val thirdFragmentViewModel by viewModels<FragmentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(layoutInflater, container, false)

        binding = FragmentThirdBinding.inflate(
            layoutInflater, container, false
        )

        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(requireActivity(), 2)


        thirdFragmentViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.lyMain.visibility = View.GONE
                binding.lyMainCopia.visibility = View.VISIBLE
            } else {
                binding.lyMain.visibility = View.VISIBLE
                binding.lyMainCopia.visibility = View.GONE
            }
        }
        lifecycleScope.launch {
            thirdFragmentViewModel.chargingData()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        chargeDataRV(5)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV(5)
            binding.rvSwipe.isRefreshing = false
            lmanager.scrollToPositionWithOffset(5, 20)
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dx > 0) {
                        val v = lmanager.childCount//Cuantos han pasado
                        val p = lmanager.findFirstVisibleItemPosition()//Cual es mi posicion actual
                        val t = lmanager.itemCount//Cuantos tengo en total

                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.Main))
                            {
                                val x = with(Dispatchers.IO) {
                                    JikanAnimeLogic().getAllAnimes()
                                }
                                rvAdapter.updateListAdapter((x))
                            }
                        }
                    }
                }
            })
    }

    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("item", item)
        startActivity(i)
    }

    fun saveMarvelItem(item: MarvelChars): Boolean {
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                DispositivosMoviles
                    .getDBInstance()
                    .marvelDao()
                    .insertMarvelCharacter(listOf(item.getMarvelCharsDB()))
            }
        }
        return item !== null
    }

    fun chargeDataRV(pos: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            rvAdapter.items = JikanAnimeLogic().getAllAnimes()
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter;
                this.layoutManager = gManager;
            }
        }
        page++
    }
}