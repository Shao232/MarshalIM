package com.marshalim.moudle.open.question

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshalim.moudle.open.question.databinding.ActivityOpenQuestionBankBinding
import com.marshalim.moudle.open.question.dialog.OpenQuestionSelectDialog


@Route(path = OpenQuestionRouter.OPEN_QUESTION_BANK)
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

        const val appSecondEnglish = 4
        const val fourTitle = "大学英语(3)"
    }

    private var fragmentManager: FragmentManager? = null
    private var appFunctionFragment: OpenAppFunctionFragment? = null
    private var thoughtFragment: OpenThoughtFragment? = null
    private var programDesignFragment: OpenProgramDesignFragment? = null
    private var appSecondEnglishFragment: OpenSecondEnglishFragment? = null
    private var currentShowFragment:Fragment? = null

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
                val dialogFragment = OpenQuestionSelectDialog(
                    arrayListOf(
                        firstTitle, secondTitle,
                        thirdTitle, fourTitle
                    )
                )
                dialogFragment.setOnDialogClickListener(object :
                    OpenQuestionSelectDialog.QuestionDialogClickListener {
                    override fun onClickTitleContent(title: String) {
                        setTitle(title)
                        when (title) {
                            firstTitle -> {
                                currentCourse = appFunction
                                showAndSwitchFragment(appFunctionFragment, firstTitle)
                            }

                            secondTitle -> {
                                currentCourse = thought
                                showAndSwitchFragment(thoughtFragment, secondTitle)
                            }

                            thirdTitle -> {
                                currentCourse = programDesign
                                showAndSwitchFragment(programDesignFragment, thirdTitle)
                            }

                            fourTitle -> {
                                currentCourse = appSecondEnglish
                                showAndSwitchFragment(appSecondEnglishFragment, fourTitle)
                            }
                        }
                    }
                })
                dialogFragment.show(supportFragmentManager, "dialog_title")
            }
        }


        appFunctionFragment = OpenAppFunctionFragment()
        thoughtFragment = OpenThoughtFragment()
        programDesignFragment = OpenProgramDesignFragment()
        appSecondEnglishFragment = OpenSecondEnglishFragment()

        fragmentManager = supportFragmentManager
        fragmentManager?.beginTransaction()?.add(
            R.id.frame_layout, appFunctionFragment ?: return,
            "fragment_appFunction"
        )?.commitNowAllowingStateLoss()
        currentShowFragment = appFunctionFragment

    }

    override fun onClickMenu(view: View) {
        super.onClickMenu(view)
        if (currentCourse == appFunction) {
            val questionList = arrayListOf("单选题", "多选题", "判断题")
            val dialogFragment = OpenQuestionSelectDialog(questionList)
            dialogFragment.setOnDialogClickListener(object :OpenQuestionSelectDialog.QuestionDialogClickListener{
                override fun onClickTitleContent(title: String) {
                    when(title){
                        questionList[0]->{
                            appFunctionFragment?.updateList(1)
                        }
                        questionList[1]->{
                            appFunctionFragment?.updateList(2)
                        }
                        questionList[2]->{
                            appFunctionFragment?.updateList(3)
                        }
                    }
                }
            })

            dialogFragment.show(supportFragmentManager, "dialog_question")
        }
    }

    private fun showAndSwitchFragment(needShowFragment:Fragment?,title:String){
        if(currentShowFragment == needShowFragment) {
            return
        }
        currentShowFragment = needShowFragment

        if(needShowFragment?.isAdded == false) {
            fragmentManager?.beginTransaction()?.add(R.id.frame_layout,needShowFragment)?.commitNowAllowingStateLoss()
            return
        }

        when(title) {
            firstTitle ->{
                if(needShowFragment?.isAdded == true) {
                    fragmentManager?.beginTransaction()
                        ?.hide(thoughtFragment?:return)
                        ?.hide(programDesignFragment?:return)
                        ?.hide(appSecondEnglishFragment?:return)
                        ?.show(needShowFragment?:return)
                        ?.commitNowAllowingStateLoss()
                }
            }
            secondTitle ->{
                if(needShowFragment?.isAdded == true) {
                    fragmentManager?.beginTransaction()
                        ?.hide(appFunctionFragment?:return)
                        ?.hide(programDesignFragment?:return)
                        ?.hide(appSecondEnglishFragment?:return)
                        ?.show(needShowFragment)
                        ?.commitNowAllowingStateLoss()
                }

            }
            thirdTitle ->{
                if(needShowFragment?.isAdded == true) {
                    fragmentManager?.beginTransaction()
                        ?.hide(appFunctionFragment?:return)
                        ?.hide(thoughtFragment?:return)
                        ?.hide(appSecondEnglishFragment?:return)
                        ?.show(needShowFragment)
                        ?.commitNowAllowingStateLoss()
                }
            }
            fourTitle ->{
                if(needShowFragment?.isAdded == true) {
                    fragmentManager?.beginTransaction()
                        ?.hide(appFunctionFragment?:return)
                        ?.hide(thoughtFragment?:return)
                        ?.hide(programDesignFragment?:return)
                        ?.show(needShowFragment)
                        ?.commitNowAllowingStateLoss()
                }
            }
        }
    }


}