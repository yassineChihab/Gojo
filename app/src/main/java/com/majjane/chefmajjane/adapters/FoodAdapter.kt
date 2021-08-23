package com.majjane.chefmajjane.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.majjane.chefmajjane.databinding.ItemRowLayoutSushiBinding
import com.majjane.chefmajjane.responses.Article
import com.majjane.chefmajjane.views.customviews.CounterView


class SushiAdapter(
    val onClick: (Article, Int) -> Unit?,
    val onTotalPriceChangedListener: (Float, HashMap<Int, Article>) -> Unit?,
    val serviceAvailable: () -> Unit
) :
    RecyclerView.Adapter<SushiAdapter.MyViewHolder>() {
    var items = mutableListOf<Article>()

    @JvmName("setItems1")
    fun setItems(items: ArrayList<Article>) {
        val listOfId = ArrayList<Int>()
        for (id in items) {
            listOfId.add(id.id)
        }
        if (articleHashMap.size > 0) {
            articleHashMap.forEach { (articleId, article) ->
                if (listOfId.contains(articleId)) {
                    val item = items.find { it.id == articleId }
                    items[items.indexOf(item)] = article
                }
            }
        }
        this.items = items
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemRowLayoutSushiBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemRowLayoutSushiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val TAG = "SushiAdapter"
    var totalPrice = 0.0f
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = items[position]
        holder.binding.apply {
            foodQuantity.setModel(article)
            sushiTitle.text = article.name
            val price = article.prixTTC.toString() + " MAD"
            foodQuantity.setQuantity(article.selectedQuantity.toString())
            sushiPrice.text = price
            sushiDescription.text = article.description
            Glide.with(this.root)
                .load(article.image)
                .fitCenter()
                .into(sushiImageView)
            root.setOnClickListener {
                onClick(article, position)
            }
            foodQuantity.setQuantityChangedListener(object : CounterView.QuantityChangedListener {
                override fun onQuantityChanged(article: Article?) {
                    if (article != null) {
                        //Log.d(TAG, "onQuantityChanged: ${article.name} ${article.selectedQuantity}")
                        articleHashMap[article.id] = article
                        articleHashMap = articleHashMap.filterValues {
                            it.selectedQuantity > 0
                        } as HashMap<Int, Article>
                        //  Log.d(TAG, "onQuantityChanged: $articleHashMap")
                        val totalPrice = calculateSum(articleHashMap)
                        onTotalPriceChangedListener(
                            totalPrice,
                            articleHashMap
                        )
                    }
                }
            })
        }
    }

    private fun calculateSum(hashMap: HashMap<Int, Article>): Float {
        var sum = 0.0F
        hashMap.forEach { (articleId, article) ->
            // Log.d(TAG, "calculateSum: $articleId ${article.name} ${article.selectedQuantity}")
            sum += article.prixTTC.toFloat() * article.selectedQuantity
        }
        this.totalPrice = sum
        return sum
    }

    var articleHashMap = HashMap<Int, Article>()
    override fun getItemCount(): Int {
        return  items.size
    }
}