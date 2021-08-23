package com.majjane.chefmajjane.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleX(
    val id: Int,
    val name: String,
    val priceTTC: Double,
    val quantity: Int,
    val description:String
):Parcelable