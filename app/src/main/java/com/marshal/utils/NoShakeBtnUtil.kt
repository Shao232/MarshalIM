package com.marshal.utils

/**
 * 防止按钮多次点击
 */
object NoShakeBtnUtil {
    private var lastClickTime: Long = 0
    private const val DIFF: Long = 1000
    private var lastButtonId = -1

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    val isFastDoubleClick: Boolean
        get() = isFastDoubleClick(-1, DIFF)

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    fun isFastDoubleClick(buttonId: Int): Boolean {
        return isFastDoubleClick(buttonId, DIFF)
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    fun isFastDoubleClick(buttonId: Int = 10, diff: Long = 1000): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (timeD < 0) {
            return false
        }
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true
        }
        lastClickTime = time
        lastButtonId = buttonId
        return false
    }
}