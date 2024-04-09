package com.marshalim.moudle.open.question.works.third

import FileUtils
import com.google.gson.Gson
import com.marshalim.moudle.open.question.pojo.OpenAnswerBean
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import jxl.Cell
import jxl.write.WritableSheet
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import putAppThirdEnglishEighthData
import putAppThirdEnglishFifthData
import putAppThirdEnglishFirstData
import putAppThirdEnglishFourthData
import putAppThirdEnglishNinthData
import putAppThirdEnglishSecondData
import putAppThirdEnglishSeventhData
import putAppThirdEnglishSixthData
import putAppThirdEnglishTenthData
import putAppThirdEnglishThirdData

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
        zzExcelCreator1 = zzExcelCreator1.openSheet(3)
        writableSheet = zzExcelCreator1.writableSheet
        readFourthTrainTest(writableSheet)

        //实训5
        zzExcelCreator1 = zzExcelCreator1.openSheet(4)
        writableSheet = zzExcelCreator1.writableSheet
        readFifthTrainTest(writableSheet)

        //实训6
        zzExcelCreator1 = zzExcelCreator1.openSheet(5)
        writableSheet = zzExcelCreator1.writableSheet
        readSixthTrainTest(writableSheet)

        //实训7
        zzExcelCreator1 = zzExcelCreator1.openSheet(6)
        writableSheet = zzExcelCreator1.writableSheet
        readSeventhTrainTest(writableSheet)

        //实训8
        zzExcelCreator1 = zzExcelCreator1.openSheet(7)
        writableSheet = zzExcelCreator1.writableSheet
        readEighthTrainTest(writableSheet)

        //实训9
        zzExcelCreator1 = zzExcelCreator1.openSheet(8)
        writableSheet = zzExcelCreator1.writableSheet
        readNinthTrainTest(writableSheet)

        //实训10
        zzExcelCreator1 = zzExcelCreator1.openSheet(9)
        writableSheet = zzExcelCreator1.writableSheet
        readTenthTrainTest(writableSheet)

    }

    private fun readTenthTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(tenthArrayList, arrayZero)
        readFirstArray(tenthArrayList ?: return, arrayFirst)
        readSecondArray(tenthArrayList ?: return, arraySecond)
        readAnswerArray(tenthArrayList ?: return, arrayThird)
        readAnswerArray(tenthArrayList ?: return, arrayFourth)
        readAnswerArray(tenthArrayList ?: return, arrayFifth)
        readAnswerArray(tenthArrayList ?: return, arraySixth)
        readParseArray(tenthArrayList ?: return, arraySeventh)

        val json = Gson().toJson(tenthArrayList)
        putAppThirdEnglishTenthData(json)

    }

    private fun readNinthTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(ninthArrayList, arrayZero)
        readFirstArray(ninthArrayList ?: return, arrayFirst)
        readSecondArray(ninthArrayList ?: return, arraySecond)
        readAnswerArray(ninthArrayList ?: return, arrayThird)
        readAnswerArray(ninthArrayList ?: return, arrayFourth)
        readAnswerArray(ninthArrayList ?: return, arrayFifth)
        readAnswerArray(ninthArrayList ?: return, arraySixth)
        readParseArray(ninthArrayList ?: return, arraySeventh)

        val json = Gson().toJson(ninthArrayList)
        putAppThirdEnglishNinthData(json)

    }

    private fun readEighthTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(eighthArrayList, arrayZero)
        readFirstArray(eighthArrayList ?: return, arrayFirst)
        readSecondArray(eighthArrayList ?: return, arraySecond)
        readAnswerArray(eighthArrayList ?: return, arrayThird)
        readAnswerArray(eighthArrayList ?: return, arrayFourth)
        readAnswerArray(eighthArrayList ?: return, arrayFifth)
        readAnswerArray(eighthArrayList ?: return, arraySixth)
        readParseArray(eighthArrayList ?: return, arraySeventh)

        val json = Gson().toJson(eighthArrayList)
        putAppThirdEnglishEighthData(json)

    }

    private fun readSeventhTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(seventhArrayList, arrayZero)
        readFirstArray(seventhArrayList ?: return, arrayFirst)
        readSecondArray(seventhArrayList ?: return, arraySecond)
        readAnswerArray(seventhArrayList ?: return, arrayThird)
        readAnswerArray(seventhArrayList ?: return, arrayFourth)
        readAnswerArray(seventhArrayList ?: return, arrayFifth)
        readAnswerArray(seventhArrayList ?: return, arraySixth)
        readParseArray(seventhArrayList ?: return, arraySeventh)

        val json = Gson().toJson(seventhArrayList)
        putAppThirdEnglishSeventhData(json)

    }

    private fun readSixthTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(sixthArrayList, arrayZero)
        readFirstArray(sixthArrayList ?: return, arrayFirst)
        readSecondArray(sixthArrayList ?: return, arraySecond)
        readAnswerArray(sixthArrayList ?: return, arrayThird)
        readAnswerArray(sixthArrayList ?: return, arrayFourth)
        readAnswerArray(sixthArrayList ?: return, arrayFifth)
        readAnswerArray(sixthArrayList ?: return, arraySixth)
        readParseArray(sixthArrayList ?: return, arraySeventh)

        val json = Gson().toJson(sixthArrayList)
        putAppThirdEnglishSixthData(json)

    }

    private fun readFifthTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(fifthArrayList, arrayZero)
        readFirstArray(fifthArrayList ?: return, arrayFirst)
        readSecondArray(fifthArrayList ?: return, arraySecond)
        readAnswerArray(fifthArrayList ?: return, arrayThird)
        readAnswerArray(fifthArrayList ?: return, arrayFourth)
        readAnswerArray(fifthArrayList ?: return, arrayFifth)
        readAnswerArray(fifthArrayList ?: return, arraySixth)
        readParseArray(fifthArrayList ?: return, arraySeventh)

        val json = Gson().toJson(fifthArrayList)
        putAppThirdEnglishFifthData(json)

    }


    private fun readFourthTrainTest(writableSheet: WritableSheet) {
        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)
        val arraySeventh = writableSheet.getColumn(7)

        readQuestionType(fourthArrayList, arrayZero)
        readFirstArray(fourthArrayList ?: return, arrayFirst)
        readSecondArray(fourthArrayList ?: return, arraySecond)
        readAnswerArray(fourthArrayList ?: return, arrayThird)
        readAnswerArray(fourthArrayList ?: return, arrayFourth)
        readAnswerArray(fourthArrayList ?: return, arrayFifth)
        readAnswerArray(fourthArrayList ?: return, arraySixth)
        readParseArray(fourthArrayList ?: return, arraySeventh)

        val json = Gson().toJson(fourthArrayList)
        putAppThirdEnglishFourthData(json)

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

        readQuestionType(thirdArrayList, arrayZero)
        readFirstArray(thirdArrayList ?: return, arrayFirst)
        readSecondArray(thirdArrayList ?: return, arraySecond)
        readAnswerArray(thirdArrayList ?: return, arrayThird)
        readAnswerArray(thirdArrayList ?: return, arrayFourth)
        readAnswerArray(thirdArrayList ?: return, arrayFifth)
        readAnswerArray(thirdArrayList ?: return, arraySixth)
        readParseArray(thirdArrayList ?: return, arraySeventh)

        val json = Gson().toJson(thirdArrayList)
        putAppThirdEnglishThirdData(json)

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

        readQuestionType(secondArrayList, arrayZero)
        readFirstArray(secondArrayList ?: return, arrayFirst)
        readSecondArray(secondArrayList ?: return, arraySecond)
        readAnswerArray(secondArrayList ?: return, arrayThird)
        readAnswerArray(secondArrayList ?: return, arrayFourth)
        readAnswerArray(secondArrayList ?: return, arrayFifth)
        readAnswerArray(secondArrayList ?: return, arraySixth)
        readParseArray(secondArrayList ?: return, arraySeventh)

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

        readQuestionType(firstArrayList, arrayZero)
        readFirstArray(firstArrayList ?: return, arrayFirst)
        readSecondArray(firstArrayList ?: return, arraySecond)
        readAnswerArray(firstArrayList ?: return, arrayThird)
        readAnswerArray(firstArrayList ?: return, arrayFourth)
        readAnswerArray(firstArrayList ?: return, arrayFifth)
        readAnswerArray(firstArrayList ?: return, arraySixth)
        readParseArray(firstArrayList ?: return, arraySeventh)

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
                    "单选题","词汇与结构", "辨别错误", "完形填空", "完型填空", "阅读理解" -> {
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
        arrayList: ArrayList<OpenAnswersBean>, arrayFirst: Array<Cell>
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
        arrayList: ArrayList<OpenAnswersBean>, arraySecond: Array<Cell>
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
        arrayList: ArrayList<OpenAnswersBean>, arrayAnswerList: Array<Cell>
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