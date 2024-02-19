package com.marshalim.moudle.open.question.fragment.second

import com.marshalim.moudle.open.question.fragment.OpenQuestionBaseFragment

class OpenOSFragment: OpenQuestionBaseFragment() {
    override fun getAdapter() {

    }

    override fun viewCreate() {
        super.viewCreate()

        if (hasIncludeToolbar) {
            setTitle("操作系统")
        }
    }

}