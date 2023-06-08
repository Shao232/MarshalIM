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
import com.marshal.mine.dialog.OpenQuestionSelectDialog
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswerBean
import com.marshal.pojo.OpenAnswersBean
import com.marshal.utils.FileUtils
import jxl.Cell
import jxl.write.WritableSheet
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Route(path = OPEN_QUESTION_BANK)
class OpenQuestionBankActivity : BaseViewActivity<ActivityOpenQuestionBankBinding>() {

    private var adapter: OpenQuestionAdapter? = null

    private var singleQuestions = ArrayList<OpenAnswersBean>()
    private var multipleQuestions = ArrayList<OpenAnswersBean>()
    private var estimateQuestions = ArrayList<OpenAnswersBean>()

    private var layoutManager: LinearLayoutManager? = null

    companion object {
        /**
         * 应用基础
         */
        const val appFunction = 3

        /**
         * 思维导论
         */
        const val thought = 2

        /**
         * 程序设计
         */
        const val programDesign = 1
    }

    override fun hasToolbar(): Boolean {
        return true
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityOpenQuestionBankBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        EventBus.getDefault().register(this)

        if (hasIncludeToolbar) {
            tvTitle?.text = "计算机应用基础"
        }

        Thread(OpenReadQuestionRunnable()).start()

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerList?.layoutManager = layoutManager
        adapter = OpenQuestionAdapter()
        binding?.recyclerList?.adapter = adapter

    }

    override fun onClickMenu(view: View) {
        super.onClickMenu(view)
        val dialogFragment = OpenQuestionSelectDialog()
        dialogFragment.setOnDialogClickListener(object :
            OpenQuestionSelectDialog.QuestionDialogClickListener {
            override fun onClickFirstItem(view: View) {
                adapter?.itemList?.clear()
                adapter?.addListAll(singleQuestions)
                layoutManager?.scrollToPosition(0)
            }

            override fun onClickSecondItem(view: View) {
                adapter?.itemList?.clear()
                adapter?.addListAll(multipleQuestions)
                layoutManager?.scrollToPosition(0)

            }

            override fun onClickThirdItem(view: View) {
                adapter?.itemList?.clear()
                adapter?.addListAll(estimateQuestions)
                layoutManager?.scrollToPosition(0)
            }
        })

        dialogFragment.show(supportFragmentManager, "dialog_question")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusMessage(data: OpenQuestionEventBean) {
        Log.d("TAG", "eventBus收到消息了: ${data}")
        when (data.what) {
            1 -> {
                adapter?.itemList?.clear()
                singleQuestions = data.questionList as ArrayList<OpenAnswersBean>
                adapter?.addListAll(singleQuestions)
            }

            2 -> {
                multipleQuestions = data.questionList as ArrayList<OpenAnswersBean>
                /* adapter?.itemList?.clear()

                 adapter?.addListAll(multipleQuestions)*/

            }

            3 -> {
                estimateQuestions = data.questionList as ArrayList<OpenAnswersBean>
            }
        }

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    inner class OpenReadQuestionRunnable : Runnable {
        override fun run() {
            try {
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
                Log.d("TAG", "columns: 有${writableSheet.columns}列")
                Log.d("TAG", "rows: 有${writableSheet.rows}行")

                if (writableSheet.name == "单选题") {
                    singleChoiceAnswer(writableSheet)
                }

                val zzExcelCreator2 = zzExcelCreator1.openSheet(1)
                writableSheet = zzExcelCreator2.writableSheet

                if (writableSheet.name == "多选题") {
                    Log.d("TAG", "columns: 有${writableSheet.columns}列")
                    Log.d("TAG", "rows: 有${writableSheet.rows}行")
                    multipleChoiceAnswer(writableSheet)
                }

                val zzExcelCreator3 = zzExcelCreator1.openSheet(2)
                writableSheet = zzExcelCreator3.writableSheet

                if (writableSheet.name == "判断题") {
                    Log.d("TAG", "columns: 有${writableSheet.columns}列")
                    Log.d("TAG", "rows: 有${writableSheet.rows}行")
                    estimateAnswer(writableSheet)
                }

                //别忘了close
                zzExcelCreator3.close()
                zzExcelCreator2.close()
                zzExcelCreator1.close()


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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
        eventMsg.what = 1
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

    private fun multipleChoiceAnswer(writableSheet: WritableSheet) {
        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)
        val array3 = writableSheet.getColumn(2)
        val array4 = writableSheet.getColumn(3)
        val array5 = writableSheet.getColumn(4)
        val array6 = writableSheet.getColumn(5)
        val array7 = writableSheet.getColumn(6)

        /*  val size = writableSheet.columns
          var index = 0
          while(index < size) {
              val array = writableSheet.getColumn(index)
              Log.d("TAG","index ---- :${index}")
              for(item in array) {
                  Log.d("TAG","多选:${item.contents}")
              }
              index++
          }*/

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

        //val json = Gson().toJson(data)
        //Log.d("TAG", "json:${json}")
        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 2
        eventMsg.questionList = data
        EventBus.getDefault().postSticky(eventMsg)
    }

    private fun estimateAnswer(writableSheet: WritableSheet) {
        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)

//        val size = writableSheet.columns
      /*  var index = 0
        while (index < size) {
            val array = writableSheet.getColumn(index)
            Log.d("TAG", "index ---- :${index}")
            for (item in array) {
                Log.d("TAG", "判断:${item.contents}")
            }
            index++
        }*/

        val data = ArrayList<OpenAnswersBean>()

        var indexFirst = 0
        Log.d("TAG", "array1.size::${array1.size}")
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
        Log.d("TAG", "array2.size::${array2.size}")
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
        Log.d("TAG", "json:${json}")
        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 3
        eventMsg.questionList = data
        EventBus.getDefault().postSticky(eventMsg)
    }


}