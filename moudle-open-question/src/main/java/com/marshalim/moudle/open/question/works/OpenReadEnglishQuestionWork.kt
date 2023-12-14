package com.marshalim.moudle.open.question.works

import FileUtils
import android.util.Log
import com.google.gson.Gson
import com.marshalim.moudle.open.question.pojo.OpenAnswerBean
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import jxl.Cell
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import putAppSecondEnglishQuestionData
import java.util.concurrent.locks.ReentrantReadWriteLock

class OpenReadEnglishQuestionWork : Runnable {

    private val lock = ReentrantReadWriteLock()
    private val data = ArrayList<OpenAnswersBean>()

    override fun run() {
        synchronized(this) {
            lock.readLock().lock()
            try {
                readEnglishQuestion()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                lock.readLock().unlock()
            }
        }
    }

    private fun readEnglishQuestion() {

        val file = FileUtils.copyAssetsResFile(
            "english_question.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "english_question.xls"
        )

        var zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file)

        zzExcelCreator1 = zzExcelCreator1.openSheet(0)
        val writableSheet = zzExcelCreator1.writableSheet
        Log.d("TAG", "english columns: 有${writableSheet.columns}列")
        Log.d("TAG", "english rows: 有${writableSheet.rows}行")


        val arrayZero = writableSheet.getColumn(0)
        val array1 = writableSheet.getColumn(1)
        val array2 = writableSheet.getColumn(2)
        val array3 = writableSheet.getColumn(3)
        val array4 = writableSheet.getColumn(4)
        val array5 = writableSheet.getColumn(5)
        val array7 = writableSheet.getColumn(7)

        printContent(arrayZero, 0)
        printContent(array1, 1)
        printContent(array2, 2)
        printContent(array3, 3)
        printContent(array4, 4)
        printContent(array5, 5)
        printContent(array7, 6)

//        for(bean in data) {
//            Log.d("TAG","bean :${bean}")
//        }

        val json = Gson().toJson(data)
        putAppSecondEnglishQuestionData(json)

        zzExcelCreator1.close()
    }

    private fun printContent(arrayCell: Array<Cell>, type: Int) {
        Log.d("TAG","size :${arrayCell.size}")
        var array1Index = 1
        var bean:OpenAnswersBean? = null
        while (array1Index < arrayCell.size) {
            when(type) {
                0,1,2,3,4,6->{
                    if(array1Index >=251){
                        break
                    }
                }
            }

            val item = arrayCell[array1Index]
            if(type == 0) {
                val questionTypeStr = item.contents
                val questionType:Int = if(questionTypeStr.equals("单选题")) {
                    1
                }else {
                    3
                }
                bean = OpenAnswersBean(array1Index,questionType,null, arrayListOf(),null)
                data.add(bean)
            }
            array1Index++
        }

        if(type == 1) {
            array1Index = 1
            while (array1Index < arrayCell.size) {
                val item = arrayCell[array1Index]
                data.find { it.id == array1Index }?.title = item.contents

                array1Index++
            }
        }

        if(type == 2) {
            array1Index = 1
            while (array1Index < arrayCell.size) {
                if(array1Index >=251){
                    break
                }
                val item = arrayCell[array1Index]
                data.find { it.id == array1Index }?.answerList?.add(OpenAnswerBean("",item.contents))
                array1Index++
            }
        }

        if(type == 3) {
            array1Index = 1
            while (array1Index < arrayCell.size) {
                if(array1Index >=251){
                    break
                }
                val item = arrayCell[array1Index]
                data.find { it.id == array1Index }?.answerList?.add(OpenAnswerBean("",item.contents))
                array1Index++
            }
        }

        if(type == 4) {
            array1Index = 1
            while (array1Index < arrayCell.size) {
                if(array1Index >=101){
                    break
                }
                val item = arrayCell[array1Index]
                data.find { it.id == array1Index }?.answerList?.add(OpenAnswerBean("",item.contents))
                array1Index++
            }
        }

        if(type == 5) {
            array1Index = 1
            while (array1Index < arrayCell.size) {
                if(array1Index >=101){
                    break
                }
                val item = arrayCell[array1Index]
                data.find { it.id == array1Index }?.answerList?.add(OpenAnswerBean("",item.contents))
                array1Index++
            }
        }

        if(type == 6) {
            array1Index = 1
            while (array1Index < arrayCell.size) {
                if(array1Index >=251){
                    break
                }
                val item = arrayCell[array1Index]
                data.find { it.id == array1Index }?.rightAnswer = item.contents
                array1Index++
            }
        }

    }


}