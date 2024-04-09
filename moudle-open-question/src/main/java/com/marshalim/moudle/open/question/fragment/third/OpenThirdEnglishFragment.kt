package com.marshalim.moudle.open.question.fragment.third

import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshalim.moudle.open.question.R
import com.marshalim.moudle.open.question.databinding.FragmentOpenThirdEnglishBinding
import com.marshalim.moudle.open.question.dialog.OpenQuestionSelectDialog
import com.marshalim.moudle.open.question.fragment.third.adapter.OpenThirdEnglishAdapter
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean
import getAppThirdEnglishEighthData
import getAppThirdEnglishFifthData
import getAppThirdEnglishFirstData
import getAppThirdEnglishFourthData
import getAppThirdEnglishNinthData
import getAppThirdEnglishSecondData
import getAppThirdEnglishSeventhData
import getAppThirdEnglishSixthData
import getAppThirdEnglishTenthData
import getAppThirdEnglishThirdData

class OpenThirdEnglishFragment : BaseViewFragment<FragmentOpenThirdEnglishBinding>() {

    private val englishAdapter: OpenThirdEnglishAdapter = OpenThirdEnglishAdapter()
    private var appThirdEnglishFirstList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishSecondList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishThirdList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishFourthList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishFifthList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishSixthList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishSevenList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishEightList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishNinthList = ArrayList<OpenAnswersBean>()
    private var appThirdEnglishTenthList = ArrayList<OpenAnswersBean>()

    override fun hasToolbar(): Boolean = true
    override fun getResLayoutId(): Int? = R.layout.fragment_open_third_english

    override fun getResLayoutBinding(): View? {
        binding = FragmentOpenThirdEnglishBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if (hasIncludeToolbar) {
            setTitle("大学英语复习题")
        }
        ivMenu?.visibility = View.VISIBLE

        /*
          if (appThirdSoftwareData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            appThirdSoftwareList = Gson().fromJson(appThirdSoftwareData, type)
        }
         */

        parseJsonData()

        englishAdapter.itemList.addAll(appThirdEnglishFirstList)

        Log.d("TAG", "数据源 :${englishAdapter.itemList?.size}")

        binding?.viewPager?.adapter = englishAdapter
    }

    private fun parseJsonData() {
        val appThirdEnglishFirstListJson = getAppThirdEnglishFirstData()
        val appThirdEnglishSecondListJson = getAppThirdEnglishSecondData()
        val appThirdEnglishThirdListJson = getAppThirdEnglishThirdData()
        val appThirdEnglishFourthListJson = getAppThirdEnglishFourthData()
        val appThirdEnglishFifthListJson = getAppThirdEnglishFifthData()
        val appThirdEnglishSixthListJson = getAppThirdEnglishSixthData()
        val appThirdEnglishSeventhListJson = getAppThirdEnglishSeventhData()
        val appThirdEnglishEighthListJson = getAppThirdEnglishEighthData()
        val appThirdEnglishNinthListJson = getAppThirdEnglishNinthData()
        val appThirdEnglishTenthListJson = getAppThirdEnglishTenthData()

        appThirdEnglishFirstList = parseToArrayList(appThirdEnglishFirstListJson)
        appThirdEnglishSecondList = parseToArrayList(appThirdEnglishSecondListJson)
        appThirdEnglishThirdList = parseToArrayList(appThirdEnglishThirdListJson)
        appThirdEnglishFourthList = parseToArrayList(appThirdEnglishFourthListJson)
        appThirdEnglishFifthList = parseToArrayList(appThirdEnglishFifthListJson)
        appThirdEnglishSixthList = parseToArrayList(appThirdEnglishSixthListJson)
        appThirdEnglishSevenList = parseToArrayList(appThirdEnglishSeventhListJson)
        appThirdEnglishEightList = parseToArrayList(appThirdEnglishEighthListJson)
        appThirdEnglishNinthList = parseToArrayList(appThirdEnglishNinthListJson)
        appThirdEnglishTenthList = parseToArrayList(appThirdEnglishTenthListJson)

    }

    private fun parseToArrayList(json:String?):ArrayList<OpenAnswersBean>{
        var arraylist = ArrayList<OpenAnswersBean>()
        if (json?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            arraylist = Gson().fromJson(json, type)
        }
        return arraylist
    }

    override fun onClickMenu(view: View) {
        super.onClickMenu(view)
        val dialogFragment = OpenQuestionSelectDialog(
            arrayListOf(
                "实训1",
                "实训2",
                "实训3",
                "实训4",
                "实训5",
                "实训6",
                "实训7",
                "实训8",
                "实训9",
                "实训10"
            )
        )
        dialogFragment.setOnDialogClickListener(object :
            OpenQuestionSelectDialog.QuestionDialogClickListener {
            override fun onClickTitleContent(title: String) {
                englishAdapter.itemList.clear()

                when (title) {
                    "实训1" -> {
                        englishAdapter.addListAll(appThirdEnglishFirstList)
                    }

                    "实训2" -> {
                        englishAdapter.addListAll(appThirdEnglishSecondList)
                    }

                    "实训3" -> {
                        englishAdapter.addListAll(appThirdEnglishThirdList)
                    }

                    "实训4" -> {
                        englishAdapter.addListAll(appThirdEnglishFourthList)
                    }

                    "实训5" -> {
                        englishAdapter.addListAll(appThirdEnglishFifthList)
                    }

                    "实训6" -> {
                        englishAdapter.addListAll(appThirdEnglishSixthList)
                    }

                    "实训7" -> {
                        englishAdapter.addListAll(appThirdEnglishSevenList)
                    }

                    "实训8" -> {
                        englishAdapter.addListAll(appThirdEnglishEightList)
                    }

                    "实训9" -> {
                        englishAdapter.addListAll(appThirdEnglishNinthList)
                    }

                    "实训10" -> {
                        englishAdapter.addListAll(appThirdEnglishTenthList)
                    }
                }

                //重新从第一页开始
                binding?.viewPager?.currentItem = 0
            }
        })
        dialogFragment.show(childFragmentManager, "third_english")
    }

}