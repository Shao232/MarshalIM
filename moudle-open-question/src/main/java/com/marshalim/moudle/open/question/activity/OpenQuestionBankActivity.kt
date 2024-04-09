package com.marshalim.moudle.open.question.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshalim.moudle.open.question.adapter.TextContentAdapter
import com.marshalim.moudle.open.question.databinding.ActivityOpenQuestionBankBinding
import com.marshalim.moudle.open.question.dialog.OpenQuestionSelectDialog
import com.marshalim.moudle.open.question.fragment.first.OpenAppFunctionFragment
import com.marshalim.moudle.open.question.fragment.first.OpenProgramDesignFragment
import com.marshalim.moudle.open.question.fragment.first.OpenThoughtFragment
import com.marshalim.moudle.open.question.fragment.second.OpenSecondEnglishFragment
import com.marshalim.moudle.open.question.fragment.third.OpenDatabaseFragment
import com.marshalim.moudle.open.question.fragment.third.OpenSoftwareFragment
import com.marshalim.moudle.open.question.fragment.third.OpenThirdEnglishFragment
import com.marshalim.moudle.open.question.utils.OpenFirstContent
import com.marshalim.moudle.open.question.utils.OpenFourthContent
import com.marshalim.moudle.open.question.utils.OpenQuestionRouter
import com.marshalim.moudle.open.question.utils.OpenSecondContent
import com.marshalim.moudle.open.question.utils.OpenThirdContent


@Route(path = OpenQuestionRouter.OPEN_QUESTION_BANK)
class OpenQuestionBankActivity : BaseViewActivity<ActivityOpenQuestionBankBinding>() {

    /**
     * 当前的课程 第一次进入时默认应用基础 ,后续通过点击title切换成思维导论或者程序设计
     */
//    private var currentCourse = appFunction

//    companion object {
//        /**
//         * 应用基础
//         */
//        const val appFunction = 3
//        const val firstTitle = "计算机应用基础"
//
//        /**
//         * 思维导论
//         */
//        const val thought = 2
//        const val secondTitle = "计算思维导论"
//
//        /**
//         * 程序设计
//         */
//        const val programDesign = 1
//        const val thirdTitle = "程序设计复习资料"
//
//        const val appSecondEnglish = 4
//        const val fourTitle = "大学英语(3)"
//    }

//    private var fragmentManager: FragmentManager? = null
//    private var appFunctionFragment: OpenAppFunctionFragment? = null
//    private var thoughtFragment: OpenThoughtFragment? = null
//    private var programDesignFragment: OpenProgramDesignFragment? = null
//    private var appSecondEnglishFragment: OpenSecondEnglishFragment? = null
//    private var currentShowFragment:Fragment? = null

    private val adapter: TextContentAdapter = TextContentAdapter()

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityOpenQuestionBankBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("开大题库")
            clnTitleLayout?.setOnClickListener {
//                val dialogFragment = OpenQuestionSelectDialog(
//                    arrayListOf(
//                        "第一", "第二"
//                    )
//                )
//                dialogFragment.setOnDialogClickListener(object :
//                    OpenQuestionSelectDialog.QuestionDialogClickListener {
//                    override fun onClickTitleContent(title: String) {
//
//                    }
//                })
//                dialogFragment.show(supportFragmentManager, "dialog_title")
            }
        }


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerList?.layoutManager = layoutManager
        binding?.recyclerList?.adapter = adapter

        adapter.itemList.add(OpenFirstContent)
        adapter.itemList.add(OpenSecondContent)
        adapter.itemList.add(OpenThirdContent)
        adapter.itemList.add(OpenFourthContent)


        adapter.setAdapterItemOnClickListener(object : AdapterItemOnClickListener<String> {
            override fun onClick(view: View, bean: String) {
                super.onClick(view, bean)
                var questionList: ArrayList<String> = arrayListOf()
                when (bean) {
                    OpenFirstContent -> {
                        questionList = arrayListOf("程序设计", "计算机思维导论", "计算机应用基础")
                    }

                    OpenSecondContent -> {
                        questionList = arrayListOf("大学英语2")
                    }

                    OpenThirdContent -> {
                        questionList = arrayListOf("软件工程复习题", "数据库及原理","大学学位英语")
                    }

                    OpenFourthContent -> {
                        return
                    }
                }

                val dialogFragment = OpenQuestionSelectDialog(questionList)
                dialogFragment.setOnDialogClickListener(object :
                    OpenQuestionSelectDialog.QuestionDialogClickListener {
                    override fun onClickTitleContent(title: String) {
                        when (title) {
                            "程序设计" -> {
                                OpenFragmentActivity.startFragment(
                                    this@OpenQuestionBankActivity,
                                    OpenProgramDesignFragment::class.java.simpleName
                                )
                            }

                            "计算机思维导论" -> {
                                OpenFragmentActivity.startFragment(
                                    this@OpenQuestionBankActivity,
                                    OpenThoughtFragment::class.java.simpleName
                                )
                            }

                            "计算机应用基础" -> {
                                OpenFragmentActivity.startFragment(
                                    this@OpenQuestionBankActivity,
                                    OpenAppFunctionFragment::class.java.simpleName
                                )
                            }
//                            "操作系统" -> {
//                                OpenFragmentActivity.startFragment(
//                                    this@OpenQuestionBankActivity,
//                                    OpenOSFragment()
//                                )
//                            }
                            "大学英语2" -> {
                                OpenFragmentActivity.startFragment(
                                    this@OpenQuestionBankActivity,
                                    OpenSecondEnglishFragment::class.java.simpleName
                                )
                            }

                            "软件工程复习题" -> {
                                OpenFragmentActivity.startFragment(
                                    this@OpenQuestionBankActivity,
                                    OpenSoftwareFragment::class.java.simpleName
                                )
                            }

                            "数据库及原理" -> {
                                OpenFragmentActivity.startFragment(
                                    this@OpenQuestionBankActivity,
                                    OpenDatabaseFragment::class.java.simpleName
                                )
                            }
                            "大学学位英语"->{
                                OpenFragmentActivity.startFragment(
                                    this@OpenQuestionBankActivity,
                                    OpenThirdEnglishFragment::class.java.simpleName
                                )
                            }
                        }
                    }

                })
                dialogFragment.show(supportFragmentManager, "dialog_question")
            }
        })

    }

    override fun onClickMenu(view: View) {
        super.onClickMenu(view)

        val questionList = arrayListOf("单选题", "多选题", "判断题")
        val dialogFragment = OpenQuestionSelectDialog(questionList)
        dialogFragment.setOnDialogClickListener(object :
            OpenQuestionSelectDialog.QuestionDialogClickListener {
            override fun onClickTitleContent(title: String) {
            }
        })

        dialogFragment.show(supportFragmentManager, "dialog_question")
    }



}