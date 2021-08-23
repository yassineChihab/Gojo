package com.majjane.chefmajjane.responses

data class CommandeResponseItem(
    val date: String,
    val id: Int,
    val image: String,
    val name: String,
    val state: String,
    val total: Int
)