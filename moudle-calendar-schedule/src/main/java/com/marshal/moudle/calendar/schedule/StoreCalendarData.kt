

// ================Token====================================
const val appInfoTokenKey:String = "app_info_token"
// ================Token====================================

fun putAppInfoToken(value: String){
    StoreManager.putData(appInfoTokenKey,value)
}

fun getAppInfoToken():String? = StoreManager.getData(appInfoTokenKey,"")

