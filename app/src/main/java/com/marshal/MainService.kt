package com.marshal

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.marshal.mine.open.works.OpenReadQuestionProgramDesignWork
import com.marshal.mine.open.works.OpenReadQuestionThoughtWork
import com.marshal.mine.open.works.OpenReadQuestionsWork
import getAppFunctionEstimateData
import getAppFunctionMultipleData
import getAppFunctionSingleData
import getAppThoughtSingleData

class MainService :Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG","service:onCreate")

        if (getAppFunctionSingleData()?.isEmpty() == true
            || getAppFunctionMultipleData()?.isEmpty() == true
            || getAppFunctionEstimateData()?.isEmpty() == true
        ){
            Thread(OpenReadQuestionsWork()).start()
        }

        if(getAppThoughtSingleData()?.isEmpty() == true) {
            Thread(OpenReadQuestionThoughtWork()).start()
        }

        /*if(getAppProgramSingleData()?.isEmpty() == true) {
            Thread(OpenReadQuestionProgramDesignWork()).start()
        }*/
        Thread(OpenReadQuestionProgramDesignWork()).start()
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