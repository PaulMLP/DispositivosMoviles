package com.programacion.dispositivosmoviles.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.programacion.dispositivosmoviles.R
import com.programacion.dispositivosmoviles.data.marvel.MarvelChars
import com.programacion.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class MarvelAdapter(
    //private var items: List<MarvelChars>,
    private var fnClick: (MarvelChars) -> Unit,
    private var fnSave: (MarvelChars) -> Boolean
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    var items: List<MarvelChars> = listOf()

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(
            item: MarvelChars,
            fnClick: (MarvelChars) -> Unit,
            fnSave: (MarvelChars) -> Boolean
        ) {
            binding.comicName.text = item.name;
            binding.txtComic.text = item.comic;
            Picasso.get().load(item.image).into(binding.imageView)

            itemView.setOnClickListener {
                fnClick(item)
            }
            binding.btnSave.setOnContextClickListener {
                fnSave(item)
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

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick, fnSave)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateListAdapter(newitems: List<MarvelChars>) {
        this.items = this.items.plus(newitems)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceListAdapter(newitems: List<MarvelChars>) {
        this.items = newitems
        notifyDataSetChanged()
    }

}