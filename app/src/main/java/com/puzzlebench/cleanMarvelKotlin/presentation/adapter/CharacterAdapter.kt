package com.puzzlebench.cleanMarvelKotlin.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.puzzlebench.cleanMarvelKotlin.R
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.presentation.adapter.viewholder.CharactersAdapterViewHolder
import com.puzzlebench.cleanMarvelKotlin.presentation.extension.inflate
import com.puzzlebench.cleanMarvelKotlin.presentation.listener.CharacterListener
import kotlin.properties.Delegates


class CharacterAdapter(data: List<Character> = emptyList(), val listener: CharacterListener) : RecyclerView.Adapter<CharactersAdapterViewHolder>() {

    var data by Delegates.observable(data) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersAdapterViewHolder =
            CharactersAdapterViewHolder(parent.inflate(R.layout.character_cards_layout), listener)

    override fun onBindViewHolder(holder: CharactersAdapterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}