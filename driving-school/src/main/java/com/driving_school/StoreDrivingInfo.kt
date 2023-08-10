package com.driving_school

import StoreManager

//科目1 json数据全部缓存
const val drivingSubjectOneKey: String = "driving_subject_one"
//科目4 json数据全部缓存
const val drivingSubjectFourKey: String = "driving_subject_four"
//c3初练习题 json全部缓存
const val drivingSubjectThreeKey: String = "driving_subject_three"

// 收藏题目 错题集 顺序练习的当前位置 顺序练习时正确回答的数量和错误回答的数量
// 收藏和错题集
//收藏集不分科目
const val drivingCollectQuestionListKey: String = "driving_collect_question_list"
//科目1错题集
const val drivingErrorQuestionListKey: String = "driving_error_question_list"
//科目1回答正确集合
const val drivingCorrectQuestionListKey: String = "driving_correct_question_list"
//科目4错题集
const val drivingErrorQuestionFourListKey:String = "driving_error_question_four_list"
//科目4回答正确集合
const val drivingCorrectQuestionFourListKey:String = "driving_correct_question_four_list"
//c3 正确
const val drivingCorrectQuestionThreeListKey:String = "driving_correct_question_three_list"
//c3 错题集
const val drivingErrorQuestionThreeListKey:String = "driving_error_question_three_list"


fun putDrivingSubjectOne(value: String):Boolean {
    return StoreManager.putData(drivingSubjectOneKey, value)
}

fun getDrivingSubjectOne(): String {
    return StoreManager.getData(drivingSubjectOneKey, "") ?: ""
}

fun putDrivingSubjectFour(value: String):Boolean {
    return StoreManager.putData(drivingSubjectFourKey, value)
}

fun getDrivingSubjectFour(): String {
    return StoreManager.getData(drivingSubjectFourKey, "") ?: ""
}

fun putDrivingSubjectThree(value:String):Boolean {
    return StoreManager.putData(drivingSubjectThreeKey,value)
}

fun getDrivingSubjectThree():String{
    return StoreManager.getData(drivingSubjectThreeKey,"")?:""
}

fun putDrivingCollectQuestion(value: String) {
    StoreManager.putData(drivingCollectQuestionListKey, value)
}

fun getDrivingCollectQuestion(): String {
    return StoreManager.getData(drivingCollectQuestionListKey, "") ?: ""
}

fun putDrivingErrorQuestion(value: String) {
    StoreManager.putData(drivingErrorQuestionListKey, value)
}

fun getDrivingErrorQuestion(): String {
    return StoreManager.getData(drivingErrorQuestionListKey, "") ?: ""
}

fun putDrivingCorrectQuestion(value: String){
    StoreManager.putData(drivingCorrectQuestionListKey,value)
}

fun getDrivingCorrectQuestion():String{
    return StoreManager.getData(drivingCorrectQuestionListKey, "") ?: ""
}

fun putDrivingErrorQuestionFourList(value:String){
    StoreManager.putData(drivingErrorQuestionFourListKey,value)
}

fun getDrivingErrorQuestionFourList():String{
    return StoreManager.getData(drivingErrorQuestionFourListKey,"")?:""
}

fun putDrivingCorrectQuestionFourList(value:String){
    StoreManager.putData(drivingCorrectQuestionFourListKey,value)
}

fun getDrivingCorrectQuestionFourList():String{
    return StoreManager.getData(drivingCorrectQuestionFourListKey,"")?:""
}

fun putDrivingCorrectQuestionThreeList(value: String){
    StoreManager.putData(drivingCorrectQuestionThreeListKey,value)
}

fun getDrivingCorrectQuestionThreeList():String {
    return StoreManager.getData(drivingCorrectQuestionThreeListKey,"")?:""
}

fun putDrivingErrorQuestionThreeList(value: String){
    StoreManager.putData(drivingErrorQuestionThreeListKey,value)
}

fun getDrivingErrorQuestionThreeList():String {
    return StoreManager.getData(drivingErrorQuestionThreeListKey,"")?:""
}
