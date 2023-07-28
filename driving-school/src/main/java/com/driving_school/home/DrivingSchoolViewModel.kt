package com.driving_school.home

import com.driving_school.bean.DrivingBean
import com.google.gson.Gson
import com.marshal.base_common.MApplication
import com.marshal.base_common.baseview.BaseViewModel
import getDrivingSubjectFour
import getDrivingSubjectOne
import putDrivingSubjectFour
import putDrivingSubjectOne
import java.io.InputStream

class DrivingSchoolViewModel:BaseViewModel() {

    var drivingSubjectOneSize = 0
    var drivingSubjectFourSize = 0


    /**
     * 初始化加载科目1，科目4数据到缓存中
     */
    fun initSubjectData(){
        if(getDrivingSubjectOne().isEmpty()) {
            val myInput: InputStream? = MApplication.getInstance().assets?.open("drivingSubject1.json")
            val stringBuilder = StringBuilder()
            myInput?.bufferedReader().use {
                val readText = it?.readText()
                stringBuilder.append(readText)
            }
            putDrivingSubjectOne(stringBuilder.toString())
            myInput?.close()
        }

        if(getDrivingSubjectFour().isEmpty()) {
            val myInput: InputStream? = MApplication.getInstance().assets?.open("drivingSubject4.json")
            val stringBuilder = StringBuilder()
            myInput?.bufferedReader().use {
                val readText = it?.readText()
                stringBuilder.append(readText)
            }
            putDrivingSubjectFour(stringBuilder.toString())
            myInput?.close()
        }
    }

    fun iniSubjectOneData() {
        val subjectOneJson = getDrivingSubjectOne()
        val gson = Gson()
        val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
        val questionList = data.result
        drivingSubjectOneSize = questionList?.size?:0
    }

    fun initSubjectFourData(){
        val subjectOneJson = getDrivingSubjectFour()
        val gson = Gson()
        val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
        val questionList = data.result
        drivingSubjectFourSize = questionList?.size?:0
    }


}