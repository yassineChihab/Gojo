package com.majjane.chefmajjane.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.majjane.chefmajjane.responses.*
import com.majjane.chefmajjane.responses.cityresponse.City
import com.majjane.chefmajjane.views.MesCommandesFragment

class SharedViewModel: ViewModel() {

    val sharedTotalSum: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val address:MutableLiveData<Address> by lazy {
        MutableLiveData<Address>()
    }
    val commandList:MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>()
    }
    val commandListX:MutableLiveData<List<ArticleX>> by lazy {
        MutableLiveData<List<ArticleX>>()
    }

    val customorCommande:MutableLiveData<CommandeResponse> by lazy {
        MutableLiveData<CommandeResponse>()
    }

    val sharedCategory:MutableLiveData<AccueilResponseItem> by lazy {
        MutableLiveData<AccueilResponseItem>()
    }
    val selectedCity:MutableLiveData<City> by lazy {
        MutableLiveData<City>()
    }

    val selectedPaimentMethod:MutableLiveData<MesCommandesFragment.MethodPaiment> by lazy {
        MutableLiveData<MesCommandesFragment.MethodPaiment>()
    }
}
