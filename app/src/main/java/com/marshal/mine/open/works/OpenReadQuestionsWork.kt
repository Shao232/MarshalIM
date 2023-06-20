package com.marshal.mine.open.works

import FileUtils
import android.util.Log
import com.google.gson.Gson
import com.marshal.pojo.OpenAnswerBean
import com.marshal.pojo.OpenAnswersBean
import jxl.Cell
import jxl.write.WritableSheet
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import putAppFunctionEstimateData
import putAppFunctionMultipleData
import putAppFunctionSingleData
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * 计算机应用基础
 */
class OpenReadQuestionsWork : Runnable {

    private val lock = ReentrantReadWriteLock()

    override fun run() {
        synchronized(this){
            lock.readLock().lock()
            try {
                readAppFunctionWork()
            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                lock.readLock().unlock()
            }
        }
    }

    private fun readAppFunctionWork() {

        //如果文件不存在从程序中获取
        val file = FileUtils.copyAssetsResFile(
            "计算机应用基础.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "计算机应用基础.xls"
        )

        var zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file)
        //读取单元格内容
        //读取单元格内容
        zzExcelCreator1 = zzExcelCreator1.openSheet(0)
        val writableSheet = zzExcelCreator1.writableSheet

        Log.d("TAG", "计算机应用基础 columns: 有${writableSheet.columns}列")
        Log.d("TAG", "计算机应用基础 rows: 有${writableSheet.rows}行")

        singleChoiceAnswer(writableSheet)

        //别忘了close
        zzExcelCreator1.close()
    }

    /**
     * 单选题
     */
    private fun singleChoiceAnswer(writableSheet: WritableSheet) {
         //读取应用基础的第一部分 单选题 从第3行到169行 程序中从0计数

        val dataSingle = ArrayList<OpenAnswersBean>()
        val dataMultiple = ArrayList<OpenAnswersBean>()
        val dataEstimate = ArrayList<OpenAnswersBean>()

        val arrayColum1 = writableSheet.getColumn(0)
        val arrayColum2 = writableSheet.getColumn(1)
        val arrayColum3 = writableSheet.getColumn(2)
        val arrayColum4 = writableSheet.getColumn(3)
        val arrayColum5 = writableSheet.getColumn(4)
        val arrayColum6 = writableSheet.getColumn(5)
        val arrayColum7 = writableSheet.getColumn(6)

        setQuestionTitle(arrayColum1, dataSingle, dataMultiple, dataEstimate)

        /**
         * 将答题的题目集合到arraylist中
         */
        setSingleAnswerList(arrayColum2,"A",dataSingle)
        setSingleAnswerList(arrayColum3,"B",dataSingle)
        setSingleAnswerList(arrayColum4,"C",dataSingle)
        setSingleAnswerList(arrayColum5,"D",dataSingle)
        setSingleRightAnswer(arrayColum6,dataSingle)

        setMultipleAnswerList(arrayColum2,"A",dataMultiple)
        setMultipleAnswerList(arrayColum3,"B",dataMultiple)
        setMultipleAnswerList(arrayColum4,"C",dataMultiple)
        setMultipleAnswerList(arrayColum5,"D",dataMultiple)
        setMultipleAnswerList(arrayColum6,"E",dataMultiple)
        setMultipleRightAnswer(arrayColum7,dataMultiple)

        setEstimateAnswerList(arrayColum2,dataEstimate)
        setEstimateRightAnswer(arrayColum2,dataEstimate)

       val json1 = Gson().toJson(dataSingle)
        putAppFunctionSingleData(json1)

        val json2 = Gson().toJson(dataMultiple)
        putAppFunctionMultipleData(json2)

        val json3 = Gson().toJson(dataEstimate)
        putAppFunctionEstimateData(json3)
    }

    private fun setSingleRightAnswer(arrayColum: Array<Cell>,  dataSingle: ArrayList<OpenAnswersBean>){
        var indexSecond = 0
        var indexColumRow = 0
        while(indexColumRow < arrayColum.size) {
            if (indexColumRow in 2..168) {
                dataSingle[indexSecond].rightAnswer = arrayColum[indexColumRow].contents
                indexSecond++
            }
            indexColumRow ++
        }
    }

    private fun setMultipleRightAnswer(arrayColum: Array<Cell>,  dataMultiple: ArrayList<OpenAnswersBean>){
        var indexSecond = 0
        var indexColumRow = 0
        while(indexColumRow < arrayColum.size) {
            if (indexColumRow in 172..217) {
                dataMultiple[indexSecond].rightAnswer = arrayColum[indexColumRow].contents
                indexSecond++
            }
            indexColumRow ++
        }
    }

    private fun setEstimateRightAnswer(arrayColum: Array<Cell>,  dataEstimate: ArrayList<OpenAnswersBean>){
        var indexSecond = 0
        var indexColumRow = 0
        while(indexColumRow < arrayColum.size) {
            if (indexColumRow in 222..301) {
                dataEstimate[indexSecond].rightAnswer = arrayColum[indexColumRow].contents
                indexSecond++
            }
            indexColumRow ++
        }
    }

    private fun setSingleAnswerList(
        arrayColum: Array<Cell>,
        answerTitle:String,
        dataSingle: ArrayList<OpenAnswersBean>,
    ) {
        var indexSecond = 0
        var indexColumRow = 0
        while(indexColumRow < arrayColum.size) {
            if (indexColumRow in 2..168) {
                dataSingle[indexSecond].answerList?.add(OpenAnswerBean(answerTitle, arrayColum[indexColumRow].contents))
                indexSecond++
            }
            indexColumRow ++
        }
    }

    private fun setMultipleAnswerList(
        arrayColum: Array<Cell>,
        answerTitle:String,
        dataMultiple: ArrayList<OpenAnswersBean>,

        ) {
        var indexSecond = 0
        var indexColumRow = 0
        while(indexColumRow < arrayColum.size) {
            if (indexColumRow in 172..217) {
                dataMultiple[indexSecond].answerList?.add(OpenAnswerBean(answerTitle, arrayColum[indexColumRow].contents))
                indexSecond++
            }
            indexColumRow ++
        }
    }

    private fun setEstimateAnswerList(
        arrayColum: Array<Cell>,
        dataEstimate: ArrayList<OpenAnswersBean>,
        ) {
        var indexSecond = 0
        var indexColumRow = 0
        while(indexColumRow < arrayColum.size) {
            if (indexColumRow in 222..301) {
                dataEstimate[indexSecond].answerList?.add(OpenAnswerBean("A", "正确"))
                dataEstimate[indexSecond].answerList?.add(OpenAnswerBean("B", "错误"))
                indexSecond++
            }
            indexColumRow ++
        }
    }

    /**
     * 设置单选，多选，判断的题目
     * 直接硬编码读取行数，从168之前都是单选题，后面的节点类似
     */
    private fun setQuestionTitle(
        arrayColum1: Array<Cell>,
        dataSingle: ArrayList<OpenAnswersBean>,
        dataMultiple: ArrayList<OpenAnswersBean>,
        dataEstimate: ArrayList<OpenAnswersBean>
    ) {
        Log.d("TAG", " arrayColum1.size ${arrayColum1.size}")
        var indexColumFirst = 0
        while (indexColumFirst < arrayColum1.size) {
            if (indexColumFirst in 2..168) {
                //添加数据，并设置单选题的题目
                dataSingle.add(
                    OpenAnswersBean(
                        indexColumFirst, 1,
                        arrayColum1[indexColumFirst].contents,
                    )
                )
            }

            if (indexColumFirst in 172..217) {
                //添加数据，并设置多选题的题目
                dataMultiple.add(
                    OpenAnswersBean(
                        indexColumFirst, 2,
                        arrayColum1[indexColumFirst].contents,
                    )
                )
            }

            if (indexColumFirst in 222..301) {
                //添加数据，并设置判断题的题目
                dataEstimate.add(
                    OpenAnswersBean(
                        indexColumFirst, 3,
                        arrayColum1[indexColumFirst].contents,
                    )
                )
            }
            indexColumFirst++
        }
    }
}