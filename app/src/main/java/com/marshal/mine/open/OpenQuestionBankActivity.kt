package com.marshal.mine.open

import android.view.View
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.OPEN_QUESTION_BANK
import com.marshal.R
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityOpenQuestionBankBinding
import com.marshal.mine.dialog.OpenQuestionSelectDialog


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

        if (hasIncludeToolbar) {
            setTitle(firstTitle)
            ivMenu?.visibility = View.VISIBLE
            ivDown?.visibility = View.VISIBLE
            clnTitleLayout?.setOnClickListener {
                val dialogFragment = OpenQuestionSelectDialog(firstTitle, secondTitle, thirdTitle)
                dialogFragment.setOnDialogClickListener(object :
                    OpenQuestionSelectDialog.QuestionDialogClickListener {
                    override fun onClickFirstItem(view: View) {
                        currentCourse = appFunction
                        setTitle(firstTitle)

                        if (appFunctionFragment?.isAdded == true) {
                            fragmentManager?.beginTransaction()
                                ?.hide(thoughtFragment ?: return)
                                ?.hide(programDesignFragment ?: return)
                                ?.show(appFunctionFragment ?: return)
                                ?.commitNowAllowingStateLoss()
                        }
                    }

                    override fun onClickSecondItem(view: View) {
                        currentCourse = thought
                        setTitle(secondTitle)

                        if (thoughtFragment?.isAdded == true) {
                            fragmentManager?.beginTransaction()
                                ?.hide(appFunctionFragment ?: return)
                                ?.hide(programDesignFragment ?: return)
                                ?.show(thoughtFragment ?: return)
                                ?.commitNowAllowingStateLoss()
                        }else {
                            fragmentManager?.beginTransaction()
                                ?.add(R.id.frame_layout, thoughtFragment
                                    ?: return, "fragment_thought")?.commitNowAllowingStateLoss()
                        }

                    }

                    override fun onClickThirdItem(view: View) {
                        currentCourse = programDesign
                        setTitle(thirdTitle)
                        if (programDesignFragment?.isAdded == true) {
                            fragmentManager?.beginTransaction()
                                ?.hide(appFunctionFragment ?: return)
                                ?.hide(thoughtFragment ?: return)
                                ?.show(programDesignFragment ?: return)
                                ?.commitNowAllowingStateLoss()
                        }else {
                            fragmentManager?.beginTransaction()
                                ?.add(R.id.frame_layout, programDesignFragment
                                    ?: return, "fragment_programDesign")?.commitNowAllowingStateLoss()
                        }

                    }
                })
                dialogFragment.show(supportFragmentManager, "dialog_title")
            }
        }


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


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}