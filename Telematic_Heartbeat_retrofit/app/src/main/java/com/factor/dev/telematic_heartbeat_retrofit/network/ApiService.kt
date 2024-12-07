package com.factor.dev.telematic_heartbeat_retrofit.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("heartbeat/")
    fun sendTelematicsData(@Body data: RequestBody): Call<ResponseBody>
}