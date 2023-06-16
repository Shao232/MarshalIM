package com.marshal.https

import retrofit2.Call
import retrofit2.http.GET

interface IMService {

    @GET("/")
    fun getData(): Call<String>

}