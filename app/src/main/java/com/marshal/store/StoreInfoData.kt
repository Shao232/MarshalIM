
/**
 * 设置app的日夜切换 true为白天 false为夜间
 */
const val appLightModeKey:String = "appLightMode"
const val appTestDataKey:String = "app_test_data"


fun putAppLightMode(value:Boolean){
    StoreManager.putData(appLightModeKey,value)
}

fun getAppLightMode():Boolean{
    return StoreManager.getData(appLightModeKey,true)
}

fun putAppTestData(value: String){
    StoreManager.putData(appTestDataKey,value)
}

fun getAppTestData():String? = StoreManager.getData(appTestDataKey,"")


