package com.marshalim.moudle.open.question.fragment.third

import com.marshalim.moudle.open.question.fragment.OpenQuestionBaseFragment

class OpenSoftwareFragment : OpenQuestionBaseFragment()  {


    override fun getAdapter() {

    }


    override fun viewCreate() {
        super.viewCreate()

        if (hasIncludeToolbar) {
            setTitle("软件工程复习题")
        }
    }


}