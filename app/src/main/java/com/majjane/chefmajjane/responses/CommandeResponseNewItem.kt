package com.majjane.chefmajjane.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommandeResponseNewItem(
    val date: String,
    val id: Int,
    val image: String,
    val name: String,
    val order_status: OrderStatus,
    val total: Int
): Parcelable