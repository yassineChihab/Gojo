package com.majjane.chefmajjane.repository

import com.majjane.chefmajjane.network.ReclamationApi
import com.majjane.chefmajjane.repository.base.BaseRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ReclamationRepository(private val api: ReclamationApi) : BaseRepository() {
    suspend fun uploadReclamation(
        image: MultipartBody.Part,
        comment: RequestBody,
        numCom: RequestBody,
        `object`: RequestBody,
        id_customer: RequestBody,
        isJustification: RequestBody
    ) = safeApiCall {
        api.uploadReclamation(image,comment,numCom,`object`,id_customer,isJustification)
    }


}