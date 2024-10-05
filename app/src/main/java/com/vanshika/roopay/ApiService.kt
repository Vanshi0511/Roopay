package com.vanshika.roopay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recharge_api/recharge")
    fun getRechargeDetails(
        @Query("member_id") memberId: String,
        @Query("api_password") apiPassword: String,
        @Query("api_pin") apiPin: String,
        @Query("number") number: String
    ): Call<RechargeResponse>
}
