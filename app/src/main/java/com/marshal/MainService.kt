package com.marshal

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.mine.open.works.OpenReadQuestionProgramDesignWork
import com.marshal.mine.open.works.OpenReadQuestionThoughtWork
import com.marshal.mine.open.works.OpenReadQuestionsWork
import com.marshal.pojo.OpenAnswersBean
import com.marshal.sharedata.CommitShareData
import getAppFunctionEstimateData
import getAppFunctionMultipleData
import getAppFunctionSingleData
import getAppProgramSingleData
import getAppThoughtSingleData

class MainService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG","service:onCreate")

        if (getAppFunctionSingleData()?.isEmpty() == true
            || getAppFunctionMultipleData()?.isEmpty() == true
            || getAppFunctionEstimateData()?.isEmpty() == true
        ){
            Thread(OpenReadQuestionsWork()).start()
        }else {
            val appFunctionSingleData = getAppFunctionSingleData()
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            CommitShareData.appSingleQuestions = Gson().fromJson(appFunctionSingleData,type)
            Log.d("TAG","save appfunction first :${CommitShareData.appSingleQuestions[0]}")

            val appFunctionMultipleData = getAppFunctionMultipleData()
            val type2 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            CommitShareData.appMultipleQuestions = Gson().fromJson(appFunctionMultipleData,type2)
            Log.d("TAG","save appfunction second :${CommitShareData.appMultipleQuestions[0]}")

            val appFunctionEstimateData = getAppFunctionEstimateData()
            val type3 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            CommitShareData.appEstimateQuestions = Gson().fromJson(appFunctionEstimateData,type3)
            Log.d("TAG","save appfunction third :${CommitShareData.appEstimateQuestions[0]}")

        }

        if(getAppThoughtSingleData()?.isEmpty() == true) {
            Thread(OpenReadQuestionThoughtWork()).start()
        }else {
            val appThoughtSingleData = getAppThoughtSingleData()
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            CommitShareData.thoughtSingleQuestions = Gson().fromJson(appThoughtSingleData,type)
            Log.d("TAG","save thought :${CommitShareData.thoughtSingleQuestions[0]}")
        }

        if(getAppProgramSingleData()?.isEmpty() == true) {
            Thread(OpenReadQuestionProgramDesignWork()).start()
        }else {
            val appProgramSingleData = getAppProgramSingleData()
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            CommitShareData.programSingleQuestions = Gson().fromJson(appProgramSingleData,type)
            Log.d("TAG","save program :${CommitShareData.programSingleQuestions[0]}")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}