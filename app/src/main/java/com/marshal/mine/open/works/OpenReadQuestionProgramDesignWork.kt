package com.marshal.mine.open.works

import android.util.Log
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswerBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.utils.FileUtils
import jxl.Cell
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import org.greenrobot.eventbus.EventBus

class OpenReadQuestionProgramDesignWork :Runnable{

    override fun run() {
        try {
            readProgramDesign()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 程序设计读取
     */
    private fun readProgramDesign(){
        val file = FileUtils.copyAssetsResFile(
            "程序设计复习资料_2.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "程序设计复习资料_2.xls"
        )

        val zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file).openSheet(0)
        //读取单元格内容
        //读取单元格内容
        var writableSheet = zzExcelCreator1.writableSheet
        Log.d("TAG", "columns: 有${writableSheet.columns}列")
        Log.d("TAG", "rows: 有${writableSheet.rows}行")

        val array1 = writableSheet.getColumn(1)
        val array2 = writableSheet.getColumn(2)
        val array3 = writableSheet.getColumn(3)
        val array4 = writableSheet.getColumn(4)
        val array5 = writableSheet.getColumn(5)
        val array6 = writableSheet.getColumn(6)
        val array7 = writableSheet.getColumn(7)

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

        data = setAnswerList(array2, data, "A")
        data = setAnswerList(array3, data, "B")
        data = setAnswerList(array4, data, "C")
        data = setAnswerList(array5, data, "D")
        data = setAnswerList(array6, data, "E")

        var indexSix = 0
        array7.mapIndexed { index, cell ->
            if (index >= 2) {
                data[indexSix].rightAnswer = cell.contents
                indexSix++
            }
        }

       /* val json = Gson().toJson(data)
        Log.d("TAG", "json:${json}")*/
        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 0x31
        eventMsg.questionList = data
        EventBus.getDefault().post(eventMsg)

        zzExcelCreator1.close()
    }

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


}