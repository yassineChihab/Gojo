package com.majjane.chefmajjane.viewmodel

import android.widget.ResourceCursorAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majjane.chefmajjane.repository.CommandeRepository
import com.majjane.chefmajjane.responses.*
import com.majjane.chefmajjane.responses.cityresponse.CityDeliveryPriceResponse
import com.majjane.chefmajjane.responses.model.CommandeModel
import com.majjane.chefmajjane.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.http.Query

class CommandeViewModel(val repository: CommandeRepository) : ViewModel() {

    val citiesListResponse: MutableLiveData<Resource<CityDeliveryPriceResponse>> = MutableLiveData()
    val confirmCommandResponse: MutableLiveData<Resource<BaseResponse>> = MutableLiveData()

    fun getCitiesList(idLang: Int, idCustomer: Int) = viewModelScope.launch {
        citiesListResponse.postValue(Resource.Loading())
        citiesListResponse.postValue(repository.getCitiesList(idLang, idCustomer))
    }

    fun sendCommandeToApi(commandeModel: CommandeModel) = viewModelScope.launch {
        confirmCommandResponse.postValue(Resource.Loading())
        confirmCommandResponse.postValue(repository.sendCommandeToApi(commandeModel))

    }
    val allCommandeResponse: MutableLiveData<Resource<CommandeResponseNew>> = MutableLiveData()
    val allCommandeLiveData:LiveData<Resource<CommandeResponseNew>> get() = allCommandeResponse
    fun getCustomerOrder(idLang:Int,idCustomer: Int)=viewModelScope.launch {
        allCommandeResponse.postValue(Resource.Loading())
        allCommandeResponse.postValue(repository.getCustomerOrder(idLang,idCustomer))
    }
    val orderDetailResponse: MutableLiveData<Resource<OrederDetailResponseNewItem>> = MutableLiveData()
    val orderDetailLivaData:LiveData<Resource<OrederDetailResponseNewItem>> get() = orderDetailResponse
    fun getOrderDetail(idOrder:Int,idCustomer:Int, idLang: Int)=viewModelScope.launch {
        orderDetailResponse.postValue(Resource.Loading())
        orderDetailResponse.postValue(repository.getOrderDetail(idOrder,idCustomer,idLang))
    }


}