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
    private var fnClick: (MarvelChars) -> Unit
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    var dataSet: List<MarvelChars> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(item: MarvelChars, fnClick: (MarvelChars) -> Unit) {
            binding.comicName.text = item.name
            binding.txtComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imageView)

            itemView
            binding.imageView.setOnClickListener {
                fnClick(item)
                // Snackbar.make(binding.imgMarvel, item.name, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters, parent, false))
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(dataSet[position], fnClick)
    }

    override fun getItemCount(): Int = dataSet.size
    fun updateListItems(newItems: List<MarvelChars>) {
        this.dataSet = this.dataSet.plus(newItems)
        notifyDataSetChanged()
    }

}