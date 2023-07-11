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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.databinding.FragmentFirstBinding
import com.programacion.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.programacion.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.programacion.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding;
    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }
    private lateinit var lmanager: LinearLayoutManager
    private var page = 1
    private lateinit var gManager: GridLayoutManager
    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false
        )


        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(requireActivity(), 2)
        return binding.root

    }

    override fun onStart() {
        super.onStart();


        val names = arrayListOf<String>("A", "B", "C", "D", "E")

        val adapter1 = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            names
        )

        binding.spinner.adapter = adapter1
        chargeDataRVDB(5)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVDB(5)
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
                                    MarvelLogic().getAllMarvelChars(0, 99)
                                    //MarvelLogic().getMarvelChars(name = "spider", page * 3)
                                    //JikanAnimeLogic().getAllAnimes()
                                }
                                rvAdapter.updateListAdapter((x))

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
        //Intent(contexto de la activity, .class de la activity)
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("item", item)//mandamos los items a la otra activity
        startActivity(i)
    }

    fun chargeDataRVAPI(pos: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            //rvAdapter.items = JikanAnimeLogic().getAllAnimes()
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelChars(0, 99))
            }

            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter;
                this.layoutManager = gManager;
                //lmanager.scrollToPositionWithOffset(pos, 10)
            }
        }
        page++
    }

    fun chargeDataRVDB(pos: Int) {

        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharsItems = withContext(Dispatchers.IO) {

                var items = MarvelLogic().getAllMarvelCharDB().toMutableList()

                if (items.isEmpty()) {
                    items = (MarvelLogic().getAllMarvelChars(
                        0, page * 3
                    ))


                    MarvelLogic().insertMarvelCharstoDB(items)

                }
                return@withContext items
            }

        }
        /*withContext(Dispatchers.IO){
        }
        */
        rvAdapter.items = marvelCharsItems

        binding.rvMarvelChars.apply {
            this.adapter = rvAdapter;
            this.layoutManager = gManager;


            gManager.scrollToPositionWithOffset(pos, 10)
        }

        page++
    }

}

