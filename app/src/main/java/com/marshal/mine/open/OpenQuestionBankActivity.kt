package com.marshal.mine.open

import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.OPEN_QUESTION_BANK
import com.marshal.R
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityOpenQuestionBankBinding
import com.marshal.mine.dialog.OpenQuestionSelectDialog
import com.marshal.mine.event.OpenQuestionEventBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Route(path = OPEN_QUESTION_BANK)
class OpenQuestionBankActivity : BaseViewActivity<ActivityOpenQuestionBankBinding>() {

    /**
     * 当前的课程 第一次进入时默认应用基础 ,后续通过点击title切换成思维导论或者程序设计
     */
    private var currentCourse = appFunction

    companion object {
        /**
         * 应用基础
         */
        const val appFunction = 3
        const val firstTitle = "计算机应用基础"

        /**
         * 思维导论
         */
        const val thought = 2
        const val secondTitle = "计算思维导论"

        /**
         * 程序设计
         */
        const val programDesign = 1
        const val thirdTitle = "程序设计复习资料"
    }

    private var fragmentManager: FragmentManager? = null
    private var appFunctionFragment: OpenAppFunctionFragment? = null
    private var thoughtFragment: OpenThoughtFragment? = null
    private var programDesignFragment: OpenProgramDesignFragment? = null

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
            tvTitle?.text = firstTitle
            ivDown?.visibility = View.VISIBLE
            clnTitleLayout?.setOnClickListener {
                val dialogFragment = OpenQuestionSelectDialog(firstTitle, secondTitle, thirdTitle)
                dialogFragment.setOnDialogClickListener(object :
                    OpenQuestionSelectDialog.QuestionDialogClickListener {
                    override fun onClickFirstItem(view: View) {
                        currentCourse = appFunction
                        tvTitle?.text = firstTitle

                        if (appFunctionFragment?.isAdded == true) {
                            fragmentManager?.beginTransaction()?.hide(thoughtFragment ?: return)
                                ?.hide(programDesignFragment ?: return)
                                ?.show(appFunctionFragment ?: return)
                                ?.commitNowAllowingStateLoss()
                        }

                    }

                    override fun onClickSecondItem(view: View) {
                        currentCourse = thought
                        tvTitle?.text = secondTitle

                        if (thoughtFragment?.isAdded == true) {
                            fragmentManager?.beginTransaction()?.hide(appFunctionFragment ?: return)
                                ?.hide(programDesignFragment ?: return)
                                ?.show(thoughtFragment ?: return)
                                ?.commitNowAllowingStateLoss()
                        }else {
                            fragmentManager?.beginTransaction()?.add(
                                R.id.frame_layout, thoughtFragment
                                    ?: return, "fragment_thought")?.commitNowAllowingStateLoss()
                        }

                    }

                    override fun onClickThirdItem(view: View) {
                        currentCourse = programDesign
                        tvTitle?.text = thirdTitle

                        if (programDesignFragment?.isAdded == true) {
                            fragmentManager?.beginTransaction()?.hide(appFunctionFragment ?: return)
                                ?.hide(thoughtFragment ?: return)
                                ?.show(programDesignFragment ?: return)
                                ?.commitNowAllowingStateLoss()
                        }else {
                            fragmentManager?.beginTransaction()?.add(
                                R.id.frame_layout, programDesignFragment
                                    ?: return, "fragment_thought")?.commitNowAllowingStateLoss()
                        }

                    }
                })
                dialogFragment.show(supportFragmentManager, "dialog_title")
            }
        }

        Thread(OpenReadQuestionsWork()).start()

        appFunctionFragment = OpenAppFunctionFragment()
        thoughtFragment = OpenThoughtFragment()
        programDesignFragment = OpenProgramDesignFragment()

        fragmentManager = supportFragmentManager
        fragmentManager?.beginTransaction()?.add(
            R.id.frame_layout, appFunctionFragment ?: return,
            "fragment_appFunction"
        )?.commitNowAllowingStateLoss()

    }

    override fun onClickMenu(view: View) {
        super.onClickMenu(view)

        if(currentCourse == appFunction) {
            val dialogFragment = OpenQuestionSelectDialog("单选题", "多选题", "判断题")
            dialogFragment.setOnDialogClickListener(object :
                OpenQuestionSelectDialog.QuestionDialogClickListener {
                override fun onClickFirstItem(view: View) {
                    //通知fragment更新
                    appFunctionFragment?.updateList(1)
                }

                override fun onClickSecondItem(view: View) {
                    //通知fragment更新
                    appFunctionFragment?.updateList(2)
                }

                override fun onClickThirdItem(view: View) {
                    //通知fragment更新
                    appFunctionFragment?.updateList(3)
                }
            })
            dialogFragment.show(supportFragmentManager, "dialog_question")
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusMessage(data: OpenQuestionEventBean) {
        Log.d("TAG", "eventBus收到消息了: ")

        when (data.what) {
            0x11 -> {
                appFunctionFragment?.addSingleQuestionsShow(data)
            }

            0x12 -> {
                appFunctionFragment?.setMultipleQuestionList(data)
            }

            0x13 -> {
                appFunctionFragment?.setEstimateQuestionList(data)
            }
            0x21 ->{
                thoughtFragment?.addSingleQuestionsShow(data)
            }
            0x31 ->{
                programDesignFragment?.addSingleQuestionsShow(data)
            }
        }

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

  /*  *//**
     * 读取计算机应用基础文件
     *//*
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

    *//**
     * 单选题
     *//*
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

    *//**
     * 将答题的题目集合到arraylist中
     *//*
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

    *//**
     * 多选题
     *//*
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

        //val json = Gson().toJson(data)
        //Log.d("TAG", "json:${json}")
        val eventMsg = OpenQuestionEventBean()
        eventMsg.what = 2
        eventMsg.questionList = data
        EventBus.getDefault().postSticky(eventMsg)
    }

    *//**
     * 判断题
     *//*
    private fun estimateAnswer(writableSheet: WritableSheet) {
        val array1 = writableSheet.getColumn(0)
        val array2 = writableSheet.getColumn(1)

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
*/

}