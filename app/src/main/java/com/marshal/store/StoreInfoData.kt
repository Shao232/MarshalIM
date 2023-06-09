

/**
 * 设置app的日夜切换 true为白天 false为夜间
 */
const val appLightModeKey:String = "appLightMode"

fun putAppLightMode(value:Boolean){
    StoreManager.putData(appLightModeKey,value)
}

fun getAppLightMode():Boolean{
    return StoreManager.getData(appLightModeKey,true)
}