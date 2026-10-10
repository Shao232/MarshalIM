package com.marshalim.moudle.open.question.works.first

import FileUtils
import android.util.Log
import com.google.gson.Gson
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import jxl.Workbook
import putAppThoughtSingleData
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * 计算思维导论
 */
class OpenReadQuestionThoughtWork : Runnable {

    private val lock = ReentrantReadWriteLock()

    override fun run() {
        synchronized(this) {
            lock.readLock().lock()
            try {
                readThought()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                lock.readLock().unlock()
            }
        }
    }

    /**
     * 思维导论读取
     */
    private fun readThought() {
        val file = FileUtils.copyAssetsResFile(
            "计算思维导论_2.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "计算思维导论_2.xls"
        )

        val workbook = Workbook.getWorkbook(file)
        val writableSheet = workbook.getSheet(0)
        Log.d("TAG", "计算思维导论 columns: 有${writableSheet.columns}列")
        Log.d("TAG", "计算思维导论 rows: 有${writableSheet.rows}行")

        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)

        val data = ArrayList<OpenAnswersBean>()

        var indexFirst = 0
        while (indexFirst < array1.size) {
            //添加数据，并设置题目
            data.add(
                OpenAnswersBean(
                    indexFirst, 1,
                    array1[indexFirst].contents,
                )
            )
            indexFirst++
        }

        var indexSix = 0
        array2.mapIndexed { index, cell ->
            data[indexSix].rightAnswer = cell.contents
            indexSix++
        }

        val json = Gson().toJson(data)
        putAppThoughtSingleData(json)

        workbook.close()
    }
}