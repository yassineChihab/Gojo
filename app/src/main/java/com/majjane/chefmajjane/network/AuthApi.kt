package com.majjane.chefmajjane.network

import com.majjane.chefmajjane.responses.*
import com.majjane.chefmajjane.responses.login.Login
import com.majjane.chefmajjane.responses.login.GoogleResponse
import com.majjane.chefmajjane.responses.login.VerifyOTP
import com.majjane.chefmajjane.responses.login.loginGoogle
import retrofit2.Response
import retrofit2.http.*

interface  AuthApi {


    @POST("gconnect.php")
    suspend fun postGoogleLogin(
        @Body login: Login
    ): GoogleResponse

    @GET("fconnect.php")
    suspend fun facebookLogin( @Query("token") token: String):BaseResponse

    @GET("sendOTP.php")
    suspend fun sendOTP(
        @Query("phone_number") phoneNumber: String,
        @Query("iso_code") Iso_Code:String
    ): BaseResponse

    @POST("verifyOTP.php")
    suspend fun verifyOTP(
       @Body verify:VerifyOTP
    ): BaseResponse

    @POST("signUp.php")
    suspend fun signUp(@Body signUp: SignUp): BaseResponse

    @POST("signUpWithPhone.php")
    suspend fun signUpWithPhone(@Body signUp: SignUp): BaseResponse


    @POST("login.php")
    suspend fun loginWithEmail(@Body login: Login): BaseResponse

    @POST("updatePassword.php")
    suspend fun updatePassword(@Body password: Password): BaseResponse


}