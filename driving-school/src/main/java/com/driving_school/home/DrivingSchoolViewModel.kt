package com.driving_school.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.driving_school.bean.DrivingBean
import com.driving_school.getDrivingSubjectFour
import com.driving_school.getDrivingSubjectOne
import com.driving_school.getDrivingSubjectThree
import com.driving_school.putDrivingSubjectFour
import com.driving_school.putDrivingSubjectOne
import com.driving_school.putDrivingSubjectThree
import com.google.gson.Gson
import com.marshal.base_common.MApplication
import com.marshal.base_common.baseview.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream

class DrivingSchoolViewModel : BaseViewModel() {

    var drivingSubjectOneSize = 0
    var drivingSubjectFourSize = 0
    var drivingSubjectThreeSize = 0
    var drivingSubjectAllSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    /**
     * 初始化加载科目1，科目4,c3数据到缓存中
     */
    fun initSubjectData() {
        val subjectOne: Boolean
        val subjectFour: Boolean
        val subjectThree: Boolean
        if (getDrivingSubjectOne().isEmpty()) {
            val myInput: InputStream? =
                MApplication.getInstance().assets?.open("drivingSubject1.json")
            val stringBuilder = StringBuilder()
            myInput?.bufferedReader().use {
                val readText = it?.readText()
                stringBuilder.append(readText)
            }
            subjectOne = putDrivingSubjectOne(stringBuilder.toString())
            myInput?.close()
        }else {
            subjectOne = true
        }

        if (getDrivingSubjectFour().isEmpty()) {
            val myInput: InputStream? =
                MApplication.getInstance().assets?.open("drivingSubject4.json")
            val stringBuilder = StringBuilder()
            myInput?.bufferedReader().use {
                val readText = it?.readText()
                stringBuilder.append(readText)
            }
            subjectFour = putDrivingSubjectFour(stringBuilder.toString())
            myInput?.close()
        }else {
            subjectFour = true
        }

        if (getDrivingSubjectThree().isEmpty()) {
            val myInput: InputStream? =
                MApplication.getInstance().assets?.open("drivingSubject3.json")
            val stringBuilder = StringBuilder()
            myInput?.bufferedReader().use {
                val readText = it?.readText()
                stringBuilder.append(readText)
            }
            subjectThree = putDrivingSubjectThree(stringBuilder.toString())
            myInput?.close()
        }else {
            subjectThree = true
        }

        viewModelScope.launch {
            delay(100)
            val subjectLoad = subjectOne || subjectFour || subjectThree
            drivingSubjectAllSuccess.postValue(subjectLoad)
        }
    }

    fun iniSubjectOneData() {
        val subjectOneJson = getDrivingSubjectOne()
        if (subjectOneJson.isNotEmpty()) {
            val gson = Gson()
            val data = gson.fromJson(subjectOneJson, DrivingBean::class.java)
            val questionList = data.result
            drivingSubjectOneSize = questionList?.size ?: 0
        }
    }

    fun initSubjectFourData() {
        val subjectFourJson = getDrivingSubjectFour()
        if (subjectFourJson.isNotEmpty()) {
            val gson = Gson()
            val data = gson.fromJson(subjectFourJson, DrivingBean::class.java)
            val questionList = data.result
            drivingSubjectFourSize = questionList?.size ?: 0
        }
    }

    fun initSubjectThreeData() {
        val subjectThreeJson = getDrivingSubjectThree()
        if (subjectThreeJson.isNotEmpty()) {
            val gson = Gson()
            val data = gson.fromJson(subjectThreeJson, DrivingBean::class.java)
            val questionList = data.result
            drivingSubjectThreeSize = questionList?.size ?: 0
            questionList?.forEach {
                Log.d("TAG", "item three :${it}")
            }
        }
    }


}