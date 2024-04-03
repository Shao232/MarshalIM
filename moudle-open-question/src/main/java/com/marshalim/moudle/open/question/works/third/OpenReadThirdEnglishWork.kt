package com.marshalim.moudle.open.question.works.third

import FileUtils
import android.util.Log
import com.google.gson.Gson
import com.marshalim.moudle.open.question.pojo.OpenAnswerBean
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import jxl.Cell
import jxl.write.WritableSheet
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import putAppThirdEnglishFirstData
import putAppThirdEnglishSecondData

class OpenReadThirdEnglishWork : Runnable {

    //初始化集合
    private var firstArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var secondArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var thirdArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var fourthArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var fifthArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var sixthArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var seventhArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var eighthArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var ninthArrayList: ArrayList<OpenAnswersBean>? = ArrayList()
    private var tenthArrayList: ArrayList<OpenAnswersBean>? = ArrayList()

    override fun run() {
        synchronized(this) {
            try {
                readEnglishFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun readEnglishFile() {
        val file = FileUtils.copyAssetsResFile(
            "english_third_test.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "english_third_test.xls"
        )

        var zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file)
        zzExcelCreator1 = zzExcelCreator1.openSheet(0)
        var writableSheet = zzExcelCreator1.writableSheet


        //实训1
        readFirstTrainTest(writableSheet)
        //实训2
        zzExcelCreator1 = zzExcelCreator1.openSheet(1)
        writableSheet = zzExcelCreator1.writableSheet
        readSecondTrainTest(writableSheet)
        //实训3
        zzExcelCreator1 = zzExcelCreator1.openSheet(2)
        writableSheet = zzExcelCreator1.writableSheet
        readThirdTrainTest(writableSheet)

        //实训4

        //实训5

        //实训6

        //实训7

        //实训8

        //实训9

        //实训10

    }



    private fun readFourthTrainTest(){

    }

    private fun readThirdTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(thirdArrayList,arrayZero)
        readFirstArray(thirdArrayList ?: return, arrayFirst)
        readSecondArray(thirdArrayList ?: return, arraySecond)
        readAnswerArray(thirdArrayList ?: return, arrayThird)
        readAnswerArray(thirdArrayList ?: return, arrayFourth)
        readAnswerArray(thirdArrayList ?: return, arrayFifth)
        readAnswerArray(thirdArrayList ?: return, arraySixth)
        readParseArray(thirdArrayList ?: return, arraySeventh)

//        if (thirdArrayList?.isNotEmpty() == true) {
//            for (itemBean: OpenAnswersBean in thirdArrayList ?: return) {
//                Log.d(
//                    "TAG",
//                    "item id:${itemBean.id},题目类型:${itemBean.questionType},题目:${itemBean.title}," +
//                            "参考答案:${itemBean.rightAnswer},选项:${itemBean.answerList.toString()}，解析:${itemBean.parseAnswer}"
//                )
//            }
//        }

        Log.d("TAG", "third size:${thirdArrayList?.size}")
        val json = Gson().toJson(thirdArrayList)
        putAppThirdEnglishSecondData(json)

    }

    private fun readSecondTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(secondArrayList,arrayZero)
        readFirstArray(secondArrayList ?: return, arrayFirst)
        readSecondArray(secondArrayList ?: return, arraySecond)
        readAnswerArray(secondArrayList ?: return, arrayThird)
        readAnswerArray(secondArrayList ?: return, arrayFourth)
        readAnswerArray(secondArrayList ?: return, arrayFifth)
        readAnswerArray(secondArrayList ?: return, arraySixth)
        readParseArray(secondArrayList ?: return, arraySeventh)


//        if (secondArrayList?.isNotEmpty() == true) {
//            for (itemBean: OpenAnswersBean in secondArrayList ?: return) {
//                Log.d(
//                    "TAG",
//                    "item id:${itemBean.id},题目类型:${itemBean.questionType},题目:${itemBean.title}," +
//                            "参考答案:${itemBean.rightAnswer},选项:${itemBean.answerList.toString()}，解析:${itemBean.parseAnswer}"
//                )
//            }
//        }

        Log.d("TAG", "second size:${secondArrayList?.size}")
        val json = Gson().toJson(secondArrayList)
        putAppThirdEnglishSecondData(json)

    }

    //实训1
    private fun readFirstTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(firstArrayList,arrayZero)
        readFirstArray(firstArrayList ?: return, arrayFirst)
        readSecondArray(firstArrayList ?: return, arraySecond)
        readAnswerArray(firstArrayList ?: return, arrayThird)
        readAnswerArray(firstArrayList ?: return, arrayFourth)
        readAnswerArray(firstArrayList ?: return, arrayFifth)
        readAnswerArray(firstArrayList ?: return, arraySixth)
        readParseArray(firstArrayList ?: return, arraySeventh)


//        if (firstArrayList?.isNotEmpty() == true) {
//            for (itemBean: OpenAnswersBean in firstArrayList ?: return) {
//                Log.d(
//                    "TAG",
//                    "item id:${itemBean.id},题目类型:${itemBean.questionType},题目:${itemBean.title}," +
//                            "参考答案:${itemBean.rightAnswer},选项:${itemBean.answerList.toString()}，解析:${itemBean.parseAnswer}"
//                )
//            }
//        }

        Log.d("TAG", "first size:${firstArrayList?.size}")
        val json = Gson().toJson(firstArrayList)
        putAppThirdEnglishFirstData(json)
    }

    private fun readQuestionType(arrayList: ArrayList<OpenAnswersBean>?, arrayZero: Array<Cell>) {
        for ((index, itemCell) in arrayZero.withIndex()) {
            if (itemCell.contents.isNullOrEmpty()) {
                continue
            }
            if (index > 1) {
                val answersBean = OpenAnswersBean(index)
                when (itemCell.contents) {
                    "词汇与结构", "辨别错误", "完形填空", "完型填空", "阅读理解" -> {
                        answersBean.questionType = 1
                    }

                    "英汉翻译" -> {
                        answersBean.questionType = 5
                    }
                }
                arrayList?.add(answersBean)
            }
        }
    }

    private fun readFirstArray(
        arrayList: ArrayList<OpenAnswersBean>,
        arrayFirst: Array<Cell>
    ) {
        for (itemBean in arrayList) {
            for ((index, itemCall) in arrayFirst.withIndex()) {
                if (index == itemBean.id) {
                    itemBean.title = itemCall.contents
                }
            }
        }
    }

    private fun readSecondArray(
        arrayList: ArrayList<OpenAnswersBean>,
        arraySecond: Array<Cell>
    ) {
        for (itemBean in arrayList) {
            for ((index, itemCall) in arraySecond.withIndex()) {
                if (index == itemBean.id) {
                    itemBean.rightAnswer = itemCall.contents
                }
            }
        }
    }

    private fun readAnswerArray(
        arrayList: ArrayList<OpenAnswersBean>,
        arrayAnswerList: Array<Cell>
    ) {
        for (itemBean in arrayList) {
            for ((index, itemCall) in arrayAnswerList.withIndex()) {
                if (index == itemBean.id) {
                    itemBean.answerList?.add(OpenAnswerBean(answerContent = itemCall.contents))
                }
            }
        }
    }

    private fun readParseArray(
        arrayList: ArrayList<OpenAnswersBean>,
        arrayAnswerList: Array<Cell>
    ) {
        for (itemBean in arrayList) {
            for ((index, itemCall) in arrayAnswerList.withIndex()) {
                if (index == itemBean.id) {
                    itemBean.parseAnswer = itemCall.contents
                }
            }
        }
    }


}