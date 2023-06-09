package com.marshal.mine.open.works

import android.util.Log
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.utils.FileUtils
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import org.greenrobot.eventbus.EventBus

class OpenReadQuestionThoughtWork :Runnable{

    override fun run() {
        try {
            readThought()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        EventBus.getDefault().post(eventMsg)

        zzExcelCreator1.close()
    }





}