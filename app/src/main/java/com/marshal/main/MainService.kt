package com.marshal.main

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.marshalim.moudle.open.question.works.OpenReadEnglishQuestionWork
import com.marshalim.moudle.open.question.works.OpenReadQuestionProgramDesignWork
import com.marshalim.moudle.open.question.works.OpenReadQuestionThoughtWork
import com.marshalim.moudle.open.question.works.OpenReadQuestionsWork
import getAppFunctionEstimateData
import getAppFunctionMultipleData
import getAppFunctionSingleData
import getAppProgramSingleData
import getAppSecondEnglishQuestionData
import getAppThoughtSingleData

class MainService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "service:onCreate")

        if (getAppFunctionSingleData()?.isEmpty() == true
            || getAppFunctionMultipleData()?.isEmpty() == true
            || getAppFunctionEstimateData()?.isEmpty() == true
        ) {
            Thread(OpenReadQuestionsWork()).start()
        }

        if (getAppThoughtSingleData()?.isEmpty() == true) {
            Thread(OpenReadQuestionThoughtWork()).start()
        }

        if (getAppProgramSingleData()?.isEmpty() == true) {
            Thread(OpenReadQuestionProgramDesignWork()).start()
        }

        if(getAppSecondEnglishQuestionData()?.isEmpty() == true) {
            Thread(OpenReadEnglishQuestionWork()).start()
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