package com.majjane.chefmajjane.responses

data class orderDetailResponseItem(
    val address: String,
    val articles: List<ArticleX>,
    val collecting_time: Int,
    val date: String,
    val num: String,
    val preparing_time: Int,
    val state: String,
    val total_paid: Int,
    val total_products: Int,
    val total_shipping: Int
)