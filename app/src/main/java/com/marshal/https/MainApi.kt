package com.marshal.https

import com.marshal.base_common.https.HttpRequestFactory
import com.marshal.pojo.TestData
import io.reactivex.Observable

object MainApi {

    private val imService:IMService? = HttpRequestFactory.getMarshalIMRequest()?.create(IMService::class.java)

    fun getData(): Observable<TestData>? {
        return imService?.getData()
    }

}