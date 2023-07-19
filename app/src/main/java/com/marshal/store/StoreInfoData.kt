

/**
 * 设置app的日夜切换 true为白天 false为夜间
 */
const val appLightModeKey:String = "appLightMode"
const val appFunctionSingleDataKey:String = "app_function_single_data"
const val appFunctionMultipleDataKey:String = "app_function_multiple_data"
const val appFunctionEstimateDataKey:String = "app_function_Estimate_data"
const val appThoughtSingleDataKey:String = "app_thought_single_data"
const val appProgramSingleDataKey:String = "app_program_single_data"
const val appTestDataKey:String = "app_test_data"

fun putAppLightMode(value:Boolean){
    StoreManager.putData(appLightModeKey,value)
}

fun getAppLightMode():Boolean{
    return StoreManager.getData(appLightModeKey,true)
}

fun putAppFunctionSingleData(value:String){
    StoreManager.putData(appFunctionSingleDataKey,value)
}

fun getAppFunctionSingleData():String?{
    return StoreManager.getData(appFunctionSingleDataKey,"")
}

fun putAppFunctionMultipleData(value:String){
    StoreManager.putData(appFunctionMultipleDataKey,value)
}

fun getAppFunctionMultipleData():String?{
    return StoreManager.getData(appFunctionMultipleDataKey,"")
}

fun putAppFunctionEstimateData(value:String){
    StoreManager.putData(appFunctionEstimateDataKey,value)
}

fun getAppFunctionEstimateData():String?{
    return StoreManager.getData(appFunctionEstimateDataKey,"")
}

fun putAppThoughtSingleData(value:String){
    StoreManager.putData(appThoughtSingleDataKey,value)
}

fun getAppThoughtSingleData():String?{
    return StoreManager.getData(appThoughtSingleDataKey,"")
}

fun putAppProgramSingleData(value:String){
    StoreManager.putData(appProgramSingleDataKey,value)
}

fun getAppProgramSingleData():String?{
    return StoreManager.getData(appProgramSingleDataKey,"")
}

fun putAppTestData(value: String){
    StoreManager.putData(appTestDataKey,value)
}

fun getAppTestData():String? = StoreManager.getData(appTestDataKey,"")



