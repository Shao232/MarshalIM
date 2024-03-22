package com.marshalim.moudle.open.question.works.third

import FileUtils
import com.google.gson.Gson
import com.marshalim.moudle.open.question.pojo.OpenAnswerBean
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import jxl.Cell
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import putAppThirdDatabaseData

/**
 * 读取数据库题库
 */
class OpenReadDatabaseWork : Runnable {
    override fun run() {
        synchronized(this) {
            try {
                readDataBaseFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun readDataBaseFile() {
        val file = FileUtils.copyAssetsResFile(
            "数据库2.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "数据库2.xls"
        )

        var zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file)

        zzExcelCreator1 = zzExcelCreator1.openSheet(0)
        val writableSheet = zzExcelCreator1.writableSheet

        val arrayZero = writableSheet.getColumn(0)
        val arrayFirst = writableSheet.getColumn(1)
        val arraySecond = writableSheet.getColumn(2)
        val arrayThird = writableSheet.getColumn(3)
        val arrayFourth = writableSheet.getColumn(4)
        val arrayFifth = writableSheet.getColumn(5)
        val arraySixth = writableSheet.getColumn(6)

        val arrayList: ArrayList<OpenAnswersBean> = ArrayList()
        readZeroArray(arrayZero, arrayList)

        readFirstArray(arrayList, arrayFirst)

        readSecondArray(arrayList, arraySecond)

        readAnswerArray(arrayList, arrayThird)

        readAnswerArray(arrayList, arrayFourth)

        readAnswerArray(arrayList, arrayFifth)

        readAnswerArray(arrayList, arraySixth)

        val json = Gson().toJson(arrayList)
        putAppThirdDatabaseData(json)

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

    private fun readZeroArray(
        arrayZero: Array<Cell>,
        arrayList: ArrayList<OpenAnswersBean>
    ) {
        var bean: OpenAnswersBean?
        for ((index, itemCell) in arrayZero.withIndex()) {
            if (itemCell.contents.isNullOrEmpty()) {
                continue
            }

            if ((itemCell.contents?.contentEquals("题型") == false)) {
                bean = OpenAnswersBean(index)
                if (itemCell.contents.equals("单选题")) {
                    bean.questionType = 1
                } else if (itemCell.contents.equals("填空题")) {
                    bean.questionType = 4
                }
                arrayList.add(bean)
            }
        }
    }

}