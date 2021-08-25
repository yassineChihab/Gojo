package com.majjane.chefmajjane.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.majjane.chefmajjane.databinding.ItemRowLayoutVosCommandeBinding
import com.majjane.chefmajjane.responses.*
import com.majjane.chefmajjane.utils.visible

class vosCommadeAdapter(
    val onClick: (CommandeResponseNewItem, Int) -> Unit?,
    val onClickV:(CommandeResponseNewItem,Int) ->Unit?,
    ): RecyclerView.Adapter<vosCommadeAdapter.CommandeViewHolder>() {
    var items = mutableListOf<CommandeResponseNewItem>()
    @JvmName("setItems1")
    fun setItems(items: CommandeResponseNew) {
        this.items = items as MutableList<CommandeResponseNewItem>
        notifyDataSetChanged()
    }
    class CommandeViewHolder(val binding: ItemRowLayoutVosCommandeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeViewHolder {
        return CommandeViewHolder(
            ItemRowLayoutVosCommandeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: vosCommadeAdapter.CommandeViewHolder, position: Int) {
        val commande = items[position]
        holder.binding.apply {
            commandeTitle.text = commande.name
            state.text=commande.order_status.name
            Glide.with(this.root)
                .load(commande.image)
                .fitCenter()
                .into(commandeImageView)
            commandePrice.text = commande.total.toString()+" MAD"

            if(commande.order_status.name.equals("Validé")){
                stateBtn.setOnClickListener{
                    onClickV(commande,position)
                }
                commandeImageView.setOnClickListener{
                    onClickV(commande,position)
                }
            }else{
                stateBtn.setOnClickListener{
                    onClick(commande,position)
                }
                commandeImageView.setOnClickListener{
                    onClick(commande,position)
                }
            }


        }
    }

    override fun getItemCount(): Int = items.size













}