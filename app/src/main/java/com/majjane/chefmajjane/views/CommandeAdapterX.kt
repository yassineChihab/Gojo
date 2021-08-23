package com.majjane.chefmajjane.views

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majjane.chefmajjane.databinding.ItemRowLayoutCommandeBinding
import com.majjane.chefmajjane.responses.Article
import com.majjane.chefmajjane.responses.ArticleX

class CommandeAdapterX :
    RecyclerView.Adapter<CommandeAdapterX.CommandeViewHolder>() {
    var items = mutableListOf<ArticleX>()
    private  val TAG = "CommandeAdapter"
    @JvmName("setItems1")
    fun setItems(items: List<ArticleX>) {
        this.items = items as MutableList<ArticleX>
        notifyDataSetChanged()
    }

    class CommandeViewHolder(val binding: ItemRowLayoutCommandeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeViewHolder {
        return CommandeViewHolder(
            ItemRowLayoutCommandeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommandeViewHolder, position: Int) {
        val article = items[position]
        holder.binding.apply {
            quantityTextView.text = article.quantity.toString()+"x"
            articleName.text = article.name
           // articleDescription.text = article.description
            articlePrice.text = article.priceTTC.toString()+" MAD"
        }
    }

    override fun getItemCount():Int{
        return if(items.size > 3 ) 3 else items.size
    }
}
