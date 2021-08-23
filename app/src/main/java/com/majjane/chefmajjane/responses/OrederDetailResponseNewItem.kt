package com.majjane.chefmajjane.responses

data class OrederDetailResponseNewItem(
    val address: String,
    val articles: List<ArticleX>,
    val date: String,
    val num: String,
    val order_status: OrderStatusX,
    val state: String,
    val total_paid: Int,
    val total_products: Int,
    val total_shipping: Int
)