package com.marshal.mine

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.marshal.IMPath.OPEN_QUESTION_BANK
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityOpenQuestionBankBinding
import com.marshal.mine.adapter.OpenQuestionAdapter
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.utils.FileUtils
import jxl.Cell
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Route(path = OPEN_QUESTION_BANK)
class OpenQuestionBankActivity : BaseViewActivity<ActivityOpenQuestionBankBinding>() {

    private var adapter: OpenQuestionAdapter? = null


    override fun getResLayoutBinding(): View? {
        binding = ActivityOpenQuestionBankBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        EventBus.getDefault().register(this)

        Thread(OpenReadQuestionRunnable()).start()

        var layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding?.recyclerList?.layoutManager = layoutManager
        adapter = OpenQuestionAdapter()
        binding?.recyclerList?.adapter = adapter

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusMessage(data:OpenQuestionEventBean){
        Log.d("TAG","eventBus收到消息了: ${data}")
        adapter?.addListAll(data.questionList as ArrayList<OpenAnswersBean>)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    inner class OpenReadQuestionRunnable : Runnable {
        override fun run() {

            val file = FileUtils.copyAssetsResFile(
                "计算机应用基础.xls", "${FileUtils.app_cacheDir_path}/open/", "计算机应用基础.xls"
            )

            val zzExcelCreator = ZzExcelCreator.getInstance().openExcel(file).openSheet(0)
            //读取单元格内容
            //读取单元格内容
            val writableSheet = zzExcelCreator.writableSheet
            Log.d("TAG", "columns: 有${writableSheet.columns}列")
            Log.d("TAG", "rows: 有${writableSheet.rows}行")

            val array1 = writableSheet.getColumn(0)
            val array2 = writableSheet.getColumn(1)
            val array3 = writableSheet.getColumn(2)
            val array4 = writableSheet.getColumn(3)
            val array5 = writableSheet.getColumn(4)
            val array6 = writableSheet.getColumn(5)

            var data = ArrayList<OpenAnswersBean>()

            var indexFirst = 0
            while (indexFirst < array1.size) {
                if (indexFirst >= 2) {
                    data.add(OpenAnswersBean(indexFirst,
                        array1[indexFirst].contents,
                    ))
                }
                indexFirst++
            }

            data = setAnswerList(array2,data,"A")
            data = setAnswerList(array3,data,"B")
            data = setAnswerList(array4,data,"C")
            data = setAnswerList(array5,data,"D")

            var indexSix = 0
            array6.mapIndexed { index, cell ->
                Log.d("TAG", "array6 cell:${cell.contents}")
                if(index >=2){
                    data[indexSix].rightAnswer = cell.contents
                    indexSix++
                }
            }

            val json = Gson().toJson(data)
            Log.d("TAG", "json:${json}")
            val eventMsg = OpenQuestionEventBean()
            eventMsg.questionList = data
            EventBus.getDefault().postSticky(eventMsg)


            //别忘了close
            zzExcelCreator.close()
        }

    }

    private fun setAnswerList(array:Array<Cell>,data:ArrayList<OpenAnswersBean>,answerTag:String):ArrayList<OpenAnswersBean>{
        var indexSecond = 0
        array.mapIndexed { index, cell ->
            Log.d("TAG", "array2 cell:${cell.contents}")
            if(index >=2){
                data[indexSecond].answerList?.add("$answerTag.${cell.contents}")
                indexSecond++
            }
        }
        return data
    }


}