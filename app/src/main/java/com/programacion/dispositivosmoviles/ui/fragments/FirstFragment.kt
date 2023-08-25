package com.programacion.dispositivosmoviles.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.programacion.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.programacion.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.programacion.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.programacion.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.programacion.dispositivosmoviles.ui.utilities.Metodos
import com.programacion.dispositivosmoviles.ui.viewmodels.FragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNREACHABLE_CODE")
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding;
    private var rvAdapter: MarvelAdapter = MarvelAdapter(
        { item -> sendMarvelItem(item) }
    ) { item -> saveMarvelItem(item) }

    private lateinit var lmanager: LinearLayoutManager

    private val limit = 99
    private var offset = 0

    private lateinit var gManager: GridLayoutManager
    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    private val firstFragmentViewModel by viewModels<FragmentViewModel>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false
        )


        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(requireActivity(), 2)


        firstFragmentViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.lyMain.visibility = View.GONE
                binding.lyMainCopia.visibility = View.VISIBLE
            } else {
                binding.lyMain.visibility = View.VISIBLE
                binding.lyMainCopia.visibility = View.GONE
            }
        }
        lifecycleScope.launch {
            firstFragmentViewModel.chargingData()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart();

        chargeDataRVInit(10, 0)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVAPI(limit = limit, offset = offset)
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
                                    MarvelLogic().getAllMarvelChars(offset, limit)
                                    //MarvelLogic().getMarvelChars(name = "spider", page * 3)
                                    //JikanAnimeLogic().getAllAnimes()
                                }
                                rvAdapter.updateListAdapter((x))
                                this@FirstFragment.offset += offset
                            }
                        }

                    }

                }
            })

        binding.txtFilter.addTextChangedListener { filteredText ->
            val newItems = marvelCharsItems.filter { items ->
                items.name.lowercase().contains(filteredText.toString().lowercase())
            }

            rvAdapter.replaceListAdapter(newItems)
        }

    }

    fun corrotine() {
        lifecycleScope.launch(Dispatchers.Main) {
            var name = "Paul"

            name = withContext(Dispatchers.IO)
            {
                name = "Leonardo"
                return@withContext name
            }

            binding.cardView.radius
        }
    }

    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("item", item)//mandamos los items a la otra activity
        startActivity(i)
    }

    fun saveMarvelItem(item: MarvelChars): Boolean {
        Log.d("AAAAAA", "ENTRO CHARGEDATA")
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                DispositivosMoviles
                    .getDBInstance()
                    .marvelDao()
                    .insertMarvelCharacter(listOf(item.getMarvelCharsDB()))
            }
        }
        return item != null
    }

    fun chargeDataRVAPI(offset: Int, limit: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            //rvAdapter.items = JikanAnimeLogic().getAllAnimes()
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))
            }

            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter;
                this.layoutManager = gManager;
            }
            this@FirstFragment.offset = offset + limit
        }
    }

    fun chargeDataRVInit(offset: Int, limit: Int) {
        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharsItems = withContext(Dispatchers.IO) {
                    return@withContext MarvelLogic().getInitChars(offset, limit)
                }
                rvAdapter.items = marvelCharsItems
                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter;
                    this.layoutManager = gManager;
                    gManager.scrollToPositionWithOffset(limit, offset)
                }
                this@FirstFragment.offset = offset + limit
            }
        }
    }
}

