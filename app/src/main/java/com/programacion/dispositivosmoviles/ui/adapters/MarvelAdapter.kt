package com.programacion.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class MarvelAdapter(
    private var items: List<MarvelChars>,
    private var fnClick: (MarvelChars) -> Unit //no devuelve nada
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    //var items: List<MarvelChars> = listOf()
    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(
            item: MarvelChars,
            fnClick: (MarvelChars) -> Unit
        ) {
            binding.comicName.text = item.name;
            binding.txtComic.text = item.comic;
            Picasso.get().load(item.image).into(binding.imageView)

            itemView.setOnClickListener {
                //Snackbar.make(binding.imgMarvel, item.name, Snackbar.LENGTH_SHORT).show()
                fnClick(item)
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateListAdapter(newitems: List<MarvelChars>) {
        this.items = this.items.plus(newitems)
        notifyDataSetChanged()
    }

    fun replaceListAdapter(newitems: List<MarvelChars>) {
        this.items = newitems
        notifyDataSetChanged()
    }

}