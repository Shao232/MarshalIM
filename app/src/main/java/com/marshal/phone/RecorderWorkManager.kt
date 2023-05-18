package com.marshal.phone

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
    //需要实例化一个Constraints对象指定任务运行的约束(触发条件)
    val constraints:Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val myWorkRequest:WorkRequest = OneTimeWorkRequest.Builder(RecorderWorkManager::class.java)
        .setConstraints(constraints).build()
    val work = WorkManager.getInstance(this)
    work.enqueue(myWorkRequest)

 */

@Deprecated("这个类暂无无用，但是不想删除，先保留")
class RecorderWorkManager(@NonNull context: Context,
                          @NonNull workerParams: WorkerParameters):Worker(context, workerParams) {



    override fun doWork(): Result {
        Log.d("TAG","doWork运行")

        return Result.success()
    }


}