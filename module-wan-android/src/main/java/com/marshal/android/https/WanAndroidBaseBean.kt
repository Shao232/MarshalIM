package com.marshal.android.https

 data class WanAndroidBaseBean<T>(
     val errorCode: Int? = 0,
     val errorMsg: String? = "",
     val data: T? = null
 ) {
}