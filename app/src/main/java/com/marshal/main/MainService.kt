package com.marshal.main

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.marshalim.moudle.open.question.works.first.OpenReadQuestionProgramDesignWork
import com.marshalim.moudle.open.question.works.first.OpenReadQuestionThoughtWork
import com.marshalim.moudle.open.question.works.first.OpenReadQuestionsWork
import com.marshalim.moudle.open.question.works.second.OpenReadEnglishQuestionWork
import com.marshalim.moudle.open.question.works.third.OpenReadDatabaseWork
import com.marshalim.moudle.open.question.works.third.OpenReadSoftwareWork
import com.marshalim.moudle.open.question.works.third.OpenReadThirdEnglishWork
import getAppFunctionEstimateData
import getAppFunctionMultipleData
import getAppFunctionSingleData
import getAppProgramSingleData
import getAppSecondEnglishQuestionData
import getAppThirdDatabaseData
import getAppThirdEnglishEighthData
import getAppThirdEnglishFifthData
import getAppThirdEnglishFirstData
import getAppThirdEnglishFourthData
import getAppThirdEnglishNinthData
import getAppThirdEnglishSecondData
import getAppThirdEnglishSeventhData
import getAppThirdEnglishSixthData
import getAppThirdEnglishTenthData
import getAppThirdEnglishThirdData
import getAppThirdSoftwareData
import getAppThoughtSingleData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 线程池 Executors 下创建的对象
 *
 * submit方法 ：
 * 可以接收实现了Runnable和Callable接口类型的任务。Callable接口与Runnable不同之处在于它可以有返回值，并且允许抛出异常。
 * 会返回一个Future对象。通过这个Future对象，可以获取到线程执行的结果或者捕获线程执行中的异常。
 * 提交的任务，可以通过返回的Future对象来检查和处理任务执行中的异常。
 *
 * execute方法:
 * 只能接收实现了Runnable接口类型的任务。
 * 没有返回值。
 * 提交的任务，在执行过程中如果发生异常，调用者无法直接得知。
 *
 */

class MainService : Service() {

    private var executorServices: ExecutorService = Executors.newSingleThreadExecutor()


    // 简化条件判断并封装成一个方法
    private fun executeIfEmpty(data: String?, work: Runnable) {
        if (data?.isEmpty() == true) {
            executorServices.execute(work)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "service:onCreate")


        if (getAppFunctionSingleData()?.isEmpty() == true
            || getAppFunctionMultipleData()?.isEmpty() == true
            || getAppFunctionEstimateData()?.isEmpty() == true
        ) {
            executorServices.execute(OpenReadQuestionsWork())
        }

        executeIfEmpty(getAppThoughtSingleData(), OpenReadQuestionThoughtWork())
        executeIfEmpty(getAppProgramSingleData(), OpenReadQuestionProgramDesignWork())
        executeIfEmpty(getAppSecondEnglishQuestionData(), OpenReadEnglishQuestionWork())
        executeIfEmpty(getAppThirdDatabaseData(), OpenReadDatabaseWork())
        executeIfEmpty(getAppThirdSoftwareData(), OpenReadSoftwareWork())

        if (getAppThirdEnglishFirstData()?.isEmpty() == true
            || getAppThirdEnglishSecondData()?.isEmpty() == true
            || getAppThirdEnglishThirdData()?.isEmpty() == true
            || getAppThirdEnglishFourthData()?.isEmpty() == true
            || getAppThirdEnglishFifthData()?.isEmpty() == true
            || getAppThirdEnglishSixthData()?.isEmpty() == true
            || getAppThirdEnglishSeventhData()?.isEmpty() == true
            || getAppThirdEnglishEighthData()?.isEmpty() == true
            || getAppThirdEnglishNinthData()?.isEmpty() == true
            || getAppThirdEnglishTenthData()?.isEmpty() == true
        ) {
            executorServices.execute(OpenReadThirdEnglishWork())
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
        executorServices.shutdown()
    }


}