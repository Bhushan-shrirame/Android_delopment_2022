package com.bhushanshrirame.cardviewrecycleview

import androidx.recyclerview.widget.RecyclerView
import com.bhushanshrirame.cardviewrecycleview.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListner: BookClickListner
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindBook(book: Book)
    {
        cardCellBinding.cover.setImageResource(book.cover)
        cardCellBinding.title.text =book.title
        cardCellBinding.author.text = book.author

        cardCellBinding.cardView.setOnClickListener{
            clickListner.OnClick(book)
        }
    }
}