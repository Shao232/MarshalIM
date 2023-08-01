package com.driving_school

import StoreManager

const val drivingSubjectOneKey: String = "driving_subject_one"
const val drivingSubjectFourKey: String = "driving_subject_four"

// 收藏题目 错题集 顺序练习的当前位置 顺序练习时正确回答的数量和错误回答的数量
// 收藏和错题集不分科目
//收藏集
const val drivingCollectQuestionListKey: String = "driving_collect_question_list"
//错题集
const val drivingErrorQuestionListKey: String = "driving_error_question_list"
//回答正确集合
const val drivingCorrectQuestionListKey: String = "driving_correct_question_list"
//科目4错题集+回答正确集合
const val drivingErrorQuestionFourListKey:String = "driving_error_question_four_list"
const val drivingCorrectQuestionFourListKey:String = "driving_correct_question_four_list"
//练习时当前页面位置
const val drivingTestCurrentPositionKey: String = "driving_test_current_position"


fun putDrivingSubjectOne(value: String) {
    StoreManager.putData(drivingSubjectOneKey, value)
}

fun getDrivingSubjectOne(): String {
    return StoreManager.getData(drivingSubjectOneKey, "") ?: ""
}

fun putDrivingSubjectFour(value: String) {
    StoreManager.putData(drivingSubjectFourKey, value)
}

fun getDrivingSubjectFour(): String {
    return StoreManager.getData(drivingSubjectFourKey, "") ?: ""
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

fun putDrivingTestCurrentPosition(value: Int) {
    StoreManager.putData(drivingTestCurrentPositionKey, value)
}

fun getDrivingTestCurrentPosition(): Int {
    return StoreManager.getData(drivingTestCurrentPositionKey, 0)
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
