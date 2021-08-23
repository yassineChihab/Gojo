package com.majjane.chefmajjane.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majjane.chefmajjane.DisplayAllCommandesAdapter
import com.majjane.chefmajjane.databinding.ItemRowLayoutCommandeBinding
import com.majjane.chefmajjane.responses.Article
import com.majjane.chefmajjane.responses.ArticleX

class DisplayAllCommandesAdapterX() :
    RecyclerView.Adapter<DisplayAllCommandesAdapterX.CommandesViewHolder>() {
    var items = mutableListOf<ArticleX>()

    @JvmName("setItems1")
    fun setItems(items: List<ArticleX>) {
        this.items = items as MutableList<ArticleX>
        notifyDataSetChanged()
    }

    class CommandesViewHolder(val binding: ItemRowLayoutCommandeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandesViewHolder {
        return CommandesViewHolder(
            ItemRowLayoutCommandeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommandesViewHolder, position: Int) {
        val article:ArticleX = items[position]
        holder.binding.apply {
            quantityTextView.text = article.quantity.toString() + "x"
            articleName.text = article.name
            articleDescription.text =article.description
            articlePrice.text = article.priceTTC.toString()+" MAD"
        }
    }

    override fun getItemCount(): Int = items.size
}