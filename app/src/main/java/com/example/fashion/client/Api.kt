package com.example.fashion.client

import android.telecom.Call
import com.example.fashion.response.account.LoginResponse
import com.example.fashion.response.produk.ProdukResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("produk")
    fun getProduk(): retrofit2.Call<ArrayList<ProdukResponse>>

    @FormUrlEncoded
    @POST("account")
    fun postLogin(
        @Field("username") username:String,
        @Field("password") password:String,
    ): retrofit2.Call<LoginResponse>
}
