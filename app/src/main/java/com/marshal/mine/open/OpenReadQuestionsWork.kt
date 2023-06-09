package com.marshal.mine.open

import android.util.Log
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswerBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.utils.FileUtils
import jxl.Cell
import jxl.write.WritableSheet
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import org.greenrobot.eventbus.EventBus

class OpenReadQuestionsWork :Runnable{

    override fun run() {
        try {
            readAppFunctionWork()

            readThought()

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
        EventBus.getDefault().postSticky(eventMsg)

        zzExcelCreator1.close()
    }

    /**
     * 思维导论读取
     */
    private fun readThought(){
        val file = FileUtils.copyAssetsResFile(
            "计算思维导论_2.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "计算思维导论_2.xls"
        )

        val zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file).openSheet(0)
        //读取单元格内容
        //读取单元格内容
        var writableSheet = zzExcelCreator1.writableSheet
        Log.d("TAG", "columns: 有${writableSheet.columns}列")
        Log.d("TAG", "rows: 有${writableSheet.rows}行")

        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)

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

        var indexSix = 0
        array2.mapIndexed { index, cell ->
            if (index >= 2) {
                data[indexSix].rightAnswer = cell.contents
                indexSix++
            }
        }

//        val json = Gson().toJson(data)
//        Log.d("TAG", "json:${json}")
        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 0x21
        eventMsg.questionList = data
        EventBus.getDefault().postSticky(eventMsg)

        zzExcelCreator1.close()
    }


    private fun readAppFunctionWork(){
        //如果文件不存在从程序中获取
        val file = FileUtils.copyAssetsResFile(
            "计算机应用基础.xls",
            "${FileUtils.app_cacheDir_path}/open/",
            "计算机应用基础.xls"
        )

        val zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file).openSheet(0)
        //读取单元格内容
        //读取单元格内容
        var writableSheet = zzExcelCreator1.writableSheet
       /* Log.d("TAG", "columns: 有${writableSheet.columns}列")
        Log.d("TAG", "rows: 有${writableSheet.rows}行")*/

        if (writableSheet.name == "单选题") {
            singleChoiceAnswer(writableSheet)
        }

        val zzExcelCreator2 = zzExcelCreator1.openSheet(1)
        writableSheet = zzExcelCreator2.writableSheet

        if (writableSheet.name == "多选题") {

            multipleChoiceAnswer(writableSheet)
        }

        val zzExcelCreator3 = zzExcelCreator1.openSheet(2)
        writableSheet = zzExcelCreator3.writableSheet

        if (writableSheet.name == "判断题") {
            estimateAnswer(writableSheet)
        }

        //别忘了close
        zzExcelCreator3.close()
        zzExcelCreator2.close()
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

        //val json = Gson().toJson(data)
        //Log.d("TAG", "json:${json}")
        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 0x11
        eventMsg.questionList = data
        EventBus.getDefault().postSticky(eventMsg)
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

        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 0x12
        eventMsg.questionList = data
        EventBus.getDefault().postSticky(eventMsg)
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

        /*val json = Gson().toJson(data)
        Log.d("TAG", "json:${json}")*/
        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 0x13
        eventMsg.questionList = data
        EventBus.getDefault().postSticky(eventMsg)
    }


}