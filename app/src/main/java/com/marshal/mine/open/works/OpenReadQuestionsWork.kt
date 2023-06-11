package com.marshal.mine.open.works

import StoreManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswerBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.sharedata.CommitShareData
import com.marshal.utils.FileUtils
import getAppFunctionEstimateData
import getAppFunctionMultipleData
import getAppFunctionSingleData
import jxl.Cell
import jxl.write.WritableSheet
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import org.greenrobot.eventbus.EventBus
import putAppFunctionEstimateData
import putAppFunctionMultipleData
import putAppFunctionSingleData

class OpenReadQuestionsWork : Runnable {

    override fun run() {
        try {
            readAppFunctionWork()

        } catch (e: Exception) {
            e.printStackTrace()
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
        Log.d("TAG", "sheet size : ${zzExcelCreator1.writableWorkbook.numberOfSheets}")
        zzExcelCreator1 = zzExcelCreator1.openSheet(0)
        var writableSheet = zzExcelCreator1.writableSheet

        if (writableSheet.name == "单选题") {
            singleChoiceAnswer(writableSheet)
        }

        zzExcelCreator1 = zzExcelCreator1.openSheet(1)
        writableSheet = zzExcelCreator1.writableSheet

        if (writableSheet.name == "多选题") {

            multipleChoiceAnswer(writableSheet)
        }

        zzExcelCreator1 = zzExcelCreator1.openSheet(2)
        writableSheet = zzExcelCreator1.writableSheet

        if (writableSheet.name == "判断题") {
            estimateAnswer(writableSheet)
        }

        //别忘了close
        zzExcelCreator1.close()
    }

    /**
     * 单选题
     */
    private fun singleChoiceAnswer(writableSheet: WritableSheet) {
        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)
        val array3 = writableSheet.getColumn(2)
        val array4 = writableSheet.getColumn(3)
        val array5 = writableSheet.getColumn(4)
        val array6 = writableSheet.getColumn(12)

        var data = ArrayList<OpenAnswersBean>()

        var indexFirst = 0
        while (indexFirst < array1.size) {
            if (indexFirst >= 2) {
                //添加数据，并设置题目
                data.add(
                    OpenAnswersBean(
                        indexFirst, 1,
                        array1[indexFirst].contents,
                    )
                )
            }
            indexFirst++
        }

        //设置回答
        data = setAnswerList(array2, data, "A")
        data = setAnswerList(array3, data, "B")
        data = setAnswerList(array4, data, "C")
        data = setAnswerList(array5, data, "D")

        //读取参考答案，设置在对象中
        var indexSix = 0
        array6.mapIndexed { index, cell ->
            if (index >= 2) {
                data[indexSix].rightAnswer = cell.contents
                indexSix++
            }
        }

        val json = Gson().toJson(data)
        putAppFunctionSingleData(json)
        Log.d("TAG", "json:${getAppFunctionSingleData()}")

        val appFunctionSingleData = getAppFunctionSingleData()
        val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
        CommitShareData.appSingleQuestions = Gson().fromJson(appFunctionSingleData,type)
        Log.d("TAG","save CommitShareData :${CommitShareData.appSingleQuestions.size}")

//        val eventMsg = OpenQuestionEventBean()
//        eventMsg.what = 0x11
//        eventMsg.questionList = data
//        EventBus.getDefault().post(eventMsg)
    }

    /**
     * 将答题的题目集合到arraylist中
     */
    private fun setAnswerList(
        array: Array<Cell>,
        data: ArrayList<OpenAnswersBean>,
        answerTag: String
    ): ArrayList<OpenAnswersBean> {
        var indexSecond = 0
        array.mapIndexed { index, cell ->
            if (index >= 2) {
                data[indexSecond].answerList?.add(OpenAnswerBean(answerTag, cell.contents))
                indexSecond++
            }
        }
        return data
    }

    /**
     * 多选题
     */
    private fun multipleChoiceAnswer(writableSheet: WritableSheet) {
        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)
        val array3 = writableSheet.getColumn(2)
        val array4 = writableSheet.getColumn(3)
        val array5 = writableSheet.getColumn(4)
        val array6 = writableSheet.getColumn(5)
        val array7 = writableSheet.getColumn(6)

        var data = ArrayList<OpenAnswersBean>()

        var indexFirst = 0
        while (indexFirst < array1.size) {
            if (indexFirst >= 2) {
                //添加数据，并设置题目
                data.add(
                    OpenAnswersBean(
                        indexFirst, 2,
                        array1[indexFirst].contents,
                    )
                )
            }
            indexFirst++
        }

        //设置回答
        data = setAnswerList(array2, data, "A")
        data = setAnswerList(array3, data, "B")
        data = setAnswerList(array4, data, "C")
        data = setAnswerList(array5, data, "D")
        data = setAnswerList(array6, data, "E")

        //读取参考答案，设置在对象中
        var indexSix = 0
        array7.mapIndexed { index, cell ->
            if (index >= 2) {
                data[indexSix].rightAnswer = cell.contents
                indexSix++
            }
        }

        val json = Gson().toJson(data)
        putAppFunctionMultipleData(json)
        Log.d("TAG", "json:${getAppFunctionMultipleData()}")

        val appFunctionMultipleData = getAppFunctionMultipleData()
        val type2 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
        CommitShareData.appMultipleQuestions = Gson().fromJson(appFunctionMultipleData,type2)
        Log.d("TAG","save CommitShareData :${CommitShareData.appMultipleQuestions.size}")

//        val eventMsg = OpenQuestionEventBean()
//        eventMsg.what = 0x12
//        eventMsg.questionList = data
//        EventBus.getDefault().post(eventMsg)
    }

    /**
     * 判断题
     */
    private fun estimateAnswer(writableSheet: WritableSheet) {
        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)

        val data = ArrayList<OpenAnswersBean>()

        var indexFirst = 0
        while (indexFirst < array1.size) {
            if (indexFirst >= 2) {
                //添加数据，并设置题目
                data.add(
                    OpenAnswersBean(
                        indexFirst, 3,
                        array1[indexFirst].contents,
                    )
                )
            }
            indexFirst++
        }

        //设置回答
        var indexSecond = 0
        var indexValue = 0
        while (indexSecond < array2.size) {
            if (indexSecond >= 2) {
                data[indexValue].answerList?.add(OpenAnswerBean("A", "正确"))
                data[indexValue].answerList?.add(OpenAnswerBean("B", "错误"))
                indexValue++
            }
            indexSecond++
        }

        //读取参考答案，设置在对象中
        var indexSix = 0
        array2.mapIndexed { index, cell ->
            if (index >= 2) {
                data[indexSix].rightAnswer = cell.contents
                indexSix++
            }
        }

        val json = Gson().toJson(data)
        putAppFunctionEstimateData(json)
        Log.d("TAG", "json:${getAppFunctionEstimateData()}")

        val appFunctionEstimateData = getAppFunctionEstimateData()
        val type3 = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
        CommitShareData.appEstimateQuestions = Gson().fromJson(appFunctionEstimateData,type3)
        Log.d("TAG","save CommitShareData :${CommitShareData.appEstimateQuestions.size}")

//        val eventMsg = OpenQuestionEventBean()
//        eventMsg.what = 0x13
//        eventMsg.questionList = data
//        EventBus.getDefault().post(eventMsg)
    }


}