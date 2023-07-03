package com.programacion.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.databinding.FragmentFirstBinding
import com.programacion.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic
import com.programacion.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.programacion.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(
            layoutInflater, container,
            false
        )

        binding.filter.addTextChangedListener {
            binding.linearLayout.visibility = View.GONE
        }

        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val names = arrayListOf<Int>(
            1, 2, 3, 4, 5
        )
        val adapter = ArrayAdapter<Int>(
            requireActivity(),
            R.layout.simple_layout,
            names
        )

        chargeDataRV("cap")

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV("cap")
            binding.rvSwipe.isRefreshing = false
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val v = lmanager.childCount //cuantos han pasado
                        val p = lmanager.findFirstVisibleItemPosition() //posicion actual
                        val t = lmanager.itemCount //cuantos tengo en total

                        if ((v + p) >= t) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val newItems =
//                                    MarvelCharactersLogic().getCharactersMarvel(
//                                    "spider",
//                                    20
//                                )
                                    JikanAnimeLogic().getAllAnimes()
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItems(newItems)
                                }
                            }
                        }
                    }
                }
            })
        binding.spinner.adapter = adapter

    }

    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

//    fun corrotine() {
//        lifecycleScope.launch(Dispatchers.Main) {
//            var name = "Paul"
//            name = withContext(Dispatchers.IO) {
//                name = "Jairo"
//                return@withContext name
//            }
//        }
//    }

    fun chargeDataRV(search: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            rvAdapter.dataSet = JikanAnimeLogic().getAllAnimes()

            withContext(Dispatchers.Main) {
                with(binding.rvMarvelChars) {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }
            }
        }
    }


//    fun chargeDataRV() {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val rvAdapter = MarvelAdapter(
//                JikanAnimeLogic().getAllAnimes()
//            ) { sendMarvelItem(it) }
//            withContext(Dispatchers.Main) {
//                with(binding.rvMarvelChars) {
//                    this.adapter = rvAdapter
//                    this.layoutManager = LinearLayoutManager(
//                        requireActivity(),
//                        LinearLayoutManager.VERTICAL,
//                        false
//                    )
//                }
//            }
//        }
//    fun chargeDataRV(search: String) {
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