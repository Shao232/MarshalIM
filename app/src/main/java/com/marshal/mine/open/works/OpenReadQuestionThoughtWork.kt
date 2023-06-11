package com.marshal.mine.open.works

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.sharedata.CommitShareData
import com.marshal.utils.FileUtils
import getAppThoughtSingleData
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import org.greenrobot.eventbus.EventBus
import putAppThoughtSingleData

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

        var zzExcelCreator1 = ZzExcelCreator.getInstance().openExcel(file)
        //读取单元格内容
        //读取单元格内容
        zzExcelCreator1 = zzExcelCreator1.openSheet(0)
        val writableSheet = zzExcelCreator1.writableSheet
        Log.d("TAG", "columns: 有${writableSheet.columns}列")
        Log.d("TAG", "rows: 有${writableSheet.rows}行")

        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)

        val data = ArrayList<OpenAnswersBean>()

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

        val json = Gson().toJson(data)
        putAppThoughtSingleData(json)
        Log.d("TAG", "json:${getAppThoughtSingleData()}")

        val appThoughtSingleData = getAppThoughtSingleData()
        val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
        CommitShareData.thoughtSingleQuestions = Gson().fromJson(appThoughtSingleData,type)
        Log.d("TAG","save CommitShareData :${CommitShareData.thoughtSingleQuestions.size}")

//        Log.d("TAG", "json:${json}")
//        val eventMsg = OpenQuestionEventBean()
//        eventMsg.what = 0x21
//        eventMsg.questionList = data
//        EventBus.getDefault().post(eventMsg)

        zzExcelCreator1.close()
    }
}