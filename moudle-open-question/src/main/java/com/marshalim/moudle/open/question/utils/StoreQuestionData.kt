/**
 * 设置app的日夜切换 true为白天 false为夜间
 */
// ============= 开大考题 start =======================================
const val appFunctionSingleDataKey: String = "app_function_single_data"
const val appFunctionMultipleDataKey: String = "app_function_multiple_data"
const val appFunctionEstimateDataKey: String = "app_function_Estimate_data"
const val appThoughtSingleDataKey: String = "app_thought_single_data"
const val appProgramSingleDataKey: String = "app_program_single_data"

const val appSecondEnglishQuestionKey: String = "app_second_english_question"

const val appThirdDatabaseKey: String = "app_third_database_question"
const val appThirdSoftwareKey: String = "app_third_software_question"

//英语 学位题目1到10
const val appThirdEnglishFirstKey: String = "app_third_english_first"
const val appThirdEnglishSecondKey: String = "app_third_english_second"
const val appThirdEnglishThirdKey: String = "app_third_english_third"
const val appThirdEnglishFourthKey: String = "app_third_english_fourth"
const val appThirdEnglishFifthKey: String = "app_third_english_fifth"
const val appThirdEnglishSixthKey: String = "app_third_english_sixth"
const val appThirdEnglishSeventhKey: String = "app_third_english_seventh"
const val appThirdEnglishEighthKey: String = "app_third_english_eighth"
const val appThirdEnglishNinthKey: String = "app_third_english_ninth"
const val appThirdEnglishTenthKey: String = "app_third_english_tenth"

// ============== 开大考题 end ======================================


fun putAppFunctionSingleData(value: String) {
    StoreManager.putData(appFunctionSingleDataKey, value)
}

fun getAppFunctionSingleData(): String? {
    return StoreManager.getData(appFunctionSingleDataKey, "")
}

fun putAppFunctionMultipleData(value: String) {
    StoreManager.putData(appFunctionMultipleDataKey, value)
}

fun getAppFunctionMultipleData(): String? {
    return StoreManager.getData(appFunctionMultipleDataKey, "")
}

fun putAppFunctionEstimateData(value: String) {
    StoreManager.putData(appFunctionEstimateDataKey, value)
}

fun getAppFunctionEstimateData(): String? {
    return StoreManager.getData(appFunctionEstimateDataKey, "")
}

fun putAppThoughtSingleData(value: String) {
    StoreManager.putData(appThoughtSingleDataKey, value)
}

fun getAppThoughtSingleData(): String? {
    return StoreManager.getData(appThoughtSingleDataKey, "")
}

fun putAppProgramSingleData(value: String) {
    StoreManager.putData(appProgramSingleDataKey, value)
}

fun getAppProgramSingleData(): String? {
    return StoreManager.getData(appProgramSingleDataKey, "")
}

//appSecondEnglishQuestionKey
fun putAppSecondEnglishQuestionData(value: String) {
    StoreManager.putData(appSecondEnglishQuestionKey, value)
}

fun getAppSecondEnglishQuestionData(): String? {
    return StoreManager.getData(appSecondEnglishQuestionKey, "")
}

fun putAppThirdDatabaseData(value: String) {
    StoreManager.putData(appThirdDatabaseKey, value)
}

fun getAppThirdDatabaseData(): String? {
    return StoreManager.getData(appThirdDatabaseKey, "")
}

fun putAppThirdSoftwareData(value: String) {
    StoreManager.putData(appThirdSoftwareKey, value)
}

fun getAppThirdSoftwareData(): String? {
    return StoreManager.getData(appThirdSoftwareKey, "")
}

fun putAppThirdEnglishFirstData(value: String) {
    StoreManager.putData(appThirdEnglishFirstKey, value)
}

fun putAppThirdEnglishSecondData(value: String) {
    StoreManager.putData(appThirdEnglishSecondKey, value)
}

fun putAppThirdEnglishThirdData(value: String) {
    StoreManager.putData(appThirdEnglishThirdKey, value)
}

fun putAppThirdEnglishFourthData(value: String) {
    StoreManager.putData(appThirdEnglishFourthKey, value)
}

fun putAppThirdEnglishFifthData(value: String) {
    StoreManager.putData(appThirdEnglishFifthKey, value)
}

fun putAppThirdEnglishSixthData(value: String) {
    StoreManager.putData(appThirdEnglishSixthKey, value)
}

fun putAppThirdEnglishSeventhData(value: String) {
    StoreManager.putData(appThirdEnglishSeventhKey, value)
}

fun putAppThirdEnglishEighthData(value: String) {
    StoreManager.putData(appThirdEnglishEighthKey, value)
}

fun putAppThirdEnglishNinthData(value: String) {
    StoreManager.putData(appThirdEnglishNinthKey, value)
}

fun putAppThirdEnglishTenthData(value: String) {
    StoreManager.putData(appThirdEnglishTenthKey, value)
}

fun getAppThirdEnglishFirstData(): String? {
    return StoreManager.getData(appThirdEnglishFirstKey, "")
}

fun getAppThirdEnglishSecondData(): String? {
    return StoreManager.getData(appThirdEnglishSecondKey, "")
}

fun getAppThirdEnglishThirdData(): String? {
    return StoreManager.getData(appThirdEnglishThirdKey, "")
}

fun getAppThirdEnglishFourthData(): String? {
    return StoreManager.getData(appThirdEnglishFourthKey, "")
}

fun getAppThirdEnglishFifthData(): String? {
    return StoreManager.getData(appThirdEnglishFifthKey, "")
}

fun getAppThirdEnglishSixthData(): String? {
    return StoreManager.getData(appThirdEnglishSixthKey, "")
}

fun getAppThirdEnglishSeventhData(): String? {
    return StoreManager.getData(appThirdEnglishSeventhKey, "")
}

fun getAppThirdEnglishEighthData(): String? {
    return StoreManager.getData(appThirdEnglishEighthKey, "")
}

fun getAppThirdEnglishNinthData(): String? {
    return StoreManager.getData(appThirdEnglishNinthKey, "")
}

fun getAppThirdEnglishTenthData(): String? {
    return StoreManager.getData(appThirdEnglishTenthKey, "")
}


