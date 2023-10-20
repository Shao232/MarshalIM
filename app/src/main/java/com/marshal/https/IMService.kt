package com.marshal.https

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IMService {

    @GET("/web/index")
    fun getData(): Call<String>


}