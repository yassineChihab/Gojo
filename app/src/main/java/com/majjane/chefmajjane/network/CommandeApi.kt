package com.majjane.chefmajjane.network

import com.majjane.chefmajjane.responses.*
import com.majjane.chefmajjane.responses.cityresponse.CityDeliveryPriceResponse
import com.majjane.chefmajjane.responses.model.CommandeModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommandeApi {

    @GET("getVilles.php")
    suspend fun getCitiesList(@Query("id_lang")idLang: Int, @Query("id_customer")idCustomer: Int) :CityDeliveryPriceResponse

    @POST("Commander.php")
    suspend fun sendCommandeToApi(@Body commandeModel: CommandeModel) : BaseResponse

    @GET("getOrdersByCustomer.php")
    suspend fun getCustomerOrder(
        @Query("id_lang") idLang:Int,
        @Query("id_customer") idCustomer:Int
    ):CommandeResponseNew

    @GET("orderDetail.php")
    suspend fun orderDetail(
        @Query("id_order") idOrder:Int,
        @Query("id_customer") idCustomer:Int,
        @Query("id_lang") idLang:Int
    ): OrederDetailResponseNewItem
}