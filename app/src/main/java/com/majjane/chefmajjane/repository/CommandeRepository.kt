package com.majjane.chefmajjane.repository

import com.majjane.chefmajjane.network.CommandeApi
import com.majjane.chefmajjane.network.AccueilMenuApi
import com.majjane.chefmajjane.repository.base.BaseRepository
import com.majjane.chefmajjane.responses.model.CommandeModel
import com.majjane.chefmajjane.responses.CommandeResponse


class CommandeRepository(val api: CommandeApi) : BaseRepository() {
    suspend fun getCitiesList(idLang: Int, idCustomer: Int) = safeApiCall {
            api.getCitiesList(idLang,idCustomer)

    }

    suspend fun sendCommandeToApi(commandeModel: CommandeModel) =safeApiCall  {
            api.sendCommandeToApi(commandeModel)
    }
    suspend fun getCustomerOrder(idLang: Int,idCustomer: Int)=safeApiCall {
        api.getCustomerOrder(idLang,idCustomer)
    }
    suspend fun getOrderDetail(idOrder:Int,idCustomer:Int,idLang:Int)=safeApiCall {
        api.orderDetail(idOrder,idCustomer,idLang)
    }
}
