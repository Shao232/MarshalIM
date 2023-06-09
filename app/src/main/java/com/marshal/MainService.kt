package com.marshal

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.marshal.mine.open.works.OpenReadQuestionProgramDesignWork
import com.marshal.mine.open.works.OpenReadQuestionThoughtWork
import com.marshal.mine.open.works.OpenReadQuestionsWork

class MainService:Service() {

    override fun onCreate() {
        super.onCreate()
        Thread(OpenReadQuestionsWork()).start()
        Thread(OpenReadQuestionProgramDesignWork()).start()
        Thread(OpenReadQuestionThoughtWork()).start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}