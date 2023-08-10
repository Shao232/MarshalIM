package com.driving_school.home.practice

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.driving_school.R
import com.driving_school.bean.QuestionsBean
import com.driving_school.databinding.FragPracticeTestExamBinding
import com.driving_school.getDrivingCollectQuestion
import com.driving_school.getDrivingCorrectQuestion
import com.driving_school.getDrivingCorrectQuestionFourList
import com.driving_school.getDrivingCorrectQuestionThreeList
import com.driving_school.getDrivingErrorQuestion
import com.driving_school.getDrivingErrorQuestionFourList
import com.driving_school.getDrivingErrorQuestionThreeList
import com.driving_school.home.adapter.PracticeAdapter
import com.driving_school.putDrivingCollectQuestion
import com.driving_school.putDrivingCorrectQuestion
import com.driving_school.putDrivingCorrectQuestionFourList
import com.driving_school.putDrivingCorrectQuestionThreeList
import com.driving_school.putDrivingErrorQuestion
import com.driving_school.putDrivingErrorQuestionFourList
import com.driving_school.putDrivingErrorQuestionThreeList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.base_common.utils.GsonUtils

abstract class TestAndExamBaseFragment: BaseViewFragment<FragPracticeTestExamBinding>() {

    /**
     * 0 练习
     * 1 考试
     */
    var receiveType = 0
    /**
     * 1 科目1 4 科目4
     */
    var subjectType = 0

    var adapter: PracticeAdapter? = null

    //answerCorrect 正确回答的数量 answerError 错误回答的数量
    var answerCorrect: Int = 0
    var answerError: Int = 0

    //总题目数量
    var answerCount: Int = 0

    //当前练习时的位置
    var testCurrentPosition = 0
    var examCurrentPosition = 1

    //收藏集
    var collectQuestionList: ArrayList<QuestionsBean>? = ArrayList()
    //正确集
    var correctQuestionList: ArrayList<QuestionsBean>? = ArrayList()
    //科四正确集合
    var correctQuestionFourList: ArrayList<QuestionsBean>? = ArrayList()
    //科目1错题集
    var errorQuestionList: ArrayList<QuestionsBean>? = ArrayList()
    //科四错题集
    var errorQuestionFourList: ArrayList<QuestionsBean>? = ArrayList()
    //c3 正确集
    var correctQuestionThreeList:ArrayList<QuestionsBean>? = ArrayList()
    //c3 错题集
    var errorQuestionThreeList:ArrayList<QuestionsBean>? = ArrayList()



    //59分59秒 * 60秒 * 1000毫秒 + 59秒 = 3150秒，每隔1秒执行一次onTick方法
    var timer = object : CountDownTimer(60 * 60 * 1000, 1000L) {
        override fun onTick(millisUntilFinished: Long) {
            val minutes = (millisUntilFinished / (60 * 1000)) % 60
            val seconds = (millisUntilFinished % (60 * 1000)) / 1000
            binding?.tvTimerExam?.text = "${minutes}:${seconds}"
        }

        override fun onFinish() {
            Log.d("TAG", "考试结束 <<<<<<<<")
            //完成时 处理逻辑
            examResult()
        }
    }


    override fun getResLayoutId(): Int? = R.layout.frag_practice_test_exam

    override fun getResLayoutBinding(): View? {
        binding = FragPracticeTestExamBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (receiveType == 0) {
            //练习 倒计时隐藏
            binding?.lvnTimeSubject?.visibility = View.GONE
            binding?.lvnCollectionSubject?.visibility = View.VISIBLE
            binding?.lvnTestCorrectSubject?.visibility = View.VISIBLE
            binding?.lvnTestErrorSubject?.visibility = View.VISIBLE
            binding?.tvQuestionCount?.visibility = View.VISIBLE

        } else {
            binding?.lvnTimeSubject?.visibility = View.VISIBLE
            binding?.tvQuestionCount?.visibility = View.VISIBLE
        }

        if (receiveType == 1) {
            //模拟考试
            timer.start()
        }

        adapter = PracticeAdapter(receiveType)
        binding?.viewpagerSubject?.adapter = adapter
        //禁止用户滑动
        binding?.viewpagerSubject?.isUserInputEnabled = false
        binding?.viewpagerSubject?.offscreenPageLimit = 5

        adapter?.setOnNextQuestionSelect(object : PracticeAdapter.PracticeItemSelectClick {
            override fun onItemOneSelectClick(position: Int, direction: Int) {
                val count = if (adapter?.itemCount == 0) 0 else (adapter?.itemCount ?: 0) - 1
                if (direction == 1) {
                    val beforePosition = position - 1
                    binding?.viewpagerSubject?.currentItem =
                        if (beforePosition <= 0) 0 else beforePosition
                } else {
                    val nextPosition = position + 1
                    binding?.viewpagerSubject?.currentItem =
                        if (nextPosition > count) position else nextPosition

                }
                val currentItemPosition = binding?.viewpagerSubject?.currentItem ?: 0
                setShowQuestionCount(currentItemPosition)
                val bean = adapter?.itemList?.get(currentItemPosition)
                if (bean?.isHasCollection == true) {
                    binding?.ivCollectionSubject?.setImageResource(R.drawable.collectionsed_img)
                } else {
                    binding?.ivCollectionSubject?.setImageResource(R.drawable.my_collections_img)
                }

                //完成时 处理逻辑
                if (position == count) {
                    if (receiveType == 1) {
                        timer.cancel()
                        examResult()
                    }else {
                        //练习完成时 重新开始练习 将所有的数据已完成的状态改成未完成

                    }
                }
            }

            override fun onItemTwoSelectQuestion(position: Int, correct: Boolean) {
                //如果是考试，不需要保存
                if (receiveType == 1) return
                val bean = adapter?.itemList?.get(position)
                //当每一题判断是否回答正确
                when(subjectType){
                    1->{
                        if (correct) addCorrectQuestion(bean) else addErrorQuestion(bean)
                        answerCorrect = correctQuestionList?.size ?: 0
                        answerError = errorQuestionList?.size ?: 0
                    }
                    4->{
                        if (correct) addCorrectQuestionFour(bean) else addErrorQuestionFour(bean)
                        answerCorrect = correctQuestionFourList?.size ?: 0
                        answerError = errorQuestionFourList?.size ?: 0
                    }
                    3->{
                        if (correct) addCorrectQuestionThree(bean) else addErrorQuestionThree(bean)
                        answerCorrect = correctQuestionThreeList?.size ?: 0
                        answerError = errorQuestionThreeList?.size ?: 0
                    }
                }

                binding?.tvYesSubject?.text = "$answerCorrect"
                binding?.tvNoSubject?.text = "$answerError"
            }
        })

        binding?.lvnCollectionSubject?.setOnClickListener {
            //点击收藏时
            val position = binding?.viewpagerSubject?.currentItem ?: 0
            val bean = adapter?.itemList?.get(position)
            bean?.isHasCollection = bean?.isHasCollection == false
            Log.d("TAG", "处理前 collect:${collectQuestionList?.size}")
            if (bean?.isHasCollection == true) {
                binding?.ivCollectionSubject?.setImageResource(R.drawable.collectionsed_img)
                addCollectionList(bean)
            } else {
                binding?.ivCollectionSubject?.setImageResource(R.drawable.my_collections_img)
                val collectionListFindBean = collectQuestionList?.find {
                    it.question == bean?.question && it.explains == bean?.explains
                }
                collectQuestionList?.remove(collectionListFindBean)
                val collectionJson = GsonUtils.objToJson(collectQuestionList ?: "")
                putDrivingCollectQuestion(collectionJson)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    @SuppressLint("SetTextI18n")
    protected fun setShowQuestionCount(currentItemPosition: Int) {
        val showPosition = currentItemPosition + 1
        binding?.tvQuestionCount?.text = "$showPosition/$answerCount"
    }

    private fun examResult() {
        var examCount = 0
        adapter?.itemList?.forEach {
            var success = false
            if (it.selectItemAnswer.isNotEmpty()) {
                success = it.answer == it.selectItemAnswer
            }
            if(success){
                examCount++
            }
        }
        Log.d("TAG","测试结果: ${examCount}")
        ARouter.getInstance().build(DrivingRouterPath.Driving_Exam_Result_Path)
            .withInt("resultScore", examCount)
            .navigation()
        (mContext as? PracticeActivity)?.finish()
    }


    /**
     * 添加收藏集
     */
    protected fun addCollectionList(bean: QuestionsBean?) {
        var addSuccess = false
        if (collectQuestionList.isNullOrEmpty() && bean?.isHasCollection == true) {
            addSuccess = true
            bean.let { collectQuestionList?.add(it) }
        } else {
            val hasCollectionBean =
                collectQuestionList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasCollectionBean == null) {
                addSuccess = true
                bean?.let { collectQuestionList?.add(it) }
            }
        }

        if (addSuccess) {
            if (collectQuestionList?.isNotEmpty() == true) {
                val collectionJson = GsonUtils.objToJson(collectQuestionList ?: "")
                putDrivingCollectQuestion(collectionJson)
            }
        }
    }

    /**
     * 添加科目1的正确题目
     */
    protected fun addCorrectQuestion(bean: QuestionsBean?) {
        var addSuccess = false
        if (correctQuestionList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { correctQuestionList?.add(it) }
        } else {
            val hasErrorBean =
                correctQuestionList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { correctQuestionList?.add(it) }
            }
        }

        if (addSuccess) {
            if (correctQuestionList?.isNotEmpty() == true) {
                val correctJson = GsonUtils.objToJson(correctQuestionList ?: "")
                putDrivingCorrectQuestion(correctJson)
            }
        }
    }

    /**
     * 添加科四的正确题目
     */
    protected fun addCorrectQuestionFour(bean: QuestionsBean?) {
        var addSuccess = false
        if (correctQuestionFourList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { correctQuestionFourList?.add(it) }
        } else {
            val hasErrorBean =
                correctQuestionFourList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { correctQuestionFourList?.add(it) }
            }
        }

        if (addSuccess) {
            if (correctQuestionFourList?.isNotEmpty() == true) {
                val correctJson = GsonUtils.objToJson(correctQuestionFourList ?: "")
                putDrivingCorrectQuestionFourList(correctJson)
            }
        }
    }


    /**
     * 添加错题到错题集
     * 判断是否有重复
     */
    protected fun addErrorQuestion(bean: QuestionsBean?) {
        var addSuccess = false
        if (errorQuestionList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { errorQuestionList?.add(it) }
        } else {
            val hasErrorBean =
                errorQuestionList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { errorQuestionList?.add(it) }
            }
        }

        if (addSuccess) {
            if (errorQuestionList?.isNotEmpty() == true) {
                val errorJson = GsonUtils.objToJson(errorQuestionList ?: "")
                putDrivingErrorQuestion(errorJson)
            }
        }
    }

    protected fun addErrorQuestionFour(bean: QuestionsBean?) {
        var addSuccess = false
        if (errorQuestionFourList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { errorQuestionFourList?.add(it) }
        } else {
            val hasErrorBean =
                errorQuestionFourList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { errorQuestionFourList?.add(it) }
            }
        }

        if (addSuccess) {
            if (errorQuestionFourList?.isNotEmpty() == true) {
                val errorJson = GsonUtils.objToJson(errorQuestionFourList ?: "")
                putDrivingErrorQuestionFourList(errorJson)
            }
        }
    }

    protected fun addCorrectQuestionThree(bean: QuestionsBean?) {
        var addSuccess = false
        if (correctQuestionThreeList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { correctQuestionThreeList?.add(it) }
        } else {
            val hasErrorBean =
                correctQuestionThreeList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { correctQuestionThreeList?.add(it) }
            }
        }

        if (addSuccess) {
            if (correctQuestionThreeList?.isNotEmpty() == true) {
                val correctJson = GsonUtils.objToJson(correctQuestionThreeList ?: "")
                putDrivingCorrectQuestionThreeList(correctJson)
            }
        }
    }

    protected fun addErrorQuestionThree(bean: QuestionsBean?) {
        var addSuccess = false
        if (errorQuestionThreeList.isNullOrEmpty()) {
            addSuccess = true
            bean?.let { errorQuestionThreeList?.add(it) }
        } else {
            val hasErrorBean =
                errorQuestionThreeList?.find { it.question == bean?.question && it.explains == bean?.explains }
            if (hasErrorBean == null) {
                addSuccess = true
                bean?.let { errorQuestionThreeList?.add(it) }
            }
        }

        if (addSuccess) {
            if (errorQuestionThreeList?.isNotEmpty() == true) {
                val errorJson = GsonUtils.objToJson(errorQuestionThreeList ?: "")
                putDrivingErrorQuestionThreeList(errorJson)
            }
        }
    }


    /**
     * 解析正确题的集合
     */
    protected fun parseCorrectQuestionList() {
        val correctJson = getDrivingCorrectQuestion()
        if (correctJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(correctJson, type)
        correctQuestionList?.clear()
        correctQuestionList?.addAll(dataList)
        answerCorrect = correctQuestionList?.size ?: 0
        binding?.tvYesSubject?.text = "$answerCorrect"
    }

    protected fun parseCorrectQuestionListFour() {
        val correctJson = getDrivingCorrectQuestionFourList()
        if (correctJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(correctJson, type)
        correctQuestionFourList?.clear()
        correctQuestionFourList?.addAll(dataList)
        answerCorrect = correctQuestionFourList?.size ?: 0
        binding?.tvYesSubject?.text = "$answerCorrect"
    }

    /**
     * 解析错题集
     */
    protected fun parseErrorQuestionList() {
        val errorQuestionJson = getDrivingErrorQuestion()
        if (errorQuestionJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(errorQuestionJson, type)
        errorQuestionList?.clear()
        errorQuestionList?.addAll(dataList)
        answerError = errorQuestionList?.size ?: 0
        binding?.tvNoSubject?.text = "$answerError"
    }

    protected fun parseErrorQuestionListFour() {
        val errorQuestionJson = getDrivingErrorQuestionFourList()
        if (errorQuestionJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(errorQuestionJson, type)
        errorQuestionFourList?.clear()
        errorQuestionFourList?.addAll(dataList)
        answerError = errorQuestionFourList?.size ?: 0
        binding?.tvNoSubject?.text = "$answerError"
    }

    protected fun parseCorrectQuestionListThree() {
        val correctJson = getDrivingCorrectQuestionThreeList()
        if (correctJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(correctJson, type)
        correctQuestionThreeList?.clear()
        correctQuestionThreeList?.addAll(dataList)
        answerCorrect = correctQuestionThreeList?.size ?: 0
        binding?.tvYesSubject?.text = "$answerCorrect"
    }

    protected fun parseErrorQuestionListThree() {
        val errorQuestionJson = getDrivingErrorQuestionThreeList()
        if (errorQuestionJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(errorQuestionJson, type)
        errorQuestionThreeList?.clear()
        errorQuestionThreeList?.addAll(dataList)
        answerError = errorQuestionThreeList?.size ?: 0
        binding?.tvNoSubject?.text = "$answerError"
    }

    /**
     * 解析收藏集
     */
    protected fun parseCollectQuestionList() {
        val collectJson = getDrivingCollectQuestion()
        if (collectJson.isEmpty()) return
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestionsBean>>() {}.type
        val dataList: ArrayList<QuestionsBean> = gson.fromJson(collectJson, type)
        collectQuestionList?.clear()
        collectQuestionList?.addAll(dataList)
    }

}