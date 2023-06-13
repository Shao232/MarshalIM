package com.marshal.mine.open.works

import android.util.Log
import com.google.gson.Gson
import com.marshal.pojo.OpenAnswerBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.utils.FileUtils
import getAppProgramSingleData
import jxl.Cell
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import putAppProgramSingleData
import java.util.concurrent.locks.ReentrantReadWriteLock

class OpenReadQuestionProgramDesignWork : Runnable {

    private val lock = ReentrantReadWriteLock()

    override fun run() {
        synchronized(this) {
            lock.readLock().lock()
            try {
                readProgramDesign()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                lock.readLock().unlock()
            }
        }
    }

    /**
     * 程序设计读取
     */
    private fun readProgramDesign() {

        val file = FileUtils.copyAssetsResFile(
            "程序设计复习资料_2.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "程序设计复习资料_2.xls"
        )

        var zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file)
        //读取单元格内容
        //读取单元格内容
        zzExcelCreator1 = zzExcelCreator1.openSheet(0)
        val writableSheet = zzExcelCreator1.writableSheet
        Log.d("TAG", "程序设计 columns: 有${writableSheet.columns}列")
        Log.d("TAG", "程序设计 rows: 有${writableSheet.rows}行")

        val array1 = writableSheet.getColumn(1)
        val array2 = writableSheet.getColumn(2)
        val array3 = writableSheet.getColumn(3)
        val array4 = writableSheet.getColumn(4)
        val array5 = writableSheet.getColumn(5)
        val array6 = writableSheet.getColumn(6)
        val array7 = writableSheet.getColumn(7)

        val data = ArrayList<OpenAnswersBean>()

        //设置题目
        var indexFirst = 0
        while (indexFirst < array1.size) {
            if (indexFirst >= 1) {
                //添加数据，并设置题目
                val questionType = if (indexFirst in 1..3) {
                    //填空类型
                    4
                } else {
                    //单选类型
                    1
                }
                data.add(
                    OpenAnswersBean(
                        indexFirst, questionType,
                        array1[indexFirst].contents,
                    )
                )
            }
            indexFirst++
        }

        setSecondAnswersType(array2, data, "A")
        setSecondAnswersType(array3, data, "B")
        setSecondAnswersType(array4, data, "C")
        setSecondAnswersType(array5, data, "D")
        setSecondAnswersType(array6, data, "E")


        //通过列设置正确的参考答案
        var indexSix = 0
        array7.mapIndexed { index, cell ->
            if (index >= 1) {
                data[indexSix].rightAnswer = cell.contents
                indexSix++
            }
        }

        val json = Gson().toJson(data)
        putAppProgramSingleData(json)
        Log.d("TAG", "程序设计 题目 json:${getAppProgramSingleData()}")

        zzExcelCreator1.close()
    }

    /**
     * 多选题 149到 188
     */
    private fun setSecondAnswersType(
        arrayColum: Array<Cell>,
        dataSingle: ArrayList<OpenAnswersBean>,
        answerTitle: String,
    ) {
        //这个循环excel的列
        var indexColumns = 0
        //这个循环数据源data
        var indexDataSecond = 0
        while (indexColumns < arrayColum.size) {
            //去掉题目
            if (indexColumns >= 1) {
                //去掉前三个填空题
                if(indexDataSecond >= 3) {
                    val content =  arrayColum[indexColumns].contents
                    if(content?.isNotEmpty() == true) {
                        dataSingle[indexDataSecond].answerList?.add(
                            OpenAnswerBean(
                                answerTitle,
                                arrayColum[indexColumns].contents
                            )
                        )
                    }
                }
                indexDataSecond++
            }
            indexColumns++
        }
    }

}