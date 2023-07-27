package com.driving_school.home

import com.marshal.base_common.MApplication
import com.marshal.base_common.baseview.BaseViewModel
import getDrivingSubjectFour
import getDrivingSubjectOne
import putDrivingSubjectFour
import putDrivingSubjectOne
import java.io.InputStream

class DrivingSchoolViewModel:BaseViewModel() {

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


}