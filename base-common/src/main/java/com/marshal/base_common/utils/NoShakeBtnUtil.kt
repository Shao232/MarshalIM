
import android.view.View
import kotlin.math.abs

/**
 * 防止按钮多次点击
 */
object NoShakeBtnUtil {
    private var lastClickTime: Long = 0
    private const val DIFF: Long = 700
    private var lastButtonId = -1

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    fun isFastDoubleClick(view: View): Boolean {
        return isFastDoubleClick(view, DIFF)
    }

    /**
     * 是否是快速点击
     *
     * @param v 点击的控件
     * @param intervalMillis 时间间期（毫秒）
     * @return true:是，false:不是
     */
    private fun isFastDoubleClick(view: View, intervalMillis: Long = DIFF): Boolean {
        val viewId = view.id
        val time = System.currentTimeMillis()
        val timeInterval = abs(time - lastClickTime)
        return if (timeInterval < intervalMillis && viewId == lastButtonId) {
            true
        } else {
            lastClickTime = time
            lastButtonId = viewId
            false
        }
    }
}