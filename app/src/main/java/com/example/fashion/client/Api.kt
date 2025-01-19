package com.example.fashion.client

import android.telecom.Call
import com.example.fashion.response.account.LoginResponse
import com.example.fashion.response.produk.ProdukResponse
import com.example.fashion.response.produk.UserCartResponse
import com.example.fashion.response.produk.UserOrderResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("produkById")
    fun getProdukByIds(
        @Query("id") ids: String
    ): retrofit2.Call<ArrayList<ProdukResponse>>

    @GET("UserCart")
    fun getUserCart(
        @Query("id_user") userId: String
    ): retrofit2.Call<ArrayList<UserCartResponse>>

    @GET("UserOrder")
    fun getUserOrder(
        @Query("id_user") userId: String
    ): retrofit2.Call<ArrayList<UserOrderResponse>>

    @GET("produk")
    fun getProduk(
        @Query("kategori") category: String
    ): retrofit2.Call<ArrayList<ProdukResponse>>

    @FormUrlEncoded
    @POST("account")
    fun postLogin(
        @Field("username") username:String,
        @Field("password") password:String,
    ): retrofit2.Call<LoginResponse>
}
