package com.example.fashion.client

import android.telecom.Call
import com.example.fashion.response.account.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("produk")
    fun getProduk(): Call<ArrayList<ProdukResponse>>

    @FormUrlEncoded
    @POST("account")
    fun postLogin(
        @Field("username") username:String,
        @Field("password") password:String,
    ): Call<LoginResponse>
}
