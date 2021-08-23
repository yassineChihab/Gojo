package com.majjane.chefmajjane.repository

import com.majjane.chefmajjane.responses.login.Login
import com.majjane.chefmajjane.repository.base.BaseRepository
import com.majjane.chefmajjane.network.AuthApi
import com.majjane.chefmajjane.responses.BaseResponse
import com.majjane.chefmajjane.responses.Password
import com.majjane.chefmajjane.responses.SignUp
import com.majjane.chefmajjane.responses.login.VerifyOTP
import com.majjane.chefmajjane.responses.login.loginGoogle
import com.majjane.chefmajjane.utils.Resource


class AuthRepository(val api: AuthApi) : BaseRepository() {
    suspend fun postGoogleLogin(
        email: String,
        familyName: String,
        givenName: String,
        id_lang: Int
    ) = safeApiCall {
        api.postGoogleLogin(Login(id_lang, givenName, familyName, email, null))
    }

    suspend fun facebookLogin(accessToken: String)= safeApiCall { api.facebookLogin(accessToken) }


    suspend fun sendOTP(phoneNumber: String,code:String) = safeApiCall {
        api.sendOTP(phoneNumber,code)
    }

    suspend fun verifyOTP(phoneNumberArg: String,isoCode:String , code: Int) = safeApiCall {
        api.verifyOTP(VerifyOTP( phoneNumberArg,isoCode,code))
    }

    suspend fun signUp(signUp: SignUp) = safeApiCall {
        api.signUp(signUp)
    }

    suspend fun loginWithEmail(login: Login) = safeApiCall {
        api.loginWithEmail(login)
    }

    suspend fun updatePassword(password: Password) = safeApiCall {
        api.updatePassword(password)
    }

    suspend fun signUpWithPhone(signUp: SignUp) = safeApiCall {
        api.signUpWithPhone(signUp)
    }

}