package com.bhushanshrirame.cardviewrecycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bhushanshrirame.cardviewrecycleview.databinding.CardCellBinding

class CardAdapter(
    private val books: List<Book>,
    private val clickListner: BookClickListner)
    : RecyclerView.Adapter<CardViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent,false)
        return CardViewHolder(binding , clickListner)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        holder.bindBook(books[position])
    }

    override fun getItemCount(): Int = books.size
}