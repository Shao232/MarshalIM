package com.marshal.https

import com.marshal.pojo.TestData
import io.reactivex.Observable
import retrofit2.http.GET

interface IMService {

    @GET("/web/index")
    fun getData(): Observable<TestData>


}